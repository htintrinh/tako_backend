package com.hungtin.tako.takobackend.auth.service;

import com.hungtin.tako.takobackend.auth.repo.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  private final UserAccountRepo userAccountRepo;

  @Autowired
  public UserDetailServiceImpl(UserAccountRepo userAccountRepo) {
    this.userAccountRepo = userAccountRepo;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userAccountRepo.findByUsername(username).orElse(null);
  }
}
