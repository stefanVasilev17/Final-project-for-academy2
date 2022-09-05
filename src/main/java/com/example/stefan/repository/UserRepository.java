package com.example.stefan.repository;

import com.example.stefan.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

@Repository
public class UserRepository
{

  private final NamedParameterJdbcOperations namedParameterJdbcTemplate;

  @Autowired
  public UserRepository(NamedParameterJdbcOperations namedParameterJdbcTemplate)
  {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  public void createUser(User user)
  {
    String sql =
              "INSERT INTO USERS(username,password,role) "
            + "VALUES(:username,:password,:role)         ";

    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("username", user.getUsername());
    mapSqlParameterSource.addValue("password", user.getPassword());
    mapSqlParameterSource.addValue("role", user.getRole());

    namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
  }

  public User findByUsername(String username)
  {
    String sql =
              "SELECT * FROM USERS          "
            + "WHERE USERNAME = :username   ";

    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("username", username);

    return (namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource,
        (ResultSet rs, int rowNum) -> {
          User user = new User();
          user.setUsername(rs.getString("username"));
          user.setPassword(rs.getString("password"));

          return user;
        }));
  }

  public void deleteUser(String username)
  {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("username", username);

    String sql =
              "DELETE FROM USERS          "
            + " WHERE username = :username";
    namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
  }
}
