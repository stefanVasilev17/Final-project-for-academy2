package com.example.stefan.service;

import com.example.stefan.model.User;
import com.example.stefan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService
{
  UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository)
  {
    this.userRepository = userRepository;
  }

  public void createUser(User user)
  {
    userRepository.createUser(user);
  }

  public User findByUsername(String username)
  {
    return userRepository.findByUsername(username);
  }

  public void deleteUser(String username)
  {
    userRepository.deleteUser(username);
  }
}
