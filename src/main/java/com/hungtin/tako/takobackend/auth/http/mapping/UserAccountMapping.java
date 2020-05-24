package com.hungtin.tako.takobackend.auth.http.mapping;

import com.hungtin.tako.takobackend.auth.http.UserAccountDetailResponse;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import org.springframework.stereotype.Service;

@Service
public class UserAccountMapping {

  public UserAccountDetailResponse transform(UserAccount user) {
    return UserAccountDetailResponse.builder().username(user.getUsername())
        .email(user.getEmail())
        .phone(user.getPhone())
        .build();
  }

}
