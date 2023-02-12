package com.api.rest.numeric.common.mapper;

import com.api.rest.numeric.common.response.NumericLogResponse;
import com.api.rest.numeric.domain.model.NumericLogEntity;

public abstract class NumericLogMapper {

    public static NumericLogResponse getNumericResponse(NumericLogEntity numericLogEntity) {
        return new NumericLogResponse(
            numericLogEntity.getId(),
            numericLogEntity.getMethod(),
            numericLogEntity.getUrl(),
            numericLogEntity.getRequest(),
            numericLogEntity.getRequestHeaders(),
            numericLogEntity.getResponse(),
            numericLogEntity.getResponseStatus(),
            numericLogEntity.getResponseHeaders(),
            numericLogEntity.getDuration(),
            numericLogEntity.getCreatedAt()
        );
    }
}
