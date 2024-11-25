package com.pet.management.tracker.model.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class OwnerDtoList {

  @Valid
  private List<OwnerDto> owners = new ArrayList<>();
}
