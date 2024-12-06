package com.pet.management.tracker.validator;

import com.pet.management.tracker.model.dto.UserDto;

public interface UserValidator {

  void validateCreateUser(UserDto userDto);

  void validateDeleteUser(Long id);
}
