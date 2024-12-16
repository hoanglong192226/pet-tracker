package com.pet.management.tracker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.pet.management.tracker.model.dto.UserDto;
import com.pet.management.tracker.service.UserService;
import com.pet.management.tracker.util.JwtUtil;
import com.pet.management.tracker.validator.UserValidator;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private JwtUtil jwtUtil;

  @MockBean
  private UserValidator userValidator;

  @Test
  @WithMockUser
  public void testGetAllUsers_thenReturnSuccessList() throws Exception {
    when(userService.findAll()).thenReturn(new ArrayList<>());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/users"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json("[]"));
  }

  @Test
  @WithMockUser
  public void testGetUserById_thenReturnSuccess() throws Exception {
    when(userService.findByIds(Collections.singletonList(1L))).thenReturn(
        Collections.singletonList(UserDto.builder().build()));
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/users/1"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json("{}"));
  }

  @Test
  @WithMockUser
  public void testGetUserById_whenNotFound_thenThrowException() throws Exception {
    when(userService.findByIds(Collections.singletonList(1L))).thenReturn(new ArrayList<>());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/users/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content()
            .json("{\"errorCode\":\"userNotFound\",\"errorMessage\":\"User not found\",\"statusCode\":404}"));
  }

  @Test
  @WithMockUser
  public void testCreateUsers_thenReturnSuccessList() throws Exception {
    when(userService.createUsers(any())).thenReturn(
        Collections.singletonList(UserDto.builder().id(1L).username("tester").build()));
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/users").content("  {\n"
            + "    \"username\": \"tester\",\n"
            + "    \"password\": \"testtest\",\n"
            + "    \"role\": \"member\"  \n"
            + "  }\n").contentType(MediaType.APPLICATION_JSON).with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("{\"username\":\"tester\"}"));
  }

  @Test
  @WithMockUser
  public void testDeleteUsers_thenSuccess() throws Exception {
    when(userService.findAll()).thenReturn(new ArrayList<>());
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/admin/users/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

}