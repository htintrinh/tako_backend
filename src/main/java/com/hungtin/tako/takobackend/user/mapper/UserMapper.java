package com.hungtin.tako.takobackend.user.mapper;

import com.hungtin.tako.takobackend.user.User;
import com.hungtin.tako.takobackend.user.http.UserRequest;
import com.hungtin.tako.takobackend.user.http.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
  public UserResponse transform(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .dob(user.getDob())
        .gender(user.getGender().toString())
        .phone(user.getPhone())
        .build();
  }

  public User update(User user, UserRequest req) {
    user.setDob(req.getDob());
    user.setName(req.getName());
    user.setGender(req.getGender());
    user.setPhone(req.getPhone());
    return user;
  }
}
