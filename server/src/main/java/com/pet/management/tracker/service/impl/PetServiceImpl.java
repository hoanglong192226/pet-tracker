package com.pet.management.tracker.service.impl;

import com.pet.management.tracker.converter.PetConverter;
import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.model.entity.Pet;
import com.pet.management.tracker.repository.PetRepository;
import com.pet.management.tracker.service.OwnerService;
import com.pet.management.tracker.service.PetService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PetServiceImpl implements PetService {

  private final OwnerService ownerService;
  private final PetRepository petRepository;
  private final PetConverter petConverter;

  @Override
  public List<PetDto> findAll() {
    return petRepository.findAll().stream().map(petConverter::toDto).collect(Collectors.toList());
  }

  @Override
  public List<PetDto> bulk(List<PetDto> petDtos) {
    Set<Long> ownerIds = petDtos.stream().map(PetDto::getOwnerId).collect(Collectors.toSet());
    Map<Long, OwnerDto> ownerKeyToValue = ownerService.findByIds(new ArrayList<>(ownerIds)).stream()
        .collect(Collectors.toMap(OwnerDto::getId, Function.identity()));

    for(PetDto petDto: petDtos) {
      if(petDto.getOwnerId() != null && !ownerKeyToValue.containsKey(petDto.getOwnerId())) {
        throw new RuntimeException("hihii");
      }
    }

    List<Pet> pets = petDtos.stream().map(petConverter::fromDto).collect(Collectors.toList());
    List<Pet> savedPets = petRepository.saveAll(pets);

    return savedPets.stream().map(petConverter::toDto).collect(Collectors.toList());
  }
}
