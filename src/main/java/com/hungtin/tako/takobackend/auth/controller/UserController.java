package com.hungtin.tako.takobackend.auth.controller;

import com.hungtin.tako.takobackend.auth.http.UserAccountDetailResponse;
import com.hungtin.tako.takobackend.auth.http.mapping.UserAccountMapping;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.repo.UserAccountRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private final UserAccountRepo userAccountRepo;
  private final UserAccountMapping userAccountMapping;

  @GetMapping("/me")
  public ResponseEntity<UserAccountDetailResponse> me() {
    String username = (String) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
    UserAccount user = userAccountRepo.findByUsername(username).orElseThrow();

    UserAccountDetailResponse response = userAccountMapping.transform(user);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
