package com.pet.management.tracker.converter;

import com.pet.management.tracker.model.UserRole;
import com.pet.management.tracker.model.dto.UserDto;
import com.pet.management.tracker.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements DtoConverter<User, UserDto> {

  @Override
  public UserDto toDto(User user) {
    return UserDto.builder().role(UserRole.fromValue(user.getRole())).id(user.getId()).username(user.getUsername())
        .build();
  }

  @Override
  public User fromDto(UserDto viewModel) {
    return new User(viewModel.getId(), viewModel.getUsername(), viewModel.getPassword(),
        viewModel.getRole().getValue());
  }
}
