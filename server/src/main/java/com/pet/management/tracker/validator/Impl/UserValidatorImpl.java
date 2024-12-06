package com.pet.management.tracker.validator.Impl;

import com.pet.management.tracker.exception.NotFoundException;
import com.pet.management.tracker.exception.UnsupportedOperationException;
import com.pet.management.tracker.model.dto.UserDto;
import com.pet.management.tracker.service.UserService;
import com.pet.management.tracker.util.ErrorCode;
import com.pet.management.tracker.validator.UserValidator;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserValidatorImpl implements UserValidator {

  private static final String ADMIN_USERNAME = "admin";

  private final UserService userService;

  public void validateUserExist(UserDto userDto) {
    List<UserDto> existedUsers = userService.findByIds(Collections.singletonList(userDto.getId()));
    if (!existedUsers.isEmpty()) {
      throw new NotFoundException(ErrorCode.USER_EXISTED, "User existed");
    }

    UserDto existedUsernameUser = userService.findByUsername(userDto.getUsername());
    if (existedUsernameUser != null) {
      throw new NotFoundException(ErrorCode.USER_EXISTED, "User existed");
    }

  }

  private void validAdminUsername(String username) {
    if (username.equals(ADMIN_USERNAME)) {
      throw new UnsupportedOperationException("Unable to modify admin username");
    }
  }

  @Override
  public void validateCreateUser(UserDto userDto) {
    validateUserExist(userDto);

    validAdminUsername(userDto.getUsername());
  }

  @Override
  public void validateDeleteUser(Long id) {
    List<UserDto> existedUsers = userService.findByIds(Collections.singletonList(id));
    if (existedUsers.isEmpty()) {
      return;
    }
    validAdminUsername(existedUsers.get(0).getUsername());

  }
}
