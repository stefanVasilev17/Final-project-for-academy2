package com.example.stefan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider
{
  private final NamedParameterJdbcOperations namedTemplate;

  @Autowired
  public CustomAuthenticationProvider(@Qualifier("namedTemplate") NamedParameterJdbcOperations namedTemplate)
  {
    this.namedTemplate = namedTemplate;
  }

  @Override
  public Authentication authenticate(Authentication auth)
      throws AuthenticationException
  {


    String username = auth.getName();
    String password = auth.getCredentials()
        .toString();

    final List<SimpleGrantedAuthority> list;

    try {
      namedTemplate.queryForObject("SELECT 1 FROM users2 WHERE login = :username", new MapSqlParameterSource("username", username), Integer.class);

      list =
          namedTemplate.query("SELECT key FROM privilegeCodix WHERE id IN (SELECT id_privilege FROM usres_privilege WHERE id_user IN (SELECT id FROM users2 WHERE login = :username))",
              new MapSqlParameterSource("username", username), (rs, rowNum) -> new SimpleGrantedAuthority (rs.getString(1)));

    }
    catch (IncorrectResultSizeDataAccessException e) {
      throw new BadCredentialsException("External system authentication failed");
    }

    if ("admin".equals(username) && "pass".equals(password) || "guest".equals(username)) {
      return new UsernamePasswordAuthenticationToken
          (username, password, list);
    }
    else {
      throw new
          BadCredentialsException("External system authentication failed");
    }
  }

  @Override
  public boolean supports(Class<?> auth)
  {
    return auth.equals(UsernamePasswordAuthenticationToken.class);
  }
}
