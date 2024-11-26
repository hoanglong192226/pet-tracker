package com.pet.management.tracker.controller;

import com.pet.management.tracker.exception.NotFoundException;
import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.model.dto.OwnerDtoList;
import com.pet.management.tracker.service.OwnerService;
import com.pet.management.tracker.util.ErrorCode;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public List<OwnerDto> getAllOwners() {
    return ownerService.findAll();
  }

  @PostMapping
  public List<OwnerDto> createOwners(@Valid @RequestBody OwnerDtoList ownerDtos) {
    return ownerService.createOwners(ownerDtos.getOwners());
  }

  @RequestMapping("/{id}")
  public OwnerDto getOwnerById(@PathVariable @NotNull Long id) {
    List<OwnerDto> ownerDtos = ownerService.findByIds(Collections.singletonList(id));
    if (ownerDtos.isEmpty()) {
      throw new NotFoundException(ErrorCode.OWNER_NOT_FOUND, "Owner not found");
    }
    return ownerDtos.get(0);
  }

  @DeleteMapping("/{id}")
  public void deleteOwner(@PathVariable @NotNull Long id) {
    ownerService.deleteOwner(id);
  }
}
