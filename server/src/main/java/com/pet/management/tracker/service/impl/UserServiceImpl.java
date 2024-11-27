package com.pet.management.tracker.service.impl;

import com.pet.management.tracker.converter.UserConverter;
import com.pet.management.tracker.model.dto.UserDto;
import com.pet.management.tracker.model.entity.User;
import com.pet.management.tracker.repository.UserRepository;
import com.pet.management.tracker.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserConverter userConverter;

  @Override
  public List<UserDto> findAll() {
    return userRepository.findAll().stream().map(userConverter::toDto).collect(Collectors.toList());
  }

  @Override
  public List<UserDto> createUsers(List<UserDto> userDtos) {
    List<User> users = userDtos.stream().map(userConverter::fromDto).collect(Collectors.toList());
    List<User> savedUsers = userRepository.saveAll(users);

    return savedUsers.stream().map(userConverter::toDto).collect(Collectors.toList());
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

    if(users.isEmpty()) {
      return null;
    }

    return userConverter.toDto(users.get(0));
  }
}
