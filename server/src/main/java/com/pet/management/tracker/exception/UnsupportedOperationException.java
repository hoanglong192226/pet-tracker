package com.pet.management.tracker.exception;

import static com.pet.management.tracker.util.ErrorCode.UNSUPPORTED_OPERATION;

import org.springframework.http.HttpStatus;

public class UnsupportedOperationException extends ApplicationException {

  public UnsupportedOperationException(String errorMessage) {
    super(UNSUPPORTED_OPERATION, errorMessage, HttpStatus.FORBIDDEN);
  }
}
