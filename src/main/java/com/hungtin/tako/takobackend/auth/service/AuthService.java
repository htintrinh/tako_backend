package com.hungtin.tako.takobackend.auth.service;

import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.model.VerifiedToken;
import com.hungtin.tako.takobackend.auth.repo.UserAccountRepo;
import com.hungtin.tako.takobackend.auth.repo.VerifiedTokenRepo;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

  private final UserAccountRepo userAccountRepo;
  private final VerifiedTokenRepo verifiedTokenRepo;

  public VerifiedToken makeVerifiedToken(UserAccount userAccount) {
    // create verified token
    String tokenValue = UUID.randomUUID().toString();
    VerifiedToken token = VerifiedToken.builder()
        .user(userAccount)
        .value(tokenValue)
        .expireAt(Instant.now().plus(Duration.ofHours(24)))
        .build();
    verifiedTokenRepo.save(token);

    return token;
  }

  public boolean isValidToken(String token) {
    Optional<VerifiedToken> verifiedToken = verifiedTokenRepo.findByValue(token);
    if (verifiedToken.isEmpty()) {
      return false;
    }

    UserAccount user = verifiedToken.get().getUser();
    if (Objects.isNull(user)) {
      return false;
    }
    user.setEnable(true);
    userAccountRepo.save(user);
    verifiedTokenRepo.deleteById(verifiedToken.get().getId());

    return true;
  }

}
