package com.pet.management.tracker.service;

import com.pet.management.tracker.model.dto.UserDto;
import java.util.List;

public interface UserService {

  List<UserDto> findAll();

  void deleteUser(Long id);

  List<UserDto> createUsers(List<UserDto> userDtos);

  List<UserDto> findByIds(List<Long> ids);

  UserDto findByUsername(String username);
}
