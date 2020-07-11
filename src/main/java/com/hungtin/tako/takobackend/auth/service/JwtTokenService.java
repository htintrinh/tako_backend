package com.hungtin.tako.takobackend.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenService {

  @Value("${jwt.secret}")
  private String secret;

  public String generateToken(UserDetails userDetails) {
    Algorithm algorithm = Algorithm.HMAC256(secret);

    return JWT.create()
        .withExpiresAt(Date.from(Instant.now().plusSeconds(24 * 60 * 60)))
        .withClaim("username", userDetails.getUsername())
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
