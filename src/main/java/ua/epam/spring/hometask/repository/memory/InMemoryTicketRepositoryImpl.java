package ua.epam.spring.hometask.repository.memory;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.repository.TicketRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ua.epam.spring.hometask.domain.DomainObject.START_SEQ;

@Repository
public class InMemoryTicketRepositoryImpl implements TicketRepository {

    private Map<Long, Ticket> repository = new ConcurrentHashMap<>();

    private AtomicLong counter = new AtomicLong(START_SEQ);


    @Override
    public boolean bookTickets(Set<Ticket> tickets) {
        if (validateTickets(tickets)) {
            repository.putAll(tickets.stream().collect(Collectors.toMap(o -> counter.getAndIncrement(), Function.identity())));
            return true;
        }
        return false;
    }

    @Override
    public Collection<Ticket> getAll() {
        return repository.values();
    }


    private boolean validateTickets(Set<Ticket> tickets) {
        for (Ticket t : tickets) {
            //null check for event and time
            try {
                Objects.requireNonNull(t.getEvent());
                Objects.requireNonNull(t.getDateTime());

            } catch (NullPointerException e) {
                return false;
            }

            Event event = t.getEvent();
            LocalDateTime ldt = t.getDateTime();
            long seat = t.getSeat();

            //check that event auditoriums contains auditorium on airDate
            if (event.getAuditoriums().get(ldt) == null) {
                return false;
            }
            //check that event airDates contains ticket's time
            if (!event.getAirDates().contains(ldt)) {
                return false;
            }
            //check that seat exists in auditorium
            if (!event.getAuditoriums().get(ldt).getAllSeats().contains(seat)) {
                return false;
            }
            if (repository.values().contains(t)) {
                return false;
            }

        }
        return true;
    }
}
