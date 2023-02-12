package com.api.rest.numeric.controller;

import com.api.rest.numeric.BaseTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@MockServerSettings(ports = 1080, perTestSuite = true)
class HealthCheckTest extends BaseTest {

  @Test
  @SneakyThrows
  public void healthCheckTest() {
    mvc.perform(MockMvcRequestBuilders.get("/health"))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

}
