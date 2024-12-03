package com.pet.management.tracker.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.pet.management.tracker.model.dto.PetDto;
import com.pet.management.tracker.service.PetService;
import com.pet.management.tracker.util.JwtUtil;
import com.pet.management.tracker.validator.PetValidator;
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

@WebMvcTest(PetController.class)
class PetControllerTest {

  @MockBean
  private PetService petService;

  @MockBean
  private PetValidator petValidator;

  @Autowired
  private PetController petController;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JwtUtil jwtUtil;

  @Test
  @WithMockUser
  public void testGetAllPets_thenReturnSuccessList() throws Exception {
    when(petService.findAll()).thenReturn(new ArrayList<>());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pets"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json("[]"));
  }

  @Test
  @WithMockUser
  public void testGetPetById_thenReturnSuccess() throws Exception {
    when(petService.findByIds(Collections.singletonList(1L))).thenReturn(
        Collections.singletonList(PetDto.builder().build()));
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pets/1"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json("{}"));
  }

  @Test
  @WithMockUser
  public void testGetPetById_whenNotFound_thenThrowException() throws Exception {
    when(petService.findByIds(Collections.singletonList(1L))).thenReturn(new ArrayList<>());
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/pets/1"))
        .andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content()
            .json("{\"errorCode\":\"petNotFound\",\"errorMessage\":\"Pet not found\",\"statusCode\":404}"));
  }

  @Test
  @WithMockUser
  public void testCreatePets_thenReturnSuccessList() throws Exception {
    when(petService.createPets(any())).thenReturn(new ArrayList<>());
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/pets").content("\n"
            + "  {\n"
            + "    \"pets\": [\n"
            + "        {\n"
            + "            \"id\": 5,\n"
            + "            \"name\": \"bap\",\n"
            + "            \"age\": 8,\n"
            + "            \"weight\": 7.8,\n"
            + "            \"type\": \"DOG\",\n"
            + "            \"medicalNote\": \"vaccinated\",\n"
            + "            \"ownerId\": 6\n"
            + "        }\n"
            + "    ]\n"
            + "   \n"
            + "  }\n").contentType(MediaType.APPLICATION_JSON).with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json("[]"));
  }

  @Test
  @WithMockUser
  public void testDeletePet_thenSuccess() throws Exception {
    when(petService.findAll()).thenReturn(new ArrayList<>());
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/pets/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

}