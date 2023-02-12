package com.api.rest.numeric.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class ApiExceptionResponse {
    @NotNull
    private String code;
    @NotNull
    private String message;
    private String cause;

}
