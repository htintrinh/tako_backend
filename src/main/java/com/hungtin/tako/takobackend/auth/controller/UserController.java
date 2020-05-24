package com.hungtin.tako.takobackend.auth.controller;

import com.hungtin.tako.takobackend.auth.http.LoginRequest;
import com.hungtin.tako.takobackend.auth.http.LoginResponse;
import com.hungtin.tako.takobackend.auth.http.UserAccountDetailResponse;
import com.hungtin.tako.takobackend.auth.http.UserRegisterRequest;
import com.hungtin.tako.takobackend.auth.http.mapping.UserAccountMapping;
import com.hungtin.tako.takobackend.auth.http.mapping.UserRegisterMapping;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.model.UserToken;
import com.hungtin.tako.takobackend.auth.repo.UserAccountRepo;
import com.hungtin.tako.takobackend.auth.repo.UserTokenRepo;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

  private final UserRegisterMapping userRegisterMapping;
  private final UserAccountMapping userAccountMapping;

  private final UserAccountRepo userAccountRepo;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final UserTokenRepo tokenRepo;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody UserRegisterRequest request) {
    UserAccount userAccount = userRegisterMapping.transform(request);
    userAccountRepo.save(userAccount);

    return ResponseEntity.ok("User registered");
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
    UserAccount user = (UserAccount) userDetailsService.loadUserByUsername(req.getUsername());
    String tokenValue = UUID.randomUUID().toString();
    UserToken token = UserToken.builder().userAccount(user).value(tokenValue).build();
    tokenRepo.save(token);

    return ResponseEntity.ok(LoginResponse.builder().tokenValue(tokenValue).build());
  }

  @GetMapping("/detail")
  public ResponseEntity<UserAccountDetailResponse> detail() {
    String username = (String) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();

    UserAccount user = userAccountRepo.findByUsername(username).orElseThrow();
    return ResponseEntity.ok(userAccountMapping.transform(user));
  }

}
