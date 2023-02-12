package com.api.rest.numeric.api;

import com.api.rest.numeric.common.constant.HttpConstant;
import com.api.rest.numeric.common.exception.ApiExceptionResponse;
import com.api.rest.numeric.common.response.CalculateResponse;
import com.api.rest.numeric.common.response.NumericLogResponse;
import com.api.rest.numeric.common.route.Route;
import com.api.rest.numeric.core.manager.NumericManager;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Numeric API")
@RestController
@RequestMapping(Route.NUMERIC_CONTROLLER_BASIC_PATH)
public class NumericController {

    private NumericManager numericManager;

    public NumericController(NumericManager numericManager) {
        this.numericManager = numericManager;
    }

    @Operation(description = "Calculate numeric by parameters", responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Do calculation and return de result",
            content = { @Content(
                schema = @Schema(implementation = CalculateResponse.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )}
        ),
        @ApiResponse(
            responseCode = "424",
            content = @Content(examples = {
                @ExampleObject(
                    description = "When client is not working",
                    value = "{\"code\": \"numeric.api.failed_dependency\", \"message\":\"Ocurrió un error consultando al cliente, intente mas tarde por favor\"}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(
            responseCode = "429",
            content = @Content(examples = {
                @ExampleObject(
                    description = "When we reach the max amount of request",
                    value = "{\"code\": \"numeric.api.too_many_request\", \"message\":\"Se superó la cantidad de request soportada por minuto\"}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping
    @RateLimiter(name = "rateLimiterApi")
    public CalculateResponse calculate(
        @RequestParam(value = HttpConstant.FIRST_NUMBER) int firstNumber,
        @RequestParam(value = HttpConstant.SECOND_NUMBER) int secondNumber)
    {
        return numericManager.calculate(firstNumber, secondNumber);
    }

    @Operation(description = "Get request logs", responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Page with the request made in the application",
            content = { @Content(
                schema = @Schema(implementation = Page.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )}
        )
    })
    @GetMapping(Route.NUMERIC_CONTROLLER_LOGS)
    @RateLimiter(name = "rateLimiterApi")
    public Page<NumericLogResponse> getNumericLogs(Pageable pageable) {
        return numericManager.getNumericLogs(pageable);
    }


}
