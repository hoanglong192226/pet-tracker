package com.pet.management.tracker.converter;

import com.pet.management.tracker.model.PetType;
import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.model.entity.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetConverter implements DtoConverter<Pet, PetDto> {

  @Override
  public PetDto toDto(Pet pet) {
    return PetDto.builder()
        .id(pet.getId())
        .name(pet.getName())
        .weight(pet.getWeight())
        .age(pet.getAge())
        .type(PetType.fromValue(pet.getType()))
        .medicalNote(pet.getMedicalNote())
//        .owner(pet.getOwner())
        .build();
  }

  @Override
  public Pet fromDto(PetDto viewModel) {
    return new Pet(viewModel.getId(), viewModel.getName(), viewModel.getAge(), viewModel.getWeight(),
        viewModel.getType().toString(), viewModel.getMedicalNote(), null);

  }
}
