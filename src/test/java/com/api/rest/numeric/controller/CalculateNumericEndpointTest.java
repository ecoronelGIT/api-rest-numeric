package com.api.rest.numeric.controller;

import com.api.rest.numeric.BaseTest;
import com.api.rest.numeric.common.constant.HttpConstant;
import com.api.rest.numeric.common.exception.ErrorCode;
import com.api.rest.numeric.common.route.Route;
import com.api.rest.numeric.common.util.CacheUtil;
import com.api.rest.numeric.domain.model.NumericLogEntity;
import com.api.rest.numeric.domain.repository.NumericLogRepository;
import com.api.rest.numeric.util.MockNumericClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.HttpStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@MockServerSettings(ports = 1080, perTestSuite = true)
public class CalculateNumericEndpointTest extends BaseTest {

    @Autowired
    private NumericLogRepository numericLogRepository;

    @Test
    @SneakyThrows
    @SqlGroup({
        @Sql(scripts = "/db/queries/delete_numeric_logs.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void getNumericPercentageShouldReturnSuccess(MockServerClient mockServer) {
        String response = fileFromResources("response/numeric_response.json");

        String externalResponse = fileFromResources("response/numeric_external_response.json");
        MockNumericClient.getCurrentPercentage(HttpStatusCode.OK_200, externalResponse, mockServer);

        mvc.perform(
            MockMvcRequestBuilders.get(Route.NUMERIC_CONTROLLER_BASIC_PATH)
                .queryParam(HttpConstant.FIRST_NUMBER, "5")
                .queryParam(HttpConstant.SECOND_NUMBER, "5")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(response, true))
            .andDo((handler) -> {
                List<NumericLogEntity> entities = numericLogRepository.findAll();
                Assertions.assertEquals(1, entities.size());

                NumericLogEntity entity = entities.get(0);
                Assertions.assertNotNull(entity.getResponse());
                Assertions.assertTrue(entity.getResponse().contains("result"));
                Assertions.assertTrue(entity.getResponse().contains("11"));
                Assertions.assertEquals(HttpStatus.OK.value(), entity.getResponseStatus());
            });
    }

    @Test
    @SneakyThrows
    @SqlGroup({
        @Sql(scripts = "/db/queries/delete_numeric_logs.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void getNumericPercentageInCacheShouldReturnSuccess() {
        String response = fileFromResources("response/numeric_response.json");

        CacheUtil.getCacheProvider().set(CacheUtil.NUMERIC_PERCENTAGE_KEY, "10");

        mvc.perform(
            MockMvcRequestBuilders.get(Route.NUMERIC_CONTROLLER_BASIC_PATH)
                .queryParam(HttpConstant.FIRST_NUMBER, "5")
                .queryParam(HttpConstant.SECOND_NUMBER, "5")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(response, true))
            .andDo((handler) -> {
                List<NumericLogEntity> entities = numericLogRepository.findAll();
                Assertions.assertEquals(1, entities.size());

                NumericLogEntity entity = entities.get(0);
                Assertions.assertNotNull(entity.getResponse());
                Assertions.assertTrue(entity.getResponse().contains("result"));
                Assertions.assertTrue(entity.getResponse().contains("11"));
                Assertions.assertEquals(HttpStatus.OK.value(), entity.getResponseStatus());
            });
    }

    @Test
    @SneakyThrows
    @SqlGroup({
        @Sql(scripts = "/db/queries/delete_numeric_logs.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void getNumericPercentageWhenClientReturnEmptyShouldReturnLastCacheSaved() {
        String response = fileFromResources("response/numeric_response.json");

        CacheUtil.getCacheProvider().set(CacheUtil.LAST_NUMERIC_PERCENTAGE_KEY, "10");

        mvc.perform(
            MockMvcRequestBuilders.get(Route.NUMERIC_CONTROLLER_BASIC_PATH)
                .queryParam(HttpConstant.FIRST_NUMBER, "5")
                .queryParam(HttpConstant.SECOND_NUMBER, "5")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(response, true))
            .andDo((handler) -> {
                List<NumericLogEntity> entities = numericLogRepository.findAll();
                Assertions.assertEquals(1, entities.size());

                NumericLogEntity entity = entities.get(0);
                Assertions.assertNotNull(entity.getResponse());
                Assertions.assertTrue(entity.getResponse().contains("result"));
                Assertions.assertTrue(entity.getResponse().contains("11"));
                Assertions.assertEquals(HttpStatus.OK.value(), entity.getResponseStatus());
            });
    }

    @Test
    @SneakyThrows
    @SqlGroup({
        @Sql(scripts = "/db/queries/delete_numeric_logs.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void getNumericPercentageWhenClientFailsShouldReturnLastCacheSaved(MockServerClient mockServer) {
        String response = fileFromResources("response/numeric_response.json");

        CacheUtil.getCacheProvider().set(CacheUtil.LAST_NUMERIC_PERCENTAGE_KEY, "10");

        MockNumericClient.getCurrentPercentage(HttpStatusCode.BAD_GATEWAY_502, null, mockServer);

        mvc.perform(
            MockMvcRequestBuilders.get(Route.NUMERIC_CONTROLLER_BASIC_PATH)
                .queryParam(HttpConstant.FIRST_NUMBER, "5")
                .queryParam(HttpConstant.SECOND_NUMBER, "5")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(response, true))
            .andDo((handler) -> {
                List<NumericLogEntity> entities = numericLogRepository.findAll();
                Assertions.assertEquals(1, entities.size());

                NumericLogEntity entity = entities.get(0);
                Assertions.assertNotNull(entity.getResponse());
                Assertions.assertTrue(entity.getResponse().contains("result"));
                Assertions.assertTrue(entity.getResponse().contains("11"));
                Assertions.assertEquals(HttpStatus.OK.value(), entity.getResponseStatus());
            });
    }

    @Test
    @SneakyThrows
    @SqlGroup({
        @Sql(scripts = "/db/queries/delete_numeric_logs.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    public void getNumericPercentageWhenClientFailsShouldReturnErrorWithEmptyCache(MockServerClient mockServer) {
        MockNumericClient.getCurrentPercentage(HttpStatusCode.BAD_GATEWAY_502, null, mockServer);

        mvc.perform(
            MockMvcRequestBuilders.get(Route.NUMERIC_CONTROLLER_BASIC_PATH)
                .queryParam(HttpConstant.FIRST_NUMBER, "5")
                .queryParam(HttpConstant.SECOND_NUMBER, "5")
        )
            .andExpect(MockMvcResultMatchers.status().isFailedDependency())
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.NUMERIC_CLIENT_ERROR))
            .andDo((handler) -> {
                List<NumericLogEntity> entities = numericLogRepository.findAll();
                Assertions.assertEquals(1, entities.size());

                NumericLogEntity entity = entities.get(0);
                Assertions.assertNotNull(entity.getResponse());
                Assertions.assertEquals(HttpStatus.FAILED_DEPENDENCY.value(), entity.getResponseStatus());
            });
    }

    @Override
    public void cleanData() {
        CacheUtil.deleteKey(CacheUtil.NUMERIC_PERCENTAGE_KEY);
        CacheUtil.deleteKey(CacheUtil.LAST_NUMERIC_PERCENTAGE_KEY);
    }
}
