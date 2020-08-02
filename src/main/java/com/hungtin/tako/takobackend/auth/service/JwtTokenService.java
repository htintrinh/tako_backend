package com.hungtin.tako.takobackend.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenService {

  private static final int EXPIRATION_DURATION = 24 * 60 * 60;

  @Value("${jwt.secret}")
  private String secret;


  public String generateToken(String username, Long expirationTime) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create()
        .withExpiresAt(Date.from(Instant.now().plusSeconds(expirationTime)))
        .withClaim("username", username)
        .sign(algorithm);
  }

  public String validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    JWTVerifier verifier = JWT.require(algorithm).build();
    try {
      verifier.verify(token);
    } catch(JWTVerificationException e) {
      // if verify not success return empty string as username
      return "";
    }

    DecodedJWT decodedJWT = JWT.decode(token);
    return decodedJWT.getClaim("username").asString();
  }
}
