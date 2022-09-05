package com.example.stefan.repository;

import com.example.stefan.model.Participants;
import com.example.stefan.model.ParticipantsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class ParticipantsRepository
{
  private final NamedParameterJdbcOperations namedParameterJdbcTemplate;


  @Autowired
  public ParticipantsRepository(NamedParameterJdbcOperations namedParameterJdbcTemplate)
  {
    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

  }

  public void createParticipant(Participants participants)
  {
    String sql =

        "INSERT INTO PARTICIPANTS                                       "
            + " (names,last_name,country,city,participantsReference)    "
            + "VALUES                                                   "
            + "(:names,:last_name,:country,:city,:participantsReference)";

    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("names", participants.getName());
    mapSqlParameterSource.addValue("last_name", participants.getLast_name());
    mapSqlParameterSource.addValue("country", participants.getCountry());
    mapSqlParameterSource.addValue("city", participants.getCity());
    mapSqlParameterSource.addValue("participantsReference", participants.getParticipantsReference());
    namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
  }

  public List<Participants> findParticipantsByType(String type)
  {

    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("type", type);
    String sql =
        "SELECT *                                                                                 "
            + "FROM PARTICIPANTS                                                                  "
            + "RIGHT JOIN links ON PARTICIPANTS.participantsReference = links.participantsReference "
            + "WHERE type = :type                                                                 ";

    return namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, (ResultSet rs, int rowNum) ->
    {
      Participants participants = new Participants();
      participants.setName(rs.getString("names"));
      participants.setLast_name(rs.getString("last_name"));
      participants.setCountry(rs.getString("country"));
      participants.setCity(rs.getString("city"));
      participants.setParticipantsReference(rs.getString("participantsReference"));
      return participants;
    });
  }

  // finds information about participants. - > EXAM 1.
  public List<ParticipantsInfo> findTypeOfParticipants(String orderBy, int start, int fetchNext)
  {
    String sql = String.format(
        "SELECT PARTICIPANTS.names, PARTICIPANTS.last_name,LINKS.type                        "
            + "FROM PARTICIPANTS                                                              "
            + "JOIN LINKS ON PARTICIPANTS.participantsReference = links.participantsReference "
            + " ORDER BY %s                                                                   "
            + " OFFSET %d ROWS                                                                "
            + " FETCH NEXT %d ROWS ONLY                                                       "
        , orderBy, start, fetchNext);

    return namedParameterJdbcTemplate.query(sql, (ResultSet rs, int rowNum) ->
    {
      ParticipantsInfo participant = new ParticipantsInfo();
      participant.setName(rs.getString("names"));
      participant.setLast_name(rs.getString("last_name"));
      participant.setType(rs.getString("type"));

      return participant;
    });
  }


  // for TESTS
  public void deleteParticipants(String participantsReference)
  {
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
    mapSqlParameterSource.addValue("participantsReference", participantsReference);

    String sql =
        "DELETE FROM PARTICIPANTS                             "
            + " WHERE participantsReference = :participantsReference";
    namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
  }
}
