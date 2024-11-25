package com.pet.management.tracker.model.dto;

import com.pet.management.tracker.model.entity.Pet;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class OwnerDto implements Serializable {
  private Long id;
  @NotNull
  private String name;
  private String phone;

  private List<Pet> pets;
}
