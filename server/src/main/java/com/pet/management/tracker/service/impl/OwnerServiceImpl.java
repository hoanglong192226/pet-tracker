package com.pet.management.tracker.service.impl;

import com.pet.management.tracker.converter.OwnerConverter;
import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.model.entity.Owner;
import com.pet.management.tracker.model.entity.Pet;
import com.pet.management.tracker.repository.OwnerRepository;
import com.pet.management.tracker.service.OwnerService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OwnerServiceImpl implements OwnerService {

  private final OwnerRepository ownerRepository;
  private final OwnerConverter ownerConverter;

  @Override
  public List<OwnerDto> findAll() {
    return ownerRepository.findAll().stream().map(ownerConverter::toDto).collect(Collectors.toList());
  }

  @Override
  public List<OwnerDto> bulk(List<OwnerDto> ownerDtos) {
    List<Owner> owners = ownerDtos.stream().map(ownerConverter::fromDto).collect(Collectors.toList());
    List<Owner> savedPets = ownerRepository.saveAll(owners);

    return savedPets.stream().map(ownerConverter::toDto).collect(Collectors.toList());
  }

  @Override
  public List<OwnerDto> findByIds(List<Long> ids) {
    return ownerRepository.findAllById(ids).stream().map(ownerConverter::toDto).collect(Collectors.toList());
  }
}