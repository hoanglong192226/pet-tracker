package com.pet.management.tracker.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pet.management.tracker.exception.ApplicationException;
import com.pet.management.tracker.util.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {
  ADMIN("admin"),
  MEMBER("member");

  private final String value;

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static UserRole fromValue(String value) {
    for (UserRole type : UserRole.values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new ApplicationException(ErrorCode.INVALID_USER_ROLE, "Invalid user role: " + value);
  }
}
