package com.pet.management.tracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {
  private final String errorCode;
  private final String errorMessage;
  private final HttpStatus statusCode;

  public ApplicationException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
  }

  public ApplicationException(String errorCode, String errorMessage, HttpStatus statusCode) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.statusCode = statusCode;
  }

}
