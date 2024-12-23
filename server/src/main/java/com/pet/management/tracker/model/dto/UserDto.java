package com.pet.management.tracker.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.pet.management.tracker.model.UserRole;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
@JsonInclude(Include.NON_NULL)
public class UserDto {

  private Long id;
  @Size(min = 5, max = 32, message = "Username must be between 6 and 32 characters")
  @NotNull
  private String username;

  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;
  @NotNull
  private UserRole role;
}
