package com.pet.management.tracker.validator.Impl;

import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.service.OwnerService;
import com.pet.management.tracker.validator.PetValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PetValidatorImpl implements PetValidator {

  private final OwnerService ownerService;


  @Override
  public void validateCreatePets(List<PetDto> petDtos) {
    Set<Long> ownerIds = petDtos.stream().map(PetDto::getOwnerId).collect(Collectors.toSet());
    Map<Long, OwnerDto> ownerKeyToValue = ownerService.findByIds(new ArrayList<>(ownerIds)).stream()
        .collect(Collectors.toMap(OwnerDto::getId, Function.identity()));

    for (PetDto petDto : petDtos) {
      var ownerDto = ownerKeyToValue.get(petDto.getOwnerId());
      if (petDto.getOwnerId() != null && ownerDto == null) {
        throw new RuntimeException("Owner not exist");
      }
    }

  }
}
