package com.pet.management.tracker.service.impl;

import com.pet.management.tracker.model.UserRole;
import com.pet.management.tracker.model.entity.User;
import com.pet.management.tracker.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserDetailsServiceImpl userDetailsService;

  @Test
  public void testLoadUserByUsername_thenReturnSuccess() {
    // Given
    String username = "test";
    List<User> users = new ArrayList<>();
    User user = new User(1L, username, "password", UserRole.ADMIN.getValue());
    users.add(user);

    // When
    Mockito.when(userRepository.findByUsername(username)).thenReturn(users);
    org.springframework.security.core.userdetails.UserDetails userDetail = userDetailsService.loadUserByUsername(
        username);

    // Then
    Assertions.assertEquals(userDetail.getUsername(), username);
    Assertions.assertEquals(userDetail.getPassword(), "password");
    Assertions.assertFalse(userDetail.getAuthorities().isEmpty());
  }

  @Test
  public void testLoadUserByUsername_whenUserNotFound_thenThrowException() {
    // Given
    String username = "test";

    // When
    Mockito.when(userRepository.findByUsername(username)).thenReturn(new ArrayList<>());

    // Then
    Assertions.assertThrows(UsernameNotFoundException.class, () -> {
      userDetailsService.loadUserByUsername(
          username);
    });
  }

}