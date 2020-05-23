package com.hungtin.tako.takobackend.auth.repo;

import com.hungtin.tako.takobackend.auth.model.UserAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepo extends JpaRepository<UserAccount, Long> {

  Optional<UserAccount> findByUsername(String username);

}
