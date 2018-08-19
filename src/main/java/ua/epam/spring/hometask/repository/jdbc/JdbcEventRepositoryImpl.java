package ua.epam.spring.hometask.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventRating;
import ua.epam.spring.hometask.domain.to.EventDetailsDTO;
import ua.epam.spring.hometask.repository.EventRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Vladimir on 24.10.2017.
 */
@Repository
public class JdbcEventRepositoryImpl implements EventRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired//we can avoid it because of Spring 4.3
    public JdbcEventRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Event save(Event event) {

        Object[] params = {event.getName(), event.getBasePrice(), event.getRating().name()};

        int result;
        if (event.isNew()) {
            String sql = "INSERT INTO event (name, base_price, rating) VALUES (?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            result = jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, event.getName());
                ps.setDouble(2, event.getBasePrice());
                ps.setString(3, event.getRating().name());
                return ps;
            }, keyHolder);
            event.setId(keyHolder.getKey().longValue());
        } else {
            String sql = "UPDATE event SET name=?, base_price=?, rating=? where id=" + event.getId();
            result = jdbcTemplate.update(sql, params);
        }

        if (event.getAirDates() != null &&
                event.getAuditoriums() != null &&
                event.getAirDates().size() == event.getAuditoriums().size() &&
                event.getAirDates().size() > 0) {
            jdbcTemplate.update("DELETE FROM event_auditoriums WHERE event_id=?", event.getId());
            event.getAirDates().forEach(airDate -> {
                jdbcTemplate.update("INSERT INTO event_auditoriums(event_id, auditorium_name, air_date) VALUES(?,?,?)",
                        event.getId(),
                        event.getAuditoriums().get(airDate).getName(),
                        Timestamp.valueOf(airDate));
            });
        }

        return result == 1 ? event : null;
    }

    @Override
    public boolean delete(Event event) {
        Long id = event.getId();
        Objects.requireNonNull(id);
        jdbcTemplate.update("DELETE FROM event_auditoriums WHERE event_id=?", event.getId());
        return jdbcTemplate.update("DELETE FROM event WHERE id=?", id) != 0;
    }

    @Override
    public Event get(long id) {

        return DataAccessUtils.singleResult(jdbcTemplate.query(
                "SELECT e.id, e.name, e.base_price, e.rating, eu.auditorium_name, eu.air_date" +
                        " FROM event e" +
                        " LEFT JOIN event_auditoriums eu ON e.id=eu.event_id" +
                        " WHERE id=?", new Object[]{id}, rse())
        );
    }

    @Override
    public Collection<Event> getAll() {

        return jdbcTemplate.query(
                "SELECT e.id, e.name, e.base_price, e.rating, eu.auditorium_name, eu.air_date" +
                        " FROM event e" +
                        " LEFT JOIN event_auditoriums eu ON e.id=eu.event_id", rse());
    }

    private ResultSetExtractor<Set<Event>> rse() {
        return rs -> {

            Map<Event, Set<EventDetailsDTO>> eventDetailsMap = new HashMap<>();
            while (rs.next()) {
                Long eventId = rs.getLong("id");
                String eventName = rs.getString("name");
                double basePrice = rs.getDouble("base_price");
                EventRating eventRating = EventRating.valueOf(rs.getString("rating"));

                String auditoriumName = rs.getString("auditorium_name");
                LocalDateTime airDate = null;
                if (rs.getTimestamp("air_date") != null) {
                    airDate = rs.getTimestamp("air_date").toLocalDateTime();
                }

                Event event = new Event();
                event.setId(eventId);
                event.setName(eventName);
                event.setBasePrice(basePrice);
                event.setRating(eventRating);

                if (!eventDetailsMap.containsKey(event)) {
                    eventDetailsMap.put(event, new HashSet<>());
                }
                eventDetailsMap.get(event).add(new EventDetailsDTO(eventId, auditoriumName, airDate));
            }

            eventDetailsMap.forEach((event, eventDetailsDTOs) -> {

                Set<LocalDateTime> airDates = eventDetailsDTOs
                        .stream()
                        .filter(eventDetailsDTO -> eventDetailsDTO.getAirDate() != null)
                        .map(EventDetailsDTO::getAirDate)
                        .collect(Collectors.toSet());
                event.setAirDates(new TreeSet<>(airDates));

                NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();

                eventDetailsDTOs.forEach(eventDetailsDTO -> {
                    if (eventDetailsDTO.getAuditoriumName() != null) {
                        Auditorium a = new Auditorium();
                        a.setName(eventDetailsDTO.getAuditoriumName());
                        auditoriums.put(eventDetailsDTO.getAirDate(), a);
                    }
                });
                event.setAuditoriums(auditoriums);
            });

            return eventDetailsMap.keySet();
        };
    }

}
