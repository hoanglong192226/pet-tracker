package com.pet.management.tracker.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.pet.management.tracker.model.PetType;
import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.model.entity.Owner;
import com.pet.management.tracker.model.entity.Pet;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class OwnerConverterTest {

  private final OwnerConverter ownerConverter = new OwnerConverter();

  @Test
  public void testToDto_thenReturnSuccess() {
    // Given
    Pet pet = new Pet();
    pet.setType(PetType.DOG.getValue());

    Owner owner = new Owner();
    owner.setPhone("phone");
    owner.setName("name");
    owner.setPets(Collections.singletonList(pet));
    owner.setId(1L);

    // When
    OwnerDto ownerDto = ownerConverter.toDto(owner);

    // Then
    assertEquals(ownerDto.getPhone(), "phone");
    assertEquals(ownerDto.getName(), "name");
    assertEquals(ownerDto.getId(), 1L);
    assertFalse(ownerDto.getPets().isEmpty());
  }

  @Test
  public void testFromDto_whenOwnerDtoIsNull_thenReturnNull() {
    // Given
    OwnerDto ownerDto = null;

    // When
    Owner owner = ownerConverter.fromDto(ownerDto);

    // Then
    assertNull(owner);
  }

  @Test
  public void testFromDto_thenReturnSuccess() {
    // Given
    OwnerDto ownerDto = OwnerDto.builder().name("name").phone("phone").id(1L)
        .pets(Collections.singletonList(PetDto.builder().build())).build();

    // When
    Owner owner = ownerConverter.fromDto(ownerDto);

    // Then
    assertEquals(owner.getPhone(), "phone");
    assertEquals(owner.getName(), "name");
    assertEquals(owner.getId(), 1L);
    assertNull(owner.getPets());
  }

}