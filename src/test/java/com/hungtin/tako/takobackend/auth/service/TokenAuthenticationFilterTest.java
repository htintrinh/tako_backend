package com.hungtin.tako.takobackend.auth.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.hungtin.tako.takobackend.auth.TokenAuthenticationFilter;
import com.hungtin.tako.takobackend.auth.model.UserAccount;
import com.hungtin.tako.takobackend.auth.model.UserToken;
import com.hungtin.tako.takobackend.auth.repo.UserTokenRepo;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenAuthenticationFilterTest {

  static class MockTokenAuthenticationFilter extends TokenAuthenticationFilter {

    public MockTokenAuthenticationFilter(
        UserTokenRepo userTokenRepo) {
      super(userTokenRepo);
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain)
        throws ServletException, IOException {
      super.doFilterInternal(request, response, chain);
    }
  }

  @SneakyThrows
  @Test
  public void testDoFilterInternal() {
    HttpServletRequest req = mock(HttpServletRequest.class);
    HttpServletResponse res = mock(HttpServletResponse.class);
    FilterChain chain = mock(FilterChain.class);
    UserTokenRepo repo = mock(UserTokenRepo.class);
    UserToken userToken = mock(UserToken.class);
    UserAccount userAccount = UserAccount.builder().username("TEST").password("TEST").build();

    when(req.getHeader("Authorization")).thenReturn("Bearer abcxyz");
    when(repo.findOneByValue("abcxyz")).thenReturn(Optional.of(userToken));
    when(userToken.getUserAccount()).thenReturn(userAccount);

    MockTokenAuthenticationFilter filterMock = new MockTokenAuthenticationFilter(repo);
    filterMock.doFilterInternal(req, res, chain);
    assertNotNull(SecurityContextHolder.getContext().getAuthentication());
  }

}
