package com.pet.management.tracker.validator.Impl;

import static com.pet.management.tracker.util.ErrorCode.USER_NOT_FOUND;

import com.pet.management.tracker.exception.BadRequestException;
import com.pet.management.tracker.exception.NotFoundException;
import com.pet.management.tracker.exception.UnsupportedOperationException;
import com.pet.management.tracker.model.UserRole;
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

  @Override
  public void validateSaveUser(UserDto userDto) {
    if(userDto.getId() != null) {
      List<UserDto> users = userService.findByIds(Collections.singletonList(userDto.getId()));
      if(users.isEmpty()) {
        throw new NotFoundException(USER_NOT_FOUND, "User not found");
      }

      UserDto user = users.get(0);
      if(!user.getUsername().equals(userDto.getUsername())) {
        throw new UnsupportedOperationException("Unable to modify username");
      }

      if(user.getUsername().equals(ADMIN_USERNAME) && userDto.getRole() != UserRole.ADMIN) {
        throw new UnsupportedOperationException("Unable to modify 'admin' user role");
      }

      return;
    }

    UserDto existedUsernameUser = userService.findByUsername(userDto.getUsername());
    if (existedUsernameUser != null) {
      throw new BadRequestException("User existed");
    }
  }

  @Override
  public void validateDeleteUser(Long id) {
    List<UserDto> existedUsers = userService.findByIds(Collections.singletonList(id));
    if (existedUsers.isEmpty()) {
      throw new NotFoundException(USER_NOT_FOUND, "User not found");
    }

    UserDto user = existedUsers.get(0);

    if(user.getUsername().equals(ADMIN_USERNAME)) {
      throw new UnsupportedOperationException("Unable to delete 'admin' user");
    }

  }
}
