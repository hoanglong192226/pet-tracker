package com.pet.management.tracker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.pet.management.tracker.model.dto.OwnerDto;
import com.pet.management.tracker.service.OwnerService;
import com.pet.management.tracker.util.JwtUtil;
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

@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JwtUtil jwtUtil;

  @MockBean
  private OwnerService ownerService;


  @Test
  @WithMockUser
  public void testGetAllOwners_thenReturnSuccessList() throws Exception {
    when(ownerService.findAll()).thenReturn(new ArrayList<>());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/owners"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json("[]"));
  }

  @Test
  @WithMockUser
  public void testGetOwnerById_thenReturnSuccess() throws Exception {
    when(ownerService.findByIds(Collections.singletonList(1L))).thenReturn(
        Collections.singletonList(OwnerDto.builder().build()));
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/owners/1"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json("{}"));
  }

  @Test
  @WithMockUser
  public void testGetOwnerById_whenNotFound_thenThrowException() throws Exception {
    when(ownerService.findByIds(Collections.singletonList(1L))).thenReturn(new ArrayList<>());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/owners/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content()
            .json("{\"errorCode\":\"ownerNotFound\",\"errorMessage\":\"Owner not found\",\"statusCode\":404}"));
  }

  @Test
  @WithMockUser
  public void testCreateOwners_thenReturnSuccessList() throws Exception {
    when(ownerService.createOwners(any())).thenReturn(new ArrayList<>());
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/owners").content("\n"
            + "  {\n"
            + "    \"owners\": [\n"
            + "        {\n"
            + "            \"name\": \"Long\",\n"
            + "            \"phone\": \"0903510089\"\n"
            + "        }\n"
            + "    ]\n"
            + "   \n"
            + "  }\n").contentType(MediaType.APPLICATION_JSON).with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json("[]"));
  }

  @Test
  @WithMockUser
  public void testDeleteOwners_thenSuccess() throws Exception {
    when(ownerService.findAll()).thenReturn(new ArrayList<>());
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/owners/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
