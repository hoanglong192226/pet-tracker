package com.pet.management.tracker.service.impl;

import com.pet.management.tracker.converter.UserConverter;
import com.pet.management.tracker.exception.ApplicationException;
import com.pet.management.tracker.exception.NotFoundException;
import com.pet.management.tracker.model.dto.UserDto;
import com.pet.management.tracker.model.entity.User;
import com.pet.management.tracker.repository.UserRepository;
import com.pet.management.tracker.service.UserService;
import com.pet.management.tracker.util.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserConverter userConverter;
  private final PasswordEncoder passwordEncoder;
  private final EntityManager entityManager;

  @Override
  public List<UserDto> findAll() {
    return userRepository.findAll().stream().map(userConverter::toDto).collect(Collectors.toList());
  }

  @Override
  public List<UserDto> createUsers(List<UserDto> userDtos) {
    List<User> users = userDtos.stream().map(userConverter::fromDto).collect(Collectors.toList());
    users.forEach((user -> {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
    }));
    List<User> savedUsers = userRepository.saveAll(users);

    return savedUsers.stream().map(userConverter::toDto).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public UserDto updateUser(UserDto userDto) {
    if (userDto.getId() == null) {
      throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "UserId is empty");
    }

    User user = entityManager.find(User.class, userDto.getId());

    if (user == null) {
      throw new NotFoundException(ErrorCode.USER_NOT_FOUND, "User not found");
    }

    if (userDto.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }

    if (userDto.getRole() != null) {
      user.setRole(userDto.getRole().getValue());
    }

    User savedUser = entityManager.merge(user);

    return userConverter.toDto(savedUser);
  }

  @Override
  public List<UserDto> findByIds(List<Long> ids) {
    return userRepository.findAllById(ids).stream().map(userConverter::toDto).collect(Collectors.toList());
  }


  @Override
  @Transactional
  public void deleteUser(Long id) {
    userRepository.deleteIfExist(id);
  }

  @Override
  public UserDto findByUsername(String username) {
    List<User> users = userRepository.findByUsername(username);

    if (users.isEmpty()) {
      return null;
    }

    return userConverter.toDto(users.get(0));
  }
}
