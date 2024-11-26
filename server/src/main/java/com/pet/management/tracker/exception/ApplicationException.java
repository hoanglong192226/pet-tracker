package com.pet.management.tracker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

  public ApplicationException(String errorCode, String errorMessage, HttpStatus statusCode) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.statusCode = statusCode;
  }

  private final String errorCode;
  private final String errorMessage;
  private final HttpStatus statusCode;
}
