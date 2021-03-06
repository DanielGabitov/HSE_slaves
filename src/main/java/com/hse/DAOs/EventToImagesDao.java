package com.hse.DAOs;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventToImagesDao {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public EventToImagesDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public void addImage(long eventId, String image) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("eventId", eventId);
        map.addValue("image", image);

        namedJdbcTemplate.update("INSERT INTO events_images (eventId, image) VALUES (:eventId, :image)", map);
    }

    public List<String> getImages(long eventId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("eventId", eventId);
        return namedJdbcTemplate.query("SELECT * from events_images WHERE eventId = :eventId", map,
                (resultSet, i) -> resultSet.getString("image"));
    }

    public void deleteEventImages(long eventId) {
        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("eventId", eventId);
        namedJdbcTemplate.update("DELETE FROM events_images WHERE eventId = :eventId", map);
    }
}
