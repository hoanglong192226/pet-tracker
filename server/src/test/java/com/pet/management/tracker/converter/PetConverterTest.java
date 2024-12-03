package com.pet.management.tracker.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.pet.management.tracker.model.PetType;
import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.model.entity.Pet;
import org.junit.jupiter.api.Test;

class PetConverterTest {

  private final PetConverter petConverter = new PetConverter();

  @Test
  public void testToDto_thenReturnSuccess() {
    // Given
    Pet pet = new Pet();
    pet.setType(PetType.DOG.getValue());
    pet.setId(1L);
    pet.setMedicalNote("medical");
    pet.setAge(20);
    pet.setWeight(50);
    pet.setName("name");

    // When
    PetDto petDto = petConverter.toDto(pet);

    // Then
    assertEquals(petDto.getType().getValue(), PetType.DOG.getValue());
    assertEquals(petDto.getName(), "name");
    assertEquals(petDto.getId(), 1L);
    assertEquals(petDto.getWeight(), 50);
    assertEquals(petDto.getMedicalNote(), "medical");
    assertEquals(petDto.getAge(), 20);
    assertNull(petDto.getOwner());
  }

  @Test
  public void testFromDto_whenOwnerIsNull_thenReturnSuccess() {
    // Give
    PetDto petDto = PetDto.builder().id(1L).name("name").age(10).medicalNote("medical").type(PetType.DOG).weight(50)
        .build();

    // When
    Pet pet = petConverter.fromDto(petDto);
    assertEquals(pet.getType(), PetType.DOG.getValue());
    assertEquals(pet.getName(), "name");
    assertEquals(pet.getId(), 1L);
    assertEquals(pet.getWeight(), 50);
    assertEquals(pet.getMedicalNote(), "medical");
    assertEquals(pet.getAge(), 10);

    // Then
    assertNull(pet.getOwner());
  }

  @Test
  public void testFromDto_whenOwnerIsNotNull_thenReturnSuccess() {
    // Give
    PetDto petDto = PetDto.builder().type(PetType.DOG).ownerId(10L).build();

    // When
    Pet pet = petConverter.fromDto(petDto);

    // Then
    assertEquals(pet.getOwner().getId(), 10L);
  }

}