package ua.epam.spring.hometask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.repository.EventRepository;
import ua.epam.spring.hometask.util.exception.NotFoundException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Vladimir on 10.10.2017.
 */
@Service
public class EventServiceImpl implements EventService {

    public EventServiceImpl() {
        this(Clock.systemDefaultZone());
    }

    public EventServiceImpl(Clock clock) {
        this.clock = clock;
    }

    private Clock clock;

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    private EventRepository eventRepository;

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        Event result = eventRepository.getAll()
                .stream()
                .filter(event -> name.equals(event.getName()))
                .findFirst()
                .orElse(null);

        if (result == null) {
            throw new NotFoundException("Event with name=" + name + " wasn't found.");
        }
        return result;
    }

    @Override
    public Event save(@Nonnull Event event) {
        Event result = eventRepository.save(event);
        if (result == null) {
            throw new NotFoundException("Event " + event + " wasn't created/updated.");
        }
        return result;
    }

    @Override
    public void remove(@Nonnull Event event) {
        boolean found = eventRepository.delete(event);
        if (!found) {
            throw new NotFoundException("Event " + event + " wasn't found for deleting.");
        }
    }

    @Override
    public Event getById(@Nonnull Long id) {

        Event result = eventRepository.get(id);
        if (result == null) {
            throw new NotFoundException("Event with id=" + id + " wasn't found.");
        }
        return result;
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return eventRepository.getAll();
    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {

        return eventRepository.getAll()
                .stream()
                .filter(event -> event.airsOnDates(from, to))
                .collect(Collectors.toSet());
    }

    @Nonnull
    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        LocalDateTime now = LocalDateTime.now(clock);

        return eventRepository.getAll()
                .stream()
                .filter(event -> event.getAirDates().stream().anyMatch(ldt -> ldt.compareTo(now) >= 0 && ldt.compareTo(to) <= 0))
                .collect(Collectors.toSet());
    }
}
