package com.pet.management.tracker.controller;


import com.pet.management.tracker.exception.NotFoundException;
import com.pet.management.tracker.model.dto.AuthRequest;
import com.pet.management.tracker.util.ErrorCode;
import com.pet.management.tracker.util.JwtUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public String login(@RequestBody @Valid AuthRequest authRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

      User user = (User) authentication.getPrincipal();
      return jwtUtil.generateToken(user.getUsername(), user.getAuthorities().iterator().next().getAuthority());
    } catch (UsernameNotFoundException e) {
      throw new NotFoundException(ErrorCode.USER_NOT_FOUND, e.getMessage());
    }
  }
}
