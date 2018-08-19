package ua.epam.spring.hometask.repository.memory;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.repository.EventRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static ua.epam.spring.hometask.domain.DomainObject.START_SEQ;

/**
 * Created by Vladimir on 10.10.2017.
 */
@Repository
public class InMemoryEventRepositoryImpl implements EventRepository {
    private Map<Long, Event> repository = new ConcurrentHashMap<>();

    private AtomicLong counter = new AtomicLong(START_SEQ);

    private static final Comparator<Event> EVENT_COMPARATOR = Comparator.comparing(Event::getName);


    @Override
    public Event save(Event event) {
        Objects.requireNonNull(event);
        if (event.isNew()) {
            event.setId(counter.getAndIncrement());
        }
        repository.put(event.getId(), event);
        return event;

    }

    @Override
    public boolean delete(Event event) {
        Event deletedEvent = repository.remove(event.getId());
        return deletedEvent != null;
    }

    @Override
    public Event get(long id) {
        return repository.get(id);
    }

    @Override
    public Collection<Event> getAll() {
        return repository.values().stream().sorted(EVENT_COMPARATOR).collect(Collectors.toList());
    }
}
