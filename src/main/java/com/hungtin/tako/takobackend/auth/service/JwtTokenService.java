package com.hungtin.tako.takobackend.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenService {

  @Value("${jwt.secret}")
  private String secret;

  private static final int EXPIRATION_DURATION = 60 * 60;

  public String generateToken(Authentication authentication) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    // The principle that we get from the the authentication
    // is not the username but the user object that we provide
    // to the implementation of UserDetailsService
    UserAccount userAccount = (UserAccount) authentication.getPrincipal();

    return JWT.create()
        .withExpiresAt(Date.from(Instant.now().plusSeconds(EXPIRATION_DURATION)))
        .withClaim("username", userAccount.getUsername())
        .sign(algorithm);
  }

  public String validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    JWTVerifier verifier = JWT.require(algorithm).build();
    verifier.verify(token);

    DecodedJWT decodedJWT = JWT.decode(token);
    return decodedJWT.getClaim("username").asString();
  }


}
