package com.pet.management.tracker.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.pet.management.tracker.model.PetType;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class PetDto implements Serializable {
  private Long id;
  @NotNull(message = "Name can not be null")
  @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
  private String name;
  private int age;
  private float weight;
  @NotNull
  private PetType type;
  private String medicalNote;
  @JsonProperty(access = Access.READ_ONLY)
  private OwnerDto owner;

  @JsonProperty(access = Access.WRITE_ONLY)
  private Long ownerId;
}
