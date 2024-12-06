package com.pet.management.tracker.exception;

import static com.pet.management.tracker.util.ErrorCode.INVALID_INPUT;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException{

  public BadRequestException(String errorMessage) {
    super(INVALID_INPUT, errorMessage, HttpStatus.BAD_REQUEST);
  }
}
