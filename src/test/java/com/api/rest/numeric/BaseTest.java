package com.api.rest.numeric;

import com.api.rest.numeric.configuration.TestRedisConfiguration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.mockserver.client.MockServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@SpringBootTest(classes = { TestRedisConfiguration.class })
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BaseTest {

  @Autowired
  protected MockMvc mvc;

  @BeforeEach
  public void beforeEach(MockServerClient mockServer) {
    mockServer.reset();
    cleanData();
  }

  protected void cleanData() {
  }

  @SneakyThrows
  protected String fileFromResources(String filePath) {
    return Files.readString(Path.of(
        Objects.requireNonNull(getClass().getClassLoader().getResource(filePath))
            .toURI()));
  }

}
