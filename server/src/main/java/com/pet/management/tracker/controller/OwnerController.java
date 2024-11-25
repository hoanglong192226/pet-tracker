package com.pet.management.tracker.controller;

import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.model.dto.OwnerDtoList;
import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.model.dto.PetDtoList;
import com.pet.management.tracker.service.OwnerService;
import com.pet.management.tracker.service.PetService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OwnerController {

  private final OwnerService ownerService;

  @GetMapping
  public List<OwnerDto> findAllOwners() {
    return ownerService.findAll();
  }

  @PostMapping
  public List<OwnerDto> bulk(@Valid @RequestBody OwnerDtoList ownerDtos) {
    return ownerService.bulk(ownerDtos.getOwners());
  }
}