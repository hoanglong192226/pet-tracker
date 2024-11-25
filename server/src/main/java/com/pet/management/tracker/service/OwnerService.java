package com.pet.management.tracker.service;

import com.pet.management.tracker.model.dto.OwnerDto;
import java.util.List;

public interface OwnerService {

  List<OwnerDto> findAll();

  List<OwnerDto> bulk(List<OwnerDto> owners);

  List<OwnerDto> findByIds(List<Long> ids);
}
