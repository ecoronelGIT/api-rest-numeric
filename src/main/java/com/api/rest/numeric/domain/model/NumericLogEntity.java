package com.api.rest.numeric.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "numeric_logs")
public class NumericLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "method")
    private String method;

    @NotBlank
    @Column(name = "url")
    private String url;

    @Column(name = "request")
    private String request;

    @NotBlank
    @Column(name = "request_headers")
    private String requestHeaders;

    @Column(name = "response")
    private String response;

    @NotNull
    @Column(name = "response_status")
    private int responseStatus;

    @Column(name = "response_headers")
    private String responseHeaders;

    @Column(name = "duration")
    private long duration;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
