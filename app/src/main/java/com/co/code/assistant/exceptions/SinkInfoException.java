package com.co.code.assistant.exceptions;

import java.io.Serial;

public class SinkInfoException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 5L;

  public SinkInfoException(String message, Throwable cause) {
    super(message, cause);
  }

  public SinkInfoException(String message) {
    super(message);
  }
}
