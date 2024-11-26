package com.pet.management.tracker.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException{

  public NotFoundException(String errorCode, String errorMessage) {
    super(errorCode, errorMessage, HttpStatus.NOT_FOUND);
  }
}
