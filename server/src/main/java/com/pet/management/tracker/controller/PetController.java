package com.pet.management.tracker.controller;

import com.pet.management.tracker.exception.NotFoundException;
import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.model.dto.PetDtoList;
import com.pet.management.tracker.service.PetService;
import com.pet.management.tracker.util.ErrorCode;
import com.pet.management.tracker.validator.PetValidator;
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
@RequestMapping("/pets")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PetController {

  private final PetService petService;
  private final PetValidator petValidator;

  @GetMapping
  public List<PetDto> getAllPets() {
    return petService.findAll();
  }

  @GetMapping(value = "/{id}")
  public PetDto getPetById(@PathVariable @NotNull Long id) {
    List<PetDto> petDtos = petService.findByIds(Collections.singletonList(id));
    if (petDtos.isEmpty()) {
      throw new NotFoundException(ErrorCode.PET_NOT_FOUND, "Pet not found");
    }

    return petDtos.get(0);
  }

  @PostMapping
  public List<PetDto> createPets(@Valid @RequestBody PetDtoList petDtos) {
    petValidator.validateCreatePets(petDtos.getPets());
    return petService.createPets(petDtos.getPets());
  }

  @DeleteMapping("/{id}")
  public void deletePet(@PathVariable @NotNull Long id) {
    petService.deletePet(id);
  }

}
