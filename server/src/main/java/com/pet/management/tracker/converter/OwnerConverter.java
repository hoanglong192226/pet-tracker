package com.pet.management.tracker.converter;

import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.model.entity.Owner;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OwnerConverter implements DtoConverter<Owner, OwnerDto> {

  @Override
  public OwnerDto toDto(Owner owner) {
    if (owner == null) {
      return null;
    }
    return OwnerDto.builder().pets(owner.getPets() != null ? owner.getPets().stream()
            .map(p -> PetDto.builder()
                .name(p.getName())
                .age(p.getAge())
                .type(p.getType())
                .weight(p.getWeight())
                .medicalNote(p.getMedicalNote())
                .id(p.getId()).build())
            .collect(Collectors.toList()) : null)
        .name(owner.getName()).id(owner.getId()).phone(owner.getPhone())
        .build();
  }

  @Override
  public Owner fromDto(OwnerDto ownerDto) {
    if (ownerDto == null) {
      return null;
    }
    return new Owner(ownerDto.getId(), ownerDto.getName(), ownerDto.getPhone(), null);
  }
}
