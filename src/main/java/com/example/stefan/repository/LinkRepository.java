package com.example.stefan.repository;

import com.example.stefan.model.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LinkRepository
{
  private final NamedParameterJdbcOperations namedParameterJdbcTemplate;

  @Autowired
  public LinkRepository(NamedParameterJdbcOperations namedParameterJdbcTemplate)
  {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  public void createLink(Link link)
  {
    String sql =
        "INSERT INTO LINKS(contract_reference,participantsReference,type)"
            + " VALUES(:contract_reference,:participantsReference,:type)";

    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("contract_reference", link.getContract_reference());
    mapSqlParameterSource.addValue("participantsReference", link.getParticipants_reference());
    mapSqlParameterSource.addValue("type", link.getType().toUpperCase());

    namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
  }

  public void deleteLink(String contract_reference)
  {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("contract_reference", contract_reference);

    String sql =
              "DELETE FROM LINK                               "
            + " WHERE contract_reference = :contract_reference";
    namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
  }
}
