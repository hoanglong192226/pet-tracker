package com.pet.management.tracker.validator.Impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.pet.management.tracker.exception.BadRequestException;
import com.pet.management.tracker.exception.NotFoundException;
import com.pet.management.tracker.exception.UnsupportedOperationException;
import com.pet.management.tracker.model.UserRole;
import com.pet.management.tracker.model.dto.UserDto;
import com.pet.management.tracker.service.UserService;
import com.pet.management.tracker.validator.UserValidator;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class UserValidatorImplTest {

  @Mock
  private UserService userService;

  @Autowired
  private UserValidator userValidator;

  @BeforeEach
  public void init() {
    userValidator = new UserValidatorImpl(userService);
  }

  @Test
  public void testValidateSaveUser_whenEditUserAndUserNotFound_thenThrowNotFoundException() {
    // Given
    UserDto userDto = UserDto.builder().username("tester").id(1L).build();

    // When
    when(userService.findByIds(Collections.singletonList(1L))).thenReturn(new ArrayList<>());

    // Then
    assertThrows(NotFoundException.class, () -> {
      userValidator.validateSaveUser(userDto);
    });
  }

  @Test
  public void testValidateSaveUser_whenEditUsername_thenThrowUnsupportedException() {
    // Given
    UserDto userDto = UserDto.builder().id(1L).username("testtesttest").build();

    // When
    when(userService.findByIds(Collections.singletonList(1L))).thenReturn(
        Collections.singletonList(UserDto.builder().username("testest").build()));

    // Then
    assertThrows(UnsupportedOperationException.class, () -> {
      userValidator.validateSaveUser(userDto);
    });
  }

  @Test
  public void testValidateSaveUser_whenEditAdminUser_thenThrowUnsupportedException() {
    // Given
    UserDto userDto = UserDto.builder().id(1L).username("admin").role(UserRole.MEMBER).build();

    // Then
    assertThrows(UnsupportedOperationException.class, () -> {
      userValidator.validateSaveUser(userDto);
    });
  }

  @Test
  public void testValidateSaveUser_whenCreateUserSameExistedUsername_thenThrowBadRequestException() {
    // Given
    UserDto userDto = UserDto.builder().username("testest").role(UserRole.MEMBER).build();

    // When
    when(userService.findByUsername("testest")).thenReturn(UserDto.builder().username("testest").build());

    // Then
    assertThrows(BadRequestException.class, () -> {
      userValidator.validateSaveUser(userDto);
    });
  }

  @Test
  public void testDeleteUser_whenUserNotExist_thenThrowNotFoundException() {
    // Given
    long id = 1;

    // When
    when(userService.findByIds(Collections.singletonList(id))).thenReturn(new ArrayList<>());

    // Then
    assertThrows(NotFoundException.class, () -> {
      userValidator.validateDeleteUser(id);
    });
  }

  @Test
  public void testDeleteUser_whenDeleteAdminUser_thenThrowUnsupportedException() {
    // Given
    UserDto userDto = UserDto.builder().id(1L).username("admin").build();

    // When
    when(userService.findByIds(Collections.singletonList(1L))).thenReturn(Collections.singletonList(userDto));

    // Then
    assertThrows(UnsupportedOperationException.class, () -> {
      userValidator.validateDeleteUser(1L);
    });
  }

}