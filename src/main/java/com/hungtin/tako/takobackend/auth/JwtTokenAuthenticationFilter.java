package com.hungtin.tako.takobackend.auth;

import com.hungtin.tako.takobackend.auth.service.JwtTokenService;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

  public static final String AUTHORIZATION = "Authorization";
  public static final String BEARER = "Bearer";

  private final UserDetailsService userDetailsService;
  private final JwtTokenService jwtTokenService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    extractJwtToken(request)
        .map(jwtTokenService::validateToken)
        .map(userDetailsService::loadUserByUsername)
        .map(u -> new UsernamePasswordAuthenticationToken(u, u.getPassword(), u.getAuthorities()))
        .ifPresent(loggedTok -> SecurityContextHolder.getContext().setAuthentication(loggedTok));
    filterChain.doFilter(request, response);
  }

  private Optional<String> extractJwtToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader(AUTHORIZATION);
    if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
      return Optional.of(authorizationHeader.substring(7));
    }
    return Optional.empty();
  }
}
