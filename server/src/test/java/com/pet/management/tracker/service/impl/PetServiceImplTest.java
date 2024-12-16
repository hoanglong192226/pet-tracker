package com.pet.management.tracker.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.pet.management.tracker.converter.PetConverter;
import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.model.entity.Pet;
import com.pet.management.tracker.repository.PetRepository;
import com.pet.management.tracker.service.PetService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PetServiceImplTest {

  @Mock
  private PetRepository petRepository;

  private final PetConverter petConverter = new PetConverter();

  private PetService petService;

  @BeforeEach
  public void init() {
    petService = new PetServiceImpl(petRepository, petConverter);
  }

  @Test
  public void testFindAll_thenReturnSuccessList() {
    // Given
    List<Pet> pets = new ArrayList<>();
    Pet pet = new Pet();
    pets.add(pet);

    //When
    Mockito.when(petRepository.findAll()).thenReturn(pets);
    List<PetDto> petDtos = petService.findAll();

    // Then
    assertFalse(petDtos.isEmpty());
  }

  @Test
  public void testCreatePets_thenSuccess() {
    // Given
    List<Pet> pets = new ArrayList<>();
    Pet pet = new Pet();
    pets.add(pet);

    // When
    Mockito.when(petRepository.saveAll(Mockito.any())).thenReturn(pets);
    List<PetDto> ownerDtos = petService.createPets(new ArrayList<>());

    // Then
    assertFalse(ownerDtos.isEmpty());
  }

  @Test
  public void testFindByIds_thenReturnSuccessList() {
    // Given
    List<Pet> pets = new ArrayList<>();
    Pet pet = new Pet();
    pets.add(pet);

    // When
    Mockito.when(petRepository.findAllById(Mockito.any())).thenReturn(pets);
    List<PetDto> petDtos = petService.findByIds(new ArrayList<>());

    // Then
    assertFalse(petDtos.isEmpty());
  }

  @Test
  public void testDeleteOwner_thenSuccess() {
    // Given
    Long id = 10L;

    // When
    Mockito.doNothing().when(petRepository).deleteIfExist(id);
    petService.deletePet(id);

    // Then
    Mockito.verify(petRepository, Mockito.times(1)).deleteIfExist(id);
  }

}