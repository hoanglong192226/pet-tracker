package com.pet.management.tracker.util;

public class ErrorCode {

  private ErrorCode() {
  }

  public static final String OWNER_NOT_FOUND = "ownerNotFound";
  public static final String PET_NOT_FOUND = "petNotFound";
  public static final String INVALID_USER_ROLE = "invalidUserRole";
  public static final String USER_NOT_FOUND = "userNotFound";

  public static final String INVALID_INPUT = "invalidInput";

  public static final String UNAUTHENTICATED = "unauthenticated";
  public static final String INTERNAL_SERVER_ERROR = "internalServerError";

  public static final String UNSUPPORTED_OPERATION = "unsupportedOperation";
}
