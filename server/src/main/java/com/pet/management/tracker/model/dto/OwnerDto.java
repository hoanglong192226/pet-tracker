package com.pet.management.tracker.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
@JsonInclude(Include.NON_NULL)
public class OwnerDto implements Serializable {
  private Long id;
  @NotNull
  @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
  private String name;
  @NotNull
  @Size(min = 9, max = 20, message = "Phone must be between 10 and 20 characters")
  private String phone;

  @JsonProperty(access = Access.READ_ONLY)
  private List<PetDto> pets;
}
