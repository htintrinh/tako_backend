package com.hungtin.tako.takobackend.auth.controller;

import com.hungtin.tako.takobackend.auth.http.LoginRequest;
import com.hungtin.tako.takobackend.auth.http.LoginResponse;
import com.hungtin.tako.takobackend.auth.http.UserAccountMapper;
import com.hungtin.tako.takobackend.auth.http.UserDetailResponse;
import com.hungtin.tako.takobackend.auth.http.UserRegisterRequest;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.service.AuthService;
import com.hungtin.tako.takobackend.mail.model.MailNotification;
import com.hungtin.tako.takobackend.mail.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final MailService mailService;
  private final AuthService authService;
  private final UserAccountMapper userAccountMapper;

  @PostMapping("/register")
  public ResponseEntity<UserDetailResponse> register(@RequestBody UserRegisterRequest request) {
    UserAccount userAccount = authService.createUserAccount(request);
    String tokenValue = authService.makeVerifiedToken(userAccount).getValue();

    mailService.sendMail(
        new MailNotification(
            "Verify Code",
            "Click this link to verify you account:"
                + "http://localhost:8080/auth/confirm-code/"
                + tokenValue,
            userAccount.getEmail()));

    return ResponseEntity.ok(userAccountMapper.transform(userAccount));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

    return ResponseEntity.ok(authService.loginWithJwtToken(req));
  }

  @GetMapping("/confirm-code/{token}")
  public ResponseEntity<String> authVerificationCode(@PathVariable String token) {
    if (authService.verifyToken(token)) {
      return ResponseEntity.ok("Authentication successfully");
    }

    return new ResponseEntity<>("Failed to authorize the code", HttpStatus.UNAUTHORIZED);
  }
}
