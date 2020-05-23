package com.hungtin.tako.takobackend.auth.repo;

import com.hungtin.tako.takobackend.auth.model.UserToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepo extends JpaRepository<UserToken, Long> {

  Optional<UserToken> findOneByValue(String value);
}
