package com.pet.management.tracker.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
@JsonInclude(Include.NON_NULL)
public class UserDto {

  private Long id;
  @NotNull
  private String username;
  @NotNull
  private String password;
}
