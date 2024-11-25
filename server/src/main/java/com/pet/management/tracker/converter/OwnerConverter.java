package com.pet.management.tracker.converter;

import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.model.entity.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerConverter implements DtoConverter<Owner, OwnerDto> {

  @Override
  public OwnerDto toDto(Owner owner) {
    if (owner == null) {
      return null;
    }
    return OwnerDto.builder().pets(owner.getPets()).name(owner.getName()).id(owner.getId()).phone(owner.getPhone())
        .build();
  }

  @Override
  public Owner fromDto(OwnerDto viewModel) {
    if (viewModel == null) {
      return null;
    }
    return new Owner(viewModel.getId(), viewModel.getName(), viewModel.getPhone(), viewModel.getPets());
  }
}
