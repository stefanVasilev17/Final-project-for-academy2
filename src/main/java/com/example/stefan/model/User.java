package com.example.stefan.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class User
{
  @NotNull
  @Size(min = 2, max = 30)
  private String      username;
  @NotNull
  @Size(min = 2, max = 30)
  private String      password;
  private String role;

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getRole()
  {
    return role;
  }

  public void setRole(String role)
  {
    this.role = role;
  }
}
