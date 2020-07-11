package com.hungtin.tako.takobackend.auth.repo;

import com.hungtin.tako.takobackend.auth.model.VerifiedToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifiedTokenRepo extends JpaRepository<VerifiedToken, Long> {

  Optional<VerifiedToken> findByValue(String value);
}
