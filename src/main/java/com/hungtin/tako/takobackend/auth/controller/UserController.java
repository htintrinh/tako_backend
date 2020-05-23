package com.hungtin.tako.takobackend.auth.controller;

import com.hungtin.tako.takobackend.auth.http.UserRegisterMapping;
import com.hungtin.tako.takobackend.auth.http.UserRegisterRequest;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.repo.UserAccountRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private final UserRegisterMapping userRegisterMapping;
  private final UserAccountRepo userAccountRepo;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody UserRegisterRequest request) {
    UserAccount userAccount = userRegisterMapping.transform(request);
    userAccountRepo.save(userAccount);

    return ResponseEntity.ok("User registered");
  }

}
