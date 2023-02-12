package com.api.rest.numeric.util;

import java.nio.charset.StandardCharsets;

import com.api.rest.numeric.common.route.ExternalRoute;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.HttpStatusCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public abstract class MockNumericClient {

  public static void getCurrentPercentage(HttpStatusCode httpStatusCode, String response, MockServerClient mockServer) {

    mockServer.when(
      HttpRequest.request()
        .withMethod(HttpMethod.GET.name())
        .withPath(ExternalRoute.NUMERIC_BASIC_PATH)
    )
      .respond(
        HttpResponse.response().withStatusCode(httpStatusCode.code())
          .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .withBody(response, StandardCharsets.UTF_8)
      );
  }

}
