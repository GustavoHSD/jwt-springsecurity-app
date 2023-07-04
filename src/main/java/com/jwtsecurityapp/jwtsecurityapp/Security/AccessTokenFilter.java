package com.jwtsecurityapp.jwtsecurityapp.Security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwtsecurityapp.jwtsecurityapp.Models.User;
import com.jwtsecurityapp.jwtsecurityapp.Service.UserService;
import com.jwtsecurityapp.jwtsecurityapp.Utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AccessTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      Optional<String> accessToken = parseAccessToken(request);
      if(accessToken.isPresent() && jwtUtils.validateAccessToken(accessToken.get())) {
        Long userId = Long.valueOf(jwtUtils.getUserIdFromAccessToken(accessToken.get()));
        User user = userService.findById(userId);
        UsernamePasswordAuthenticationToken userPasswordToken = new UsernamePasswordAuthenticationToken(userId, null, user.getAuthorities());
        userPasswordToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(userPasswordToken);
      }
    } catch (Exception e) {
      log.error("Cannot set authentication", e);
    }
  }

  private Optional<String> parseAccessToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer")) {
      return Optional.of(authHeader.replace("Bearer ", ""));
    }
    return Optional.empty();
  }

}
