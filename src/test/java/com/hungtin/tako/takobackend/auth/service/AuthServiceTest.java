package com.hungtin.tako.takobackend.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.hungtin.tako.takobackend.auth.repo.UserAccountRepo;
import com.hungtin.tako.takobackend.auth.repo.VerifiedTokenRepo;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

  @Mock
  private UserAccountRepo userAccountRepo;
  @Mock
  private VerifiedTokenRepo verifiedTokenRepo;

  @InjectMocks
  private AuthService authService;


  @Test
  void test_isValidToken_WhenTokenNotExistReturnFalse() {
    String valueToken = "123";

    given(verifiedTokenRepo.findByValue(valueToken)).willReturn(Optional.empty());
    assertThat(authService.verifyToken("123")).isEqualTo(false);

  }

}
