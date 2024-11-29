package com.pet.management.tracker.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthRequest {
  @Size(min = 5, max = 32, message = "Username must be between 6 and 32 characters")
  @NotNull
  private String username;
  @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
  @NotNull
  private String password;
}
