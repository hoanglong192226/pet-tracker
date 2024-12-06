package com.pet.management.tracker.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pet.management.tracker.util.JwtUtil;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

  @MockBean
  private AuthenticationManager authenticationManager;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JwtUtil jwtUtil;

  @Autowired
  private AuthController authController;

  @Test
  @WithMockUser
  public void testLogin_thenSuccess() throws Exception {
    Authentication authentication = mock(Authentication.class);
    User user = new User("username", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    when(authenticationManager.authenticate(any())).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(user);
    when(jwtUtil.generateToken("username", "ROLE_USER")).thenReturn("mockJwtToken");

    mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"username\", \"password\":\"password\"}").with(csrf()))
        .andExpect(status().isOk())
        .andExpect(header().exists(HttpHeaders.SET_COOKIE))
        .andExpect(content().string("mockJwtToken"));
  }

  @Test
  @WithMockUser
  public void testLogin_whenUserNotExist_thenThrowUnauthenticatedException() throws Exception {
    Authentication authentication = mock(Authentication.class);
    User user = new User("username", "password", List.of(new SimpleGrantedAuthority("ROLE_USER")));
    when(authenticationManager.authenticate(any())).thenThrow(mock(UsernameNotFoundException.class));

    mockMvc.perform(post("/api/v1/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"username\":\"username\", \"password\":\"password\"}").with(csrf()))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  public void testLogout_thenSuccess() throws Exception {
    ResultActions result = mockMvc.perform(post("/api/v1/auth/logout")
            .contentType(MediaType.APPLICATION_JSON).with(csrf()))
        .andExpect(status().isOk())
        .andExpect(header().exists(HttpHeaders.SET_COOKIE))
        .andExpect(content().string(""));

    String cookieHeader = result.andReturn().getResponse().getHeader(HttpHeaders.SET_COOKIE);
    assertNotNull(cookieHeader);
    assertTrue(cookieHeader.contains("Max-Age=0"));
  }
}