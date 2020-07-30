package com.hungtin.tako.takobackend.auth.service;

import com.hungtin.tako.takobackend.auth.http.LoginRequest;
import com.hungtin.tako.takobackend.auth.http.LoginResponse;
import com.hungtin.tako.takobackend.auth.http.UserAccountMapper;
import com.hungtin.tako.takobackend.auth.http.UserRegisterRequest;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.model.UserToken;
import com.hungtin.tako.takobackend.auth.model.VerifiedToken;
import com.hungtin.tako.takobackend.auth.repo.UserAccountRepo;
import com.hungtin.tako.takobackend.auth.repo.UserRepo;
import com.hungtin.tako.takobackend.auth.repo.UserTokenRepo;
import com.hungtin.tako.takobackend.auth.repo.VerifiedTokenRepo;
import com.hungtin.tako.takobackend.user.User;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  @Value("${jwt.accessToken.expirationTime}")
  private Long accessTkExpTime;

  @Value("${jwt.refreshToken.expirationTime}")
  private Long refreshTkExpTime;

  private final UserAccountRepo userAccountRepo;
  private final VerifiedTokenRepo verifiedTokenRepo;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final UserTokenRepo userTokenRepo;
  private final JwtTokenService jwtTokenService;
  private final UserRepo userRepo;
  private final UserAccountMapper userAccountMapper;

  @Transactional
  public VerifiedToken makeVerifiedToken(UserAccount userAccount) {
    // create verified token
    String tokenValue = UUID.randomUUID().toString();
    VerifiedToken token =
        VerifiedToken.builder()
            .user(userAccount)
            .value(tokenValue)
            .expireAt(Instant.now().plus(Duration.ofHours(24)))
            .build();
    verifiedTokenRepo.save(token);

    return token;
  }

  @Transactional
  public boolean verifyToken(String token) {
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

  public LoginResponse loginWithJwtToken(LoginRequest request) {
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

    Authentication authenticatedToken = authenticationManager.authenticate(token);
    String username = ((UserAccount) authenticatedToken.getPrincipal()).getUsername();

    String jwtToken = jwtTokenService.generateToken(username, accessTkExpTime);
    String refreshToken = jwtTokenService.generateToken(username, refreshTkExpTime);
    // return the response
    return LoginResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
  }

  public LoginResponse refreshToken(String refreshToken) {
    String username = jwtTokenService.validateToken(refreshToken);

    String newAccessToken = jwtTokenService.generateToken(username, accessTkExpTime);
    String newRefreshToken = jwtTokenService.generateToken(username, refreshTkExpTime);

    return LoginResponse.builder()
        .accessToken(newAccessToken)
        .refreshToken(newRefreshToken).build();
  }

  public LoginResponse loginWithTokenServerBase(LoginRequest request) {
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
    // if authenticate without any exception, create the token
    UserAccount user = (UserAccount) userDetailsService.loadUserByUsername(request.getUsername());
    String tokenValue = UUID.randomUUID().toString();
    UserToken userToken = UserToken.builder().userAccount(user).value(tokenValue).build();
    userTokenRepo.save(userToken);

    // return the response
    return new LoginResponse(tokenValue, tokenValue);
  }

  @Transactional
  public UserAccount createUserAccount(UserRegisterRequest request) {
    UserAccount userAccount = userAccountMapper.transform(request);
    userAccount = userAccountRepo.save(userAccount);
    User user = User.builder().userAccount(userAccount).build();
    userRepo.save(user);
    userAccount.setUser(user);
    userAccount = userAccountRepo.save(userAccount);

    return userAccount;
  }

  public UserAccount getCurrentUser() {
    return (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
