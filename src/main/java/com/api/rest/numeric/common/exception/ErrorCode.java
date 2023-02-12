package com.api.rest.numeric.common.exception;

public abstract class ErrorCode {
  public static final String INTERNAL_SERVER_ERROR = "numeric.api.internal_server_error";
  public static final String TOO_MANY_REQUEST = "numeric.api.too_many_request";

  public static final String NUMERIC_CLIENT_ERROR = "numeric.api.failed_dependency";
}
