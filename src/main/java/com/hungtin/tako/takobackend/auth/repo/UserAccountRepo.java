package com.hungtin.tako.takobackend.auth.repo;

import com.hungtin.tako.takobackend.auth.model.UserAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, Long> {

  @EntityGraph(attributePaths = {"user"})
  Optional<UserAccount> findByUsername(String username);
}
