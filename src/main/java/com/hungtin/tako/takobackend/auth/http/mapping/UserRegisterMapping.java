package com.hungtin.tako.takobackend.auth.http.mapping;

import com.hungtin.tako.takobackend.auth.http.UserRegisterRequest;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserRegisterMapping {

  private final PasswordEncoder passwordEncoder;

  public UserAccount transform(UserRegisterRequest request) {
    return UserAccount.builder().username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .email(request.getEmail()).build();
  }

}
