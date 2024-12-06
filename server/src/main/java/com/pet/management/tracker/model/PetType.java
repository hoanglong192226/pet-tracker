package com.pet.management.tracker.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.pet.management.tracker.exception.ApplicationException;
import com.pet.management.tracker.util.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PetType {
  DOG("dog"),
  CAT("cat"),
  UNKNOWN("unknown");

  private final String value;

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static PetType fromValue(String value) {
    for (PetType type : PetType.values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new ApplicationException(ErrorCode.INVALID_PET_TYPE, "Invalid pet type: " + value);
  }
}
