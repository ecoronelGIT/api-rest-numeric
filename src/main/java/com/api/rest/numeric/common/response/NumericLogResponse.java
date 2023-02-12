package com.api.rest.numeric.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NumericLogResponse {
    private Long id;
    private String method;
    private String url;
    private String request;
    private String requestHeaders;
    private String response;
    private int responseStatus;
    private String responseHeaders;
    private long duration;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime createdAt;
}
