package com.example.stefan.controller;

import com.example.stefan.model.User;
import com.example.stefan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
@RestController
public class UserController
{
  private UserService userService;

  @Autowired
  public UserController(UserService userService)
  {
    this.userService = userService;
  }

  @PostMapping("/api/v1/user/register")
  public ResponseEntity<Void> createUser(@Valid @RequestBody User user)
  {
    userService.createUser(user);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/api/v1/user/deleteUser/{username}")
  public ResponseEntity<Void> deleteUser(@PathVariable String username)
  {
    userService.deleteUser(username);
    return ResponseEntity.ok().build();
  }
}
