package com.pet.management.tracker.controller;

import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.model.dto.PetDtoList;
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
@RequestMapping("/pets")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PetController {

  private final PetService petService;

  @GetMapping
  public List<PetDto> findAllPets() {
    return petService.findAll();
  }

  @PostMapping
  public List<PetDto> bulk(@Valid @RequestBody PetDtoList petDtos) {
    return petService.bulk(petDtos.getPets());
  }
}
