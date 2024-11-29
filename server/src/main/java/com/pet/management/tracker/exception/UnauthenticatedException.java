package com.pet.management.tracker.exception;

import org.springframework.http.HttpStatus;

public class UnauthenticatedException extends ApplicationException {

  public UnauthenticatedException(String errorCode, String errorMessage) {
    super(errorCode, errorMessage, HttpStatus.UNAUTHORIZED);
  }
}
