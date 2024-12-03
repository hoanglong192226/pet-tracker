package com.pet.management.tracker.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.pet.management.tracker.converter.OwnerConverter;
import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.model.entity.Owner;
import com.pet.management.tracker.repository.OwnerRepository;
import com.pet.management.tracker.service.OwnerService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OwnerServiceImplTest {

  @Mock
  private OwnerRepository ownerRepository;

  private final OwnerConverter ownerConverter = new OwnerConverter();

  private OwnerService ownerService;

  @BeforeEach
  public void init() {
    ownerService = new OwnerServiceImpl(ownerRepository, ownerConverter);
  }

  @Test
  public void testFindAll_thenReturnSuccessList() {
    // Given
    List<Owner> owners = new ArrayList<>();
    owners.add(new Owner());

    //When
    Mockito.when(ownerRepository.findAll()).thenReturn(owners);
    List<OwnerDto> ownerDtos = ownerService.findAll();

    // Then
    assertFalse(ownerDtos.isEmpty());
  }


  @Test
  public void testCreateOwners_thenSuccess() {
    // Given
    List<Owner> owners = new ArrayList<>();
    owners.add(new Owner());

    // When
    Mockito.when(ownerRepository.saveAll(Mockito.any())).thenReturn(owners);
    List<OwnerDto> ownerDtos = ownerService.createOwners(new ArrayList<>());

    // Then
    assertFalse(ownerDtos.isEmpty());
  }

  @Test
  public void testFindByIds_thenReturnSuccessList() {
    // Given
    List<Owner> owners = new ArrayList<>();
    owners.add(new Owner());

    // When
    Mockito.when(ownerRepository.findAllById(Mockito.any())).thenReturn(owners);
    List<OwnerDto> ownerDtos = ownerService.findByIds(new ArrayList<>());

    // Then
    assertFalse(ownerDtos.isEmpty());
  }

  @Test
  public void testDeleteOwner_thenSuccess() {
    // Given
    Long id = 10L;

    // When
    Mockito.doNothing().when(ownerRepository).deleteIfExist(id);
    ownerService.deleteOwner(id);

    // Then
    Mockito.verify(ownerRepository, Mockito.times(1)).deleteIfExist(id);
  }

}