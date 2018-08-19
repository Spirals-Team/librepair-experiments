package ua.epam.spring.hometask.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.repository.AuditoriumRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Vladimir on 24.10.2017.
 */
@Repository
public class JdbcAuditoriumRepositoryImpl implements AuditoriumRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired//we can avoid it because of Spring 4.3
    public JdbcAuditoriumRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Auditorium> getAll() {

        return jdbcTemplate.query(
                "SELECT a.name, a.number_of_seats, aseats.vip_seat" +
                        " FROM auditorium a" +
                        " LEFT JOIN auditorium_seats aseats" +
                        " ON a.name=aseats.auditorium_name", rs -> {

                    Map<Auditorium, Set<Long>> auditoriumSeats = new HashMap<>();
                    while (rs.next()) {
                        String name = rs.getString("name");
                        Long numberOfSeats = rs.getLong("number_of_seats");
                        Long vipSeat = rs.getLong("vip_seat");   //return 0 if column is null

                        Auditorium a = new Auditorium();
                        a.setName(name);
                        a.setNumberOfSeats(numberOfSeats);

                        if (!auditoriumSeats.containsKey(a)) {
                            auditoriumSeats.put(a, Collections.emptySet());
                        }

                        if (vipSeat != 0) {
                            Set<Long> vipSeats = auditoriumSeats.get(a).stream().collect(Collectors.toSet());

                            vipSeats.add(vipSeat);
                            auditoriumSeats.put(a, vipSeats);
//                            auditoriumSeats.get(a).add(vipSeat);//wrong approach, because emptySet is unmodifiable
                        }
                    }

                    auditoriumSeats.forEach(Auditorium::setVipSeats);

                    return auditoriumSeats.keySet();
                });
    }
}
