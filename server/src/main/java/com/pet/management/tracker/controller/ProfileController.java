package com.pet.management.tracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

  @GetMapping("/test")
  public String test() {
    return "test";
  }
}
