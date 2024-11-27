package com.pet.management.tracker.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.pet.management.tracker.util.ErrorCode;
import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @Builder
  @Getter
  @JsonInclude(Include.NON_NULL)
  private static final class ResponseObject {

    private final String errorCode;
    private final String errorMessage;
    private final int statusCode;
    private final Object detail;
  }

  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<ResponseObject> handleNotFoundException(NotFoundException exception) {
    return new ResponseEntity<>(
        ResponseObject.builder().statusCode(exception.getStatusCode().value()).errorCode(exception.getErrorCode())
            .errorMessage(exception.getErrorMessage()).build(), exception.getStatusCode());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseObject> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return new ResponseEntity<>(
        ResponseObject.builder()
            .errorCode(ErrorCode.INVALID_INPUT)
            .errorMessage("Invalid field input")
            .detail(errors)
            .statusCode(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({Exception.class, ApplicationException.class})
  public ResponseEntity<ResponseObject> handleException(Exception exception) {
    log.info(exception.getMessage(), exception);
    String errorMessage = "Server Error";
    String errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

    if (exception instanceof ApplicationException) {
      ApplicationException applicationException = (ApplicationException) exception;
      errorMessage = applicationException.getErrorMessage();
      statusCode = applicationException.getStatusCode();
      errorCode = applicationException.getErrorCode();
    } else if (exception.getCause() != null && exception.getCause().getCause() != null && exception.getCause()
        .getCause() instanceof ApplicationException) {
      ApplicationException applicationException = (ApplicationException) exception.getCause().getCause();
      errorMessage = applicationException.getErrorMessage();
      statusCode = applicationException.getStatusCode();
      errorCode = applicationException.getErrorCode();
    }
    return new ResponseEntity<>(
        ResponseObject.builder().statusCode(statusCode.value())
            .errorCode(errorCode)
            .errorMessage(errorMessage).build(), statusCode);
  }
}
