package com.example.stefan.repository;

import com.example.stefan.model.Contract;
import com.example.stefan.model.ContractInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class ContractRepository
{
  private final NamedParameterJdbcOperations namedParameterJdbcTemplate;

  @Autowired
  public ContractRepository(NamedParameterJdbcOperations namedParameterJdbcTemplate)
  {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
  }

  //inserts values into contracts sql table
  public void createContract(Contract contract)
  {
    String sql =
        "INSERT INTO CONTRACTS                      "
            + "(contract_reference,contract_info)   "
            + " VALUES                              "
            + "(:contract_reference, :contract_info)";

    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("contract_reference", contract.getContract_reference());
    mapSqlParameterSource.addValue("contract_info", contract.getContract_info());

    namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
  }

  public Optional<Long> findCountOfContractsByName(String name)
  {
    try {
      MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
      mapSqlParameterSource.addValue("name", name);
      String sql =
          "SELECT COUNT(*) FROM links                            "
              + "WHERE participantsReference                           "
              + " = (SELECT participantsReference                      "
              + "FROM participants                                     "
              + " WHERE names = :name)";

      return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, (ResultSet rs, int rowNum) ->
          rs.getLong("COUNT(*)")));
    }
    catch (EmptyResultDataAccessException ERDAException) {
      ERDAException.printStackTrace();
      return Optional.empty();
    }
  }

  //shows information about contracts like contractor's name and his type(CLIENT OR DEBITOR). - > Exam 3
  public List<ContractInfo> getContractInfo(String contract_reference, String orderBy, int start, int fetchNext)
  {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("contract_reference", contract_reference);
    String sql = String.format(
        "SELECT PARTICIPANTS.names,PARTICIPANTS.last_name,LINKS.type                         "
            + "FROM PARTICIPANTS                                                              "
            + "JOIN LINKS ON PARTICIPANTS.participantsReference = links.participantsReference "
            + "AND LINKS.contract_reference = :contract_reference                             "
            + " ORDER BY %s                                                                   "
            + " OFFSET %d ROWS                                                                "
            + " FETCH NEXT %d ROWS ONLY                                                       "
        , orderBy, start, fetchNext);
    return namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, (ResultSet rs, int rowNum) ->
    {
      ContractInfo contractInfo = new ContractInfo();
      contractInfo.setContractType(rs.getString("type"));
      contractInfo.setContractorName(rs.getString("names"));
      contractInfo.setContractorLastName(rs.getString("last_name"));
      return contractInfo;
    });
  }

  // for TESTS
  public void deleteContract(String contract_reference)
  {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("contract_reference", contract_reference);

    String sql =
        "DELETE FROM CONTRACTS                          "
            + " WHERE contract_reference = :contract_reference";
    namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
  }
}

