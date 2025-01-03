package com.pet.management.tracker.controller;

import com.pet.management.tracker.exception.ApplicationException;
import com.pet.management.tracker.exception.NotFoundException;
import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.model.dto.UserDto;
import com.pet.management.tracker.service.UserService;
import com.pet.management.tracker.util.ErrorCode;
import com.pet.management.tracker.validator.UserValidator;
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
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

  private final UserService userService;
  private final UserValidator userValidator;

  @GetMapping("/users")
  public List<UserDto> getAllUsers() {
    return userService.findAll();
  }

  @PostMapping("/users")
  public UserDto saveUser(@Valid @RequestBody UserDto userDto) {
    userValidator.validateSaveUser(userDto);

    if(userDto.getId() == null) {
      List<UserDto> results = userService.createUsers(Collections.singletonList(userDto));
      if (results.isEmpty()) {
        throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR, "Error on creating user");
      }

      return results.get(0);
    }

    return userService.updateUser(userDto);
  }

  @GetMapping("/users/{id}")
  public UserDto getUserById(@PathVariable @NotNull Long id) {
    List<UserDto> userDtos = userService.findByIds(Collections.singletonList(id));
    if (userDtos.isEmpty()) {
      throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found");
    }
    return userDtos.get(0);
  }

  @DeleteMapping("/users/{id}")
  public void deleteUser(@PathVariable @NotNull Long id) {
    userValidator.validateDeleteUser(id);

    userService.deleteUser(id);
  }
}
