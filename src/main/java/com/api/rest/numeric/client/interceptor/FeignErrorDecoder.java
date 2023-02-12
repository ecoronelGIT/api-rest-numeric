package com.api.rest.numeric.client.interceptor;

import com.api.rest.numeric.common.exception.ApiException;
import com.api.rest.numeric.common.exception.ErrorCode;
import com.api.rest.numeric.common.exception.ErrorMessage;
import com.api.rest.numeric.common.route.ExternalRoute;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {


  @Override
  public Exception decode(String methodKey, Response response) {
    log.error("--methodKey: {} --status: {} --url: {}", methodKey, response.status(), response.request().url());

    return handleError(methodKey, response);
  }

  private Exception handleError(String methodKey, Response response) {
    String method = Optional.ofNullable(methodKey).map(m -> StringUtils.substringBefore(m, "#")).orElseGet(String::new);
    HttpStatus status = mapHttStatus(response.status());
    if(method.equals(ExternalRoute.NUMERIC_CLIENT_NAME)) {
      return new ApiException(ErrorCode.NUMERIC_CLIENT_ERROR, status, ErrorMessage.NUMERIC_CLIENT_ERROR);
    } else {
      return new ApiException(ErrorCode.INTERNAL_SERVER_ERROR, status, ErrorMessage.INTERNAL_SERVER_ERROR);
    }
  }

  private HttpStatus mapHttStatus(int statusExternal) {
    if((HttpStatus.BAD_GATEWAY.value() == statusExternal) || (HttpStatus.SERVICE_UNAVAILABLE.value() == statusExternal)
        || (HttpStatus.GATEWAY_TIMEOUT.value() == statusExternal) || (HttpStatus.INTERNAL_SERVER_ERROR.value() == statusExternal)) {
      return HttpStatus.FAILED_DEPENDENCY;
    }
    return HttpStatus.valueOf(statusExternal);
  }

}
