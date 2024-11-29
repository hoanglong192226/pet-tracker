package com.pet.management.tracker.service.impl;

import com.pet.management.tracker.model.entity.User;
import com.pet.management.tracker.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<User> users = userRepository.findByUsername(username);
    if (users.isEmpty()) {
      throw new UsernameNotFoundException("User not found");
    }
    User user = users.get(0);
    return org.springframework.security.core.userdetails.User.builder().username(user.getUsername())
        .password(user.getPassword()).roles(user.getRole()).build();
  }
}
