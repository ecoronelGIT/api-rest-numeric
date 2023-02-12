package com.api.rest.numeric.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class CalculateResponse {

    @NotNull
    private double result;

}
