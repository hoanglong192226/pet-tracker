package com.pet.management.tracker.exception;

import com.pet.management.tracker.util.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @Builder
  @Getter
  private static final class ResponseObject {

    private final String errorCode;
    private final String errorMessage;
    private final int statusCode;
  }

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<ResponseObject> handleNotFoundException(NotFoundException exception) {
    return new ResponseEntity<>(
        ResponseObject.builder().statusCode(exception.getStatusCode().value()).errorCode(exception.getErrorCode())
            .errorMessage(exception.getErrorMessage()).build(), exception.getStatusCode());
  }

  @ExceptionHandler({Exception.class, ApplicationException.class})
  public ResponseEntity<ResponseObject> handleException(Exception exception) {
    String errorMessage = "Server Error";
    String errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

    if (exception instanceof ApplicationException) {
      errorMessage = ((ApplicationException) exception).getErrorMessage();
      statusCode = ((ApplicationException) exception).getStatusCode();
      errorCode = ((ApplicationException) exception).getErrorCode();
    }
    return new ResponseEntity<>(
        ResponseObject.builder().statusCode(statusCode.value())
            .errorCode(errorCode)
            .errorMessage(errorMessage).build(), statusCode);
  }
}
