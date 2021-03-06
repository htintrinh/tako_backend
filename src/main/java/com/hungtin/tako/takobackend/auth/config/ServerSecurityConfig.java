package com.hungtin.tako.takobackend.auth.config;

import com.hungtin.tako.takobackend.auth.JwtTokenAuthenticationFilter;
import com.hungtin.tako.takobackend.auth.TokenAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final TokenAuthenticationFilter tokenAuthenticationFilter;
  private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/auth/login", "/auth/register",
            "/auth/confirm-code/**", "/auth/refresh-token")
        .permitAll()
        .anyRequest()
        .authenticated();
    http.cors();
    //    http.addFilterBefore(tokenAuthenticationFilter,
    // UsernamePasswordAuthenticationFilter.class)
    http.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .headers()
        .frameOptions()
        .sameOrigin();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  /**
   * Configure the authentication manager to use the user detail service that get injected to this
   * class above
   *
   * @param auth the builder for authentication manger
   * @throws Exception just to confirm with the description
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService);
  }

  // This is necessary to create the
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
