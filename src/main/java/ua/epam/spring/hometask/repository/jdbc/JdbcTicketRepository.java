package ua.epam.spring.hometask.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.domain.to.EventDetailsDTO;
import ua.epam.spring.hometask.repository.TicketRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Vladimir on 28.10.2017.
 */
@Repository
public class JdbcTicketRepository implements TicketRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired//we can avoid it because of Spring 4.3
    public JdbcTicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean bookTickets(Set<Ticket> tickets) {
        if (!validateTickets(tickets)) {
            return false;
        }

        String sql = "INSERT INTO ticket(user_id, event_id, date_time, seat) VALUES(?,?,?,?)";
        List<Object[]> batchArgs =
                tickets
                        .stream()
                        .map(ticket -> new Object[]{
                                ticket.getUser().getId(),
                                ticket.getEvent().getId(),
                                Timestamp.valueOf(ticket.getDateTime()),
                                ticket.getSeat()})
                        .collect(Collectors.toList());

        return jdbcTemplate.batchUpdate(sql, batchArgs).length > 0;
    }

    private boolean validateTickets(Set<Ticket> tickets) {
        Collection<Ticket> purchasedTickets = getAll();

        for (Ticket t : tickets) {
            LocalDateTime airDate = t.getDateTime();
            long seat = t.getSeat();
            try {
                Objects.requireNonNull(t.getEvent());
                Objects.requireNonNull(airDate);

            } catch (NullPointerException e) {
                return false;
            }

            //check duplicates
            if (purchasedTickets.contains(t)) {
                return false;
            }

            //check that event exists
            Event event;
            try {
                event = getEventById(t.getEvent().getId());
            } catch (IncorrectResultSizeDataAccessException e) {
                return false;
            }


            List<EventDetailsDTO> eventDetails = jdbcTemplate.query(
                    "SELECT * FROM event_auditoriums WHERE event_id=?",
                    new Object[]{event.getId()},
                    (rs, rowNum) -> {
                        EventDetailsDTO eventDetail = new EventDetailsDTO(rs.getLong("event_id"), rs.getString("auditorium_name"), rs.getTimestamp("air_date").toLocalDateTime());
                        return eventDetail;
                    });
            //

            //check that event airDates contains ticket's time
            if (!eventDetails.stream().anyMatch(eventDetailsDTO -> airDate.equals(eventDetailsDTO.getAirDate()))) {
                return false;
            }
            //check that auditorium exists and contain seat using airDate
            String expectedAuditoriumName =
                    eventDetails.stream()
                            .filter(eventDetailsDTO -> airDate.equals(eventDetailsDTO.getAirDate()))
                            .findFirst().get().getAuditoriumName();


            //check that auditorium exists
            Auditorium auditorium;
            try {
                auditorium = jdbcTemplate.queryForObject("SELECT * FROM auditorium WHERE name=?", (rs, rowNum) -> {
                    Auditorium a = new Auditorium();
                    a.setName(rs.getString("name"));
                    a.setNumberOfSeats(rs.getLong("number_of_seats"));
                    return a;
                }, expectedAuditoriumName);

            } catch (IncorrectResultSizeDataAccessException e) {
                return false;
            }
            //check that auditorium contains seat
            if (!auditorium.getAllSeats().contains(seat)) {
                return false;
            }

        }
        return true;
    }

    @Override
    public Collection<Ticket> getAll() {
        return jdbcTemplate.query("SELECT * FROM ticket", (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long user_id = rs.getLong("user_id");
            Long event_id = rs.getLong("event_id");
            LocalDateTime dateTime = rs.getTimestamp("date_time").toLocalDateTime();
            Long seat = rs.getLong("seat");

            User user = jdbcTemplate.queryForObject("SELECT * FROM users where id=?", new Object[]{user_id}, (rs1, rowNum1) -> {
                User u = new User();
                u.setId(rs1.getLong("id"));
                u.setFirstName(rs1.getString("first_name"));
                u.setLastName(rs1.getString("last_name"));
                u.setEmail(rs1.getString("email"));
                u.setBirthday(rs1.getTimestamp("birthday").toLocalDateTime().toLocalDate());
                return u;
            });

            Event event = getEventById(event_id);

            Ticket result = new Ticket(user, event, dateTime, seat);
            result.setId(id);
            return result;
        });
    }

    private Event getEventById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM event where id=?", new Object[]{id}, (rs, rowNum1) -> {
            Event e = new Event();
            e.setId(rs.getLong("id"));
            e.setName(rs.getString("name"));
            e.setBasePrice(rs.getDouble("base_price"));
            e.setRating(EventRating.valueOf(rs.getString("rating")));
            return e;
        });
    }
}
