package com.jwtsecurityapp.jwtsecurityapp.Api.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
  
  @NotNull
  @Size(min = 3, max = 30)
  private String username;
  
  @NotNull
  @Size(max = 60)
  @Email
  private String email;

  @NotNull
  @Size(min = 8, max = 60)
  private String password;

}
