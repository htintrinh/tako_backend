package com.hungtin.tako.takobackend.user.service;

import com.hungtin.tako.takobackend.auth.http.UserAccountMapper;
import com.hungtin.tako.takobackend.auth.http.UserDetailResponse;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.repo.UserRepo;
import com.hungtin.tako.takobackend.auth.service.AuthService;
import com.hungtin.tako.takobackend.user.User;
import com.hungtin.tako.takobackend.user.http.UserRequest;
import com.hungtin.tako.takobackend.user.http.UserResponse;
import com.hungtin.tako.takobackend.user.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

  private final AuthService authService;
  private final UserMapper userMapper;
  private final UserAccountMapper userAccountMapper;
  private final UserRepo userRepo;

  public UserResponse updateCurrentUser(UserRequest request) {
    User user = authService.getCurrentUser().getUser();
    user = userMapper.update(user, request);
    user = userRepo.save(user);
    return userMapper.transform(user);
  }

  public UserDetailResponse me() {
    UserAccount userAccount = authService.getCurrentUser();
    return userAccountMapper.transform(userAccount);
  }

}
