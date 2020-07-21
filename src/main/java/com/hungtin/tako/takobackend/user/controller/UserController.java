package com.hungtin.tako.takobackend.user.controller;

import com.hungtin.tako.takobackend.auth.http.UserDetailResponse;
import com.hungtin.tako.takobackend.user.http.UserRequest;
import com.hungtin.tako.takobackend.user.http.UserResponse;
import com.hungtin.tako.takobackend.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<UserDetailResponse> me() {
    return ResponseEntity.ok(userService.me());
  }

  @PostMapping("/me")
  public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest req) {
    return ResponseEntity.ok(userService.updateCurrentUser(req));
  }
}
