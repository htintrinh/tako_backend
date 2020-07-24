package com.hungtin.tako.takobackend.auth;

import com.hungtin.tako.takobackend.auth.model.UserToken;
import com.hungtin.tako.takobackend.auth.repo.UserTokenRepo;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private final UserTokenRepo userTokenRepo;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    Optional<String> optAuthHeader = Optional.ofNullable(request.getHeader("Authorization"));
    optAuthHeader
        .filter(t -> t.startsWith("Bearer "))
        .map(t -> t.substring(7))
        .flatMap(userTokenRepo::findOneByValue)
        .map(UserToken::getUserAccount)
        .map(u -> new UsernamePasswordAuthenticationToken(u.getUsername(), u.getPassword(),
            u.getAuthorities()))
        .ifPresent((authTok) -> SecurityContextHolder.getContext().setAuthentication(authTok));

    filterChain.doFilter(request, response);
  }

}
