package com.jwtsecurityapp.jwtsecurityapp.Api.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtsecurityapp.jwtsecurityapp.DTO.LoginDTO;
import com.jwtsecurityapp.jwtsecurityapp.DTO.TokenDTO;
import com.jwtsecurityapp.jwtsecurityapp.Models.RefreshToken;
import com.jwtsecurityapp.jwtsecurityapp.Models.User;
import com.jwtsecurityapp.jwtsecurityapp.Repository.RefreshTokenRepository;
import com.jwtsecurityapp.jwtsecurityapp.Repository.UserRepository;
import com.jwtsecurityapp.jwtsecurityapp.Utils.JwtUtils;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthREST {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  RefreshTokenRepository refreshTokenRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;
  
  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("login")
  @Transactional
  public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    User user = (User) authentication.getPrincipal();

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setOwner(user);
    refreshTokenRepository.save(refreshToken);

    String accessToken = jwtUtils.generateAccessToken(user);
    String refreshTokenString = jwtUtils.generateRefreshToken(user, String.valueOf(refreshToken.getId()));

    return ResponseEntity.ok(new TokenDTO(String.valueOf(user.getId()), accessToken, refreshTokenString));
  }

  @PostMapping("/singup")
  @Transactional
  public ResponseEntity<?> signup(@Valid @RequestBody SignupDTO dto) {
    User user = new User(dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
    return null;
  }
}
