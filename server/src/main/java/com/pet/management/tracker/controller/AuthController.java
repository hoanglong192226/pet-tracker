package com.pet.management.tracker.controller;


import static com.pet.management.tracker.util.ErrorCode.UNAUTHENTICATED;

import com.pet.management.tracker.exception.NotFoundException;
import com.pet.management.tracker.exception.UnauthenticatedException;
import com.pet.management.tracker.model.dto.AuthRequest;
import com.pet.management.tracker.model.dto.ProfileDto;
import com.pet.management.tracker.util.CookiesUtil;
import com.pet.management.tracker.util.ErrorCode;
import com.pet.management.tracker.util.JwtUtil;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody @Valid AuthRequest authRequest) {
    try {
      SecurityContextHolder.clearContext();
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

      User user = (User) authentication.getPrincipal();
      String token = jwtUtil.generateToken(user.getUsername(), user.getAuthorities().iterator().next().getAuthority());

      ResponseCookie cookie = ResponseCookie.from(CookiesUtil.TOKEN_COOKIE, token).httpOnly(true)
          .sameSite(SameSite.STRICT.attributeValue()).path("/")
          .maxAge(CookiesUtil.TOKEN_COOKIE_MAX_AGE).build();

      log.info("Auth login user {} successfully", authRequest.getUsername());
      return ResponseEntity.ok()
          .header(HttpHeaders.SET_COOKIE, cookie.toString())
          .body(token);
    } catch (BadCredentialsException | UsernameNotFoundException e) {
      throw new UnauthenticatedException(UNAUTHENTICATED, e.getMessage());
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout() {
    SecurityContextHolder.clearContext();

    ResponseCookie clearCookie = ResponseCookie.from(CookiesUtil.TOKEN_COOKIE, "")
        .httpOnly(true)
        .sameSite(SameSite.STRICT.attributeValue())
        .path("/")
        .maxAge(0)
        .build();

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, clearCookie.toString()).body("");
  }

  @GetMapping("/profile")
  public ProfileDto getLoginUserProfile(@CookieValue(name = CookiesUtil.TOKEN_COOKIE) String token) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      throw new UnauthenticatedException(UNAUTHENTICATED, "User not login");
    }
    long expiredAt = jwtUtil.extractClaims(token).getExpiration().getTime();

    User user = (User) authentication.getPrincipal();

    return ProfileDto.builder().username(user.getUsername())
        .role(user.getAuthorities().iterator().next().getAuthority()).expiredAt(expiredAt).build();

  }
}
