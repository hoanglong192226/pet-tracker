package com.pet.management.tracker.service;

import com.pet.management.tracker.model.dto.PetDto;
import java.util.List;

public interface PetService {

  List<PetDto> findAll();

  List<PetDto> createPets(List<PetDto> pets);

  List<PetDto> findByIds(List<Long> ids);

  void deletePet(Long id);
}
