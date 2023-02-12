package com.api.rest.numeric.common.exception;

public abstract class ErrorMessage {
  public static String INTERNAL_SERVER_ERROR = "Ocurrió un error interno, por favor consulte a soporte";
  public static final String TOO_MANY_REQUEST = "Se superó la cantidad soportada por minuto";


  public static final String NUMERIC_CLIENT_ERROR = "Ocurrió un error consultando al cliente, intente mas tarde por favor";

}
