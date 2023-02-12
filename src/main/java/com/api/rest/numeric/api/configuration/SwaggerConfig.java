package com.api.rest.numeric.api.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder()
      .group("public")
      .packagesToScan("com.api.rest.numeric.api")
      .build();
  }

  @Bean
  public OpenAPI springOpenAPI() {
    return (new OpenAPI())
      .info((new Info()).title("Numeric API"));
  }

}
