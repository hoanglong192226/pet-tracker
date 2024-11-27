package com.pet.management.tracker.validator.Impl;

import com.pet.management.tracker.exception.NotFoundException;
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

  private final UserService userService;

  @Override
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
}
