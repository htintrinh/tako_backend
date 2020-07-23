package com.hungtin.tako.takobackend.auth;

import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.model.UserToken;
import com.hungtin.tako.takobackend.auth.repo.UserTokenRepo;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private final UserTokenRepo userTokenRepo;

  @Autowired
  public TokenAuthenticationFilter(UserTokenRepo userTokenRepo) {
    this.userTokenRepo = userTokenRepo;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    Optional<String> optAuthHeader = Optional.ofNullable(request.getHeader("Authorization"));
    UserToken token = optAuthHeader
        .filter(t -> t.startsWith("Bearer "))
        .map(t -> t.substring(7))
        .flatMap(userTokenRepo::findOneByValue)
        .orElseThrow(() -> {
          throw new AuthenticationCredentialsNotFoundException("User token is not valid");
        });

    UserAccount userAccount = token.getUserAccount();

    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(
            userAccount.getUsername(), userAccount.getPassword(), userAccount.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }

}
