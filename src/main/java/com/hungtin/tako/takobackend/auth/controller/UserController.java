package com.hungtin.tako.takobackend.auth.controller;

import com.hungtin.tako.takobackend.auth.http.LoginRequest;
import com.hungtin.tako.takobackend.auth.http.LoginResponse;
import com.hungtin.tako.takobackend.auth.http.UserAccountDetailResponse;
import com.hungtin.tako.takobackend.auth.http.UserRegisterRequest;
import com.hungtin.tako.takobackend.auth.http.mapping.UserAccountMapping;
import com.hungtin.tako.takobackend.auth.http.mapping.UserRegisterMapping;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.model.UserToken;
import com.hungtin.tako.takobackend.auth.model.VerifiedToken;
import com.hungtin.tako.takobackend.auth.repo.UserAccountRepo;
import com.hungtin.tako.takobackend.auth.repo.UserTokenRepo;
import com.hungtin.tako.takobackend.auth.repo.VerifiedTokenRepo;
import com.hungtin.tako.takobackend.mail.model.MailNotification;
import com.hungtin.tako.takobackend.mail.service.MailService;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final VerifiedTokenRepo verifiedTokenRepo;
  private final MailService mailService;

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody UserRegisterRequest request) {
    UserAccount userAccount = userRegisterMapping.transform(request);
    userAccountRepo.saveAndFlush(userAccount);

    // create verified token
    String tokenValue = UUID.randomUUID().toString();
    VerifiedToken token = VerifiedToken.builder()
        .user(userAccount)
        .value(tokenValue)
        .expireAt(Instant.now().plus(Duration.ofHours(24)))
        .build();
    verifiedTokenRepo.save(token);
    mailService.sendMail(new MailNotification(
        "Verify Code",
        "Click this link to verify you account:"
            + "http://localhost:8080/user/auth-code/" + tokenValue,
        userAccount.getEmail()));

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

  @GetMapping("/auth-code/{token}")
  @Transactional
  public ResponseEntity<String> authVerificationCode(@PathVariable String token) {
    Optional<VerifiedToken> verifiedToken = verifiedTokenRepo.findByValue(token);
    if (verifiedToken.isEmpty()) {
      return new ResponseEntity<>("Not found token", HttpStatus.NOT_FOUND);
    }

    UserAccount user = verifiedToken.get().getUser();
    user.setEnable(true);
    userAccountRepo.save(user);
    verifiedTokenRepo.deleteById(verifiedToken.get().getId());

    return ResponseEntity.ok("Authentication successfully");
  }

  @GetMapping("/detail")
  public ResponseEntity<UserAccountDetailResponse> detail() {
    String username = (String) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();

    UserAccount user = userAccountRepo.findByUsername(username).orElseThrow();
    return ResponseEntity.ok(userAccountMapping.transform(user));
  }

}
