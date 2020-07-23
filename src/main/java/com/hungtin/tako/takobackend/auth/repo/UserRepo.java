package com.hungtin.tako.takobackend.auth.repo;

import com.hungtin.tako.takobackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

}
