package com.pet.management.tracker.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileDto {
  private String username;
  private String role;
  private Long expiredAt;
}
