package com.pet.management.tracker.converter;

import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.model.entity.Owner;
import com.pet.management.tracker.model.entity.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PetConverter implements DtoConverter<Pet, PetDto> {

  @Override
  public PetDto toDto(Pet pet) {
    return PetDto.builder()
        .id(pet.getId())
        .name(pet.getName())
        .weight(pet.getWeight())
        .age(pet.getAge())
        .type(pet.getType())
        .medicalNote(pet.getMedicalNote())
        .owner(pet.getOwner() != null ? OwnerDto.builder().id(pet.getOwner().getId()).phone(pet.getOwner().getPhone())
            .name(pet.getOwner().getName())
            .build() : null)
        .build();
  }

  @Override
  public Pet fromDto(PetDto petDto) {
    Owner owner = null;
    if (petDto.getOwnerId() != null) {
      owner = new Owner();
      owner.setId(petDto.getOwnerId());
    }

    return new Pet(petDto.getId(), petDto.getName(), petDto.getAge(), petDto.getWeight(),
        petDto.getType(), petDto.getMedicalNote(), owner);

  }
}
