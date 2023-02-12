package com.api.rest.numeric.controller;

import com.api.rest.numeric.BaseTest;
import com.api.rest.numeric.common.constant.HttpConstant;
import com.api.rest.numeric.common.route.Route;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@MockServerSettings(ports = 1080, perTestSuite = true)
public class GetNumericLogsEndpointTest extends BaseTest {

    @Test
    @SneakyThrows
    @SqlGroup({
        @Sql(scripts = "/db/queries/delete_numeric_logs.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "/db/queries/insert_numeric_logs.sql")
    })
    public void getNumericLogsShouldReturnFiveElements() {
        String response = fileFromResources("response/numeric_log_response.json");

        mvc.perform(MockMvcRequestBuilders.get(Route.NUMERIC_CONTROLLER_BASIC_PATH + Route.NUMERIC_CONTROLLER_LOGS)
            .queryParam("page", "0")
            .queryParam("size", "5")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(response, true));
    }

    @Test
    @SneakyThrows
    @SqlGroup({
        @Sql(scripts = "/db/queries/delete_numeric_logs.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void getNumericLogsShouldReturnZeroElements() {
        String response = fileFromResources("response/numeric_log_empty_response.json");

        mvc.perform(MockMvcRequestBuilders.get(Route.NUMERIC_CONTROLLER_BASIC_PATH + Route.NUMERIC_CONTROLLER_LOGS)
            .queryParam("page", "0")
            .queryParam("size", "5")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(response, true));
    }
}
