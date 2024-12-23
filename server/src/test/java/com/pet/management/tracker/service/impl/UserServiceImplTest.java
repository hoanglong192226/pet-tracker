package com.pet.management.tracker.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pet.management.tracker.converter.UserConverter;
import com.pet.management.tracker.exception.NotFoundException;
import com.pet.management.tracker.model.UserRole;
import com.pet.management.tracker.model.dto.UserDto;
import com.pet.management.tracker.model.entity.User;
import com.pet.management.tracker.repository.UserRepository;
import com.pet.management.tracker.service.UserService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  private final UserConverter userConverter = new UserConverter();

  private UserService userService;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private EntityManager entityManager;

  @BeforeEach
  public void init() {
    userService = new UserServiceImpl(userRepository, userConverter, passwordEncoder, entityManager);
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
  public void testCreateUsers_thenSuccess() {
    // Given
    List<User> users = new ArrayList<>();
    User user = new User();
    user.setRole(UserRole.ADMIN.getValue());
    users.add(user);

    // When
    Mockito.when(userRepository.saveAll(any())).thenReturn(users);
    List<UserDto> userDtos = userService.createUsers(
        Collections.singletonList(UserDto.builder().role(UserRole.MEMBER).password("testtest").build()));

    // Then
    verify(passwordEncoder).encode("testtest");
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
    Mockito.when(userRepository.findAllById(any())).thenReturn(users);
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
    verify(userRepository, Mockito.times(1)).deleteIfExist(id);
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

  @Test
  public void testUpdateUser_whenNoUserId_thenThrowNotFoundException() {
    // Given
    UserDto userDto = UserDto.builder().build();

    // Then
    assertThrows(NotFoundException.class, () -> {
      userService.updateUser(userDto);
    });
  }

  @Test
  public void testUpdateUser_whenNewPasswordAndRole_thenSuccess() {
    // Given
    UserDto userDto = UserDto.builder().id(1L).password("newpassword").role(UserRole.MEMBER).build();

    User user = new User();
    user.setId(1L);
    user.setPassword("oldpassword");
    user.setRole(UserRole.ADMIN.getValue());

    User savedUser = new User();
    savedUser.setId(1L);
    savedUser.setPassword("newpasswordencoded");
    savedUser.setRole(UserRole.MEMBER.getValue());

    // When
    when(entityManager.find(User.class, 1L)).thenReturn(user);
    when(passwordEncoder.encode("newpassword")).thenReturn("newpasswordencoded");

    doReturn(savedUser).when(entityManager).merge(any(User.class));

    UserDto result = userService.updateUser(userDto);

    // Then
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(entityManager).merge(captor.capture());

    User capturedUser = captor.getValue();
    assertEquals(savedUser.getId(), capturedUser.getId());
    assertEquals(savedUser.getPassword(), capturedUser.getPassword());
    assertEquals(savedUser.getRole(), capturedUser.getRole());

    assertNull(result.getPassword());

  }
}