package com.hungtin.tako.takobackend.auth.http;

import com.hungtin.tako.takobackend.auth.model.UserAccount;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAccountMapper {

  private final PasswordEncoder passwordEncoder;

  public UserDetailResponse transform(UserAccount user) {
    return UserDetailResponse.builder()
        .username(user.getUsername())
        .email(user.getEmail())
        .phone(user.getPhone())
        .name(user.getUser().getName())
        .dob(user.getUser().getDob())
        .gender(user.getUser().getGender())
        .build();
  }

  public UserAccount transform(UserRegisterRequest request) {
    return UserAccount.builder()
        .username(request.getUsername())
        .password(passwordEncoder.encode(request.getPassword()))
        .isEnable(false)
        .email(request.getEmail())
        .build();
  }
}
