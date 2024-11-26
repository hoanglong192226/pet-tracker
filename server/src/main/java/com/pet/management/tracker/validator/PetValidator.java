package com.pet.management.tracker.validator;

import com.pet.management.tracker.model.dto.PetDto;
import java.util.List;

public interface PetValidator {
  void validateCreatePets(List<PetDto> petDtos);
}
