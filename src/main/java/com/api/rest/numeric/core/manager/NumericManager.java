package com.api.rest.numeric.core.manager;

import com.api.rest.numeric.client.NumericClient;
import com.api.rest.numeric.common.exception.ApiException;
import com.api.rest.numeric.common.exception.ErrorCode;
import com.api.rest.numeric.common.mapper.NumericLogMapper;
import com.api.rest.numeric.common.response.CalculateResponse;
import com.api.rest.numeric.common.response.NumericLogResponse;
import com.api.rest.numeric.common.response.external.PercentageExternalResponse;
import com.api.rest.numeric.common.util.CacheUtil;
import com.api.rest.numeric.domain.repository.NumericLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class NumericManager {

    private final NumericClient numericClient;
    private final NumericLogRepository numericLogRepository;

    public NumericManager(NumericClient numericClient, NumericLogRepository numericLogRepository) {
        this.numericClient = numericClient;
        this.numericLogRepository = numericLogRepository;
    }

    public CalculateResponse calculate(int firstNumber, int secondNumber) {
        int percentage = getCurrentPercentage();
        int sum = firstNumber + secondNumber;
        return new CalculateResponse( sum + getPercentage(sum, percentage));
    }

    public Page<NumericLogResponse> getNumericLogs(Pageable pageable) {
        return numericLogRepository.findAll(pageable).map(NumericLogMapper::getNumericResponse);
    }

    private int getCurrentPercentage() {
        String value = CacheUtil.getKey(CacheUtil.NUMERIC_PERCENTAGE_KEY).orElseGet(() -> {
            String percentage = String.valueOf(getClientPercentage());
            CacheUtil.getCacheProvider().set(
                CacheUtil.THIRTY_MINUTES,
                CacheUtil.NUMERIC_PERCENTAGE_KEY,
                percentage
            );
            CacheUtil.getCacheProvider().set(CacheUtil.LAST_NUMERIC_PERCENTAGE_KEY, percentage);
            return percentage;
        });
        return Integer.parseInt(value);
    }

    private int getClientPercentage() {
        try {
            return numericClient.getCurrentPercentage().getPercentage();
        } catch (Exception ex) {
            String value = CacheUtil.getKey(CacheUtil.LAST_NUMERIC_PERCENTAGE_KEY)
                .orElseThrow(() -> new ApiException(ErrorCode.NUMERIC_CLIENT_ERROR, HttpStatus.FAILED_DEPENDENCY, ex.getMessage()));
            return Integer.parseInt(value);
        }
    }

    private double getPercentage(int value, int percentage) {
        return (value * percentage) / 100;
    }
}
