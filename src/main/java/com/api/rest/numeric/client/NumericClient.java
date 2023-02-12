package com.api.rest.numeric.client;

import com.api.rest.numeric.client.configuration.FeignConfiguration;
import com.api.rest.numeric.common.response.external.PercentageExternalResponse;
import com.api.rest.numeric.common.route.ExternalRoute;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
  name = ExternalRoute.NUMERIC_CLIENT_NAME,
  url = "${numeric.client.base-url}",
  configuration = {FeignConfiguration.class}
)
public interface NumericClient {

  @GetMapping(value = {ExternalRoute.NUMERIC_BASIC_PATH})
  PercentageExternalResponse getCurrentPercentage();

}
