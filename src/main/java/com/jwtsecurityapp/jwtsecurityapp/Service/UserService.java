package com.jwtsecurityapp.jwtsecurityapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jwtsecurityapp.jwtsecurityapp.Models.User;
import com.jwtsecurityapp.jwtsecurityapp.Repository.UserRepository;

public class UserService  implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  public User loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
      .orElseThrow(() ->  new UsernameNotFoundException("Username not found"));
  }

  public User findById(Long id) {
    return userRepository.findById(id)
      .orElseThrow(() -> new UsernameNotFoundException("User id not found"));
  }
}
