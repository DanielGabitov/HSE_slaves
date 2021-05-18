package com.hse.DAOs;

import com.hse.models.Event;
import com.hse.utils.ArraySQLValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventDAO {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Event> eventMapper;

    @Autowired
    public EventDAO(JdbcTemplate jdbcTemplate, RowMapper<Event> eventMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.eventMapper = eventMapper;
    }


    public List<Event> getEvent(long id) {
        return jdbcTemplate.query("SELECT * FROM events WHERE id= ?", new Object[]{id}, eventMapper);
    }

    public int saveEvent(Event event) {
        return jdbcTemplate.update(
                "INSERT INTO events" +
                        "(name," +
                        " description," +
                        " images," +
                        " organizerIDs," +
                        " participantsIDs," +
                        " rating," +
                        " geoData," +
                        " specialization," +
                        " date" +
                        ")" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                event.getName(),
                event.getDescription(),
                ArraySQLValue.create(event.getImages().toArray(), "varchar"),
                ArraySQLValue.create(event.getOrganizerIDs().toArray(), "bigint"),
                ArraySQLValue.create(event.getParticipantsIDs().toArray(), "bigint"),
                event.getRating(),
                event.getGeoData(),
                event.getSpecialization().name(),
                event.getDate());
    }

    public void updateImageHashes(long id, List<String> imageHashes){
        jdbcTemplate.update("UPDATE events set images = images || ? where id = ?",
                ArraySQLValue.create(imageHashes.toArray(), "varchar"), id);
    }
}
