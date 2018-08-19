package ua.epam.spring.hometask.repository.memory;

import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.repository.AuditoriumRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static ua.epam.spring.hometask.domain.DomainObject.START_SEQ;

/**
 * Created by Vladimir on 13.10.2017.
 */
@Repository
public class InMemoryAuditoriumRepositoryImpl implements AuditoriumRepository {
    private Map<Long, Auditorium> repository = new ConcurrentHashMap<>();

    private AtomicLong counter = new AtomicLong(START_SEQ);

    public InMemoryAuditoriumRepositoryImpl(List<String> auditoriumNames, List<String> numberOfSeats, List<String> vipSeats) {

        for (int i = 0; i < auditoriumNames.size(); i++) {
            Auditorium auditorium = new Auditorium();
            auditorium.setName(auditoriumNames.get(i));
            auditorium.setNumberOfSeats(Long.parseLong(numberOfSeats.get(i)));
            auditorium.setVipSeats(Arrays.stream(vipSeats.get(i).split("\\s*,\\s*")).map(Long::parseLong).collect(Collectors.toSet()));

            repository.put(counter.getAndIncrement(), auditorium);

        }
    }

    @Override
    public Collection<Auditorium> getAll() {
        return repository.values();
    }
}
