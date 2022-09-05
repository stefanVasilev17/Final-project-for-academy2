package com.example.stefan.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component
public class ImxUserDetailsService implements UserDetailsService
{
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
  {
    return new User("admin", "pass", new ArrayList<>());
  }
}
