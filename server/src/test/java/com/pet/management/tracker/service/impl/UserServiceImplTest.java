package com.pet.management.tracker.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.pet.management.tracker.converter.UserConverter;
import com.pet.management.tracker.model.UserRole;
import com.pet.management.tracker.model.dto.UserDto;
import com.pet.management.tracker.model.entity.User;
import com.pet.management.tracker.repository.UserRepository;
import com.pet.management.tracker.service.UserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  private final UserConverter userConverter = new UserConverter();

  private UserService userService;

  @BeforeEach
  public void init() {
    userService = new UserServiceImpl(userRepository, userConverter);
  }

  @Test
  public void testFindAll_thenReturnSuccessList() {
    // Given
    List<User> users = new ArrayList<>();
    User user = new User();
    user.setRole(UserRole.ADMIN.getValue());
    users.add(user);

    //When
    Mockito.when(userRepository.findAll()).thenReturn(users);
    List<UserDto> userDtos = userService.findAll();

    // Then
    assertFalse(userDtos.isEmpty());
  }

  @Test
  public void testCreateUSers_thenSuccess() {
    // Given
    List<User> users = new ArrayList<>();
    User user = new User();
    user.setRole(UserRole.ADMIN.getValue());
    users.add(user);

    // When
    Mockito.when(userRepository.saveAll(Mockito.any())).thenReturn(users);
    List<UserDto> userDtos = userService.createUsers(new ArrayList<>());

    // Then
    assertFalse(userDtos.isEmpty());
  }

  @Test
  public void testFindByIds_thenReturnSuccessList() {
    // Given
    List<User> users = new ArrayList<>();
    User user = new User();
    user.setRole(UserRole.ADMIN.getValue());
    users.add(user);

    // When
    Mockito.when(userRepository.findAllById(Mockito.any())).thenReturn(users);
    List<UserDto> userDtos = userService.findByIds(new ArrayList<>());

    // Then
    assertFalse(userDtos.isEmpty());
  }

  @Test
  public void testDeleteUser_thenSuccess() {
    // Given
    Long id = 10L;

    // When
    Mockito.doNothing().when(userRepository).deleteIfExist(id);
    userService.deleteUser(id);

    // Then
    Mockito.verify(userRepository, Mockito.times(1)).deleteIfExist(id);
  }

  @Test
  public void testFindByUsername_thenSuccess() {
    // Given
    String username = "test";

    List<User> users = new ArrayList<>();
    User user = new User();
    user.setRole(UserRole.ADMIN.getValue());
    user.setUsername(username);
    users.add(user);

    // When
    Mockito.when(userRepository.findByUsername("test")).thenReturn(users);
    UserDto userDto = userService.findByUsername(username);

    // Then
    Assertions.assertEquals(userDto.getUsername(), username);
    Assertions.assertNull(userDto.getPassword());
    Assertions.assertEquals(userDto.getRole().getValue(), UserRole.ADMIN.getValue());
  }

  @Test
  public void testFindByUsername_whenUserIsEmpty_thenSuccess() {
    // Given
    String username = "test";

    List<User> users = new ArrayList<>();

    // When
    Mockito.when(userRepository.findByUsername("test")).thenReturn(users);
    UserDto userDto = userService.findByUsername(username);

    // Then
    Assertions.assertNull(userDto);
  }
}