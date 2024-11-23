package com.pet.management.tracker.model.dto;

import java.io.Serializable;
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
public class PetDtoList implements Serializable {
  @Valid
  private List<PetDto> pets = new ArrayList<>();
}
