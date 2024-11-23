package com.pet.management.tracker.service.impl;

import com.pet.management.tracker.converter.PetConverter;
import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.model.entity.Pet;
import com.pet.management.tracker.repository.PetRepository;
import com.pet.management.tracker.service.PetService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PetServiceImpl implements PetService {

  private final PetRepository petRepository;
  private final PetConverter petConverter;

  @Override
  public List<PetDto> findAll() {
    return petRepository.findAll().stream().map(petConverter::toDto).collect(Collectors.toList());
  }

  @Override
  public List<PetDto> bulk(List<PetDto> petDtos) {
    List<Pet> pets = petDtos.stream().map(petConverter::fromDto).collect(Collectors.toList());
    List<Pet> savedPets = petRepository.saveAll(pets);

    return savedPets.stream().map(petConverter::toDto).collect(Collectors.toList());
  }
}
