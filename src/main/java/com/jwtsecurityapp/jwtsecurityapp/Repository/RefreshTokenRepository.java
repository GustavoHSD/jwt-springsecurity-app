package com.jwtsecurityapp.jwtsecurityapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwtsecurityapp.jwtsecurityapp.Models.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
  
}
