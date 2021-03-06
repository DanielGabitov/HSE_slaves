package com.hse.DAOs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventToParticipantDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public EventToParticipantDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public void addParticipant(long eventId, long participantId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("eventId", eventId);
        map.addValue("participantId", participantId);

        namedJdbcTemplate.update(
                "INSERT INTO events_participants (eventId, participantId) VALUES (:eventId, :userId)",
                map);
    }

    public void deleteParticipants(long eventId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("eventId", eventId);

        namedJdbcTemplate.update("DELETE FROM events_participants WHERE eventid = :eventId", map);
    }

    public List<Long> getParticipants(long eventId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("eventId", eventId);

        return namedJdbcTemplate.query("SELECT * from events_participants WHERE eventId = :eventId", map,
                (resultSet, i) -> resultSet.getLong("participantId"));
    }

    public List<Long> getUserParticipations(long userId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("participantId", userId);

        return namedJdbcTemplate.query("SELECT * from events_participants WHERE participantid = :participantId",
                map,
                (resultSet, i) -> resultSet.getLong("eventId"));
    }

    public Integer getNumberOfParticipationsInEventByParticipantId(long eventId, long participantId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("eventId", eventId);
        map.addValue("participantId", participantId);

        return namedJdbcTemplate.queryForObject(
                "SELECT count(eventid) FROM events_participants" +
                        " WHERE eventid = :eventId AND participantid = :participantId",
                map,
                Integer.class
        );
    }
}
