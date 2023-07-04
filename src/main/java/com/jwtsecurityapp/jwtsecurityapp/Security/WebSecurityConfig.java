package com.jwtsecurityapp.jwtsecurityapp.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.jwtsecurityapp.jwtsecurityapp.Service.UserService;

@Configuration
public class WebSecurityConfig {
  // @Autowired
  // private UserService userService;

  @Autowired
  private AccessTokenEntryPoint accessTokenEntryPoint;

  @Bean
  public AccessTokenFilter accessTokenFilter() {
    return new AccessTokenFilter();
  }
  
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }  

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .cors(cors -> cors.disable())
      .csrf(csrf -> csrf.disable())
      .exceptionHandling((exceptionHandling) -> exceptionHandling
                          .authenticationEntryPoint(accessTokenEntryPoint))
      .sessionManagement((session) -> session
                          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                          )
      .authorizeHttpRequests((authz) -> authz
                          .requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated());
    httpSecurity.addFilterBefore(accessTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }
  
}
