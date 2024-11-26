package com.pet.management.tracker.service;

import com.pet.management.tracker.model.dto.OwnerDto;
import java.util.List;

public interface OwnerService {

  List<OwnerDto> findAll();

  List<OwnerDto> createOwners(List<OwnerDto> owners);

  List<OwnerDto> findByIds(List<Long> ids);

  void deleteOwner(Long id);
}
