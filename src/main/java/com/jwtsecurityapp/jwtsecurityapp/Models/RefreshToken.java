package com.jwtsecurityapp.jwtsecurityapp.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @OneToOne
  @JoinTable(
    name="user"
  )
  private User owner;
}
