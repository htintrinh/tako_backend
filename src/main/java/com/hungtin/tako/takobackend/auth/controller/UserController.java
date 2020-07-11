package com.hungtin.tako.takobackend.auth.controller;

import com.hungtin.tako.takobackend.auth.http.UserAccountMapper;
import com.hungtin.tako.takobackend.auth.http.UserDetailResponse;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private final AuthService authService;
  private final UserAccountMapper userAccountMapper;

  @GetMapping("/me")
  @Transactional
  public ResponseEntity<UserDetailResponse> me() {
    UserAccount userAccount = authService.getCurrentUser();

    return ResponseEntity.ok(userAccountMapper.transform(userAccount));
  }
}
