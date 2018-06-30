package pl.sda.jira.calendar.persistency.jpa;

import org.springframework.stereotype.Repository;
import pl.sda.jira.calendar.domain.CalendarRepository;
import pl.sda.jira.calendar.domain.CrudJpaCalendarRepository;
import pl.sda.jira.calendar.domain.model.Calendar;

@Repository
public class JpaDataCalendarRepository implements CalendarRepository {
    private final CrudJpaCalendarRepository repository;

    public JpaDataCalendarRepository(CrudJpaCalendarRepository repository) {
        this.repository = repository;
    }

    @Override
    public void add(Calendar calendar) {
        repository.save(calendar);
    }

    @Override
    public boolean exists(String id) {
        return repository.exists(id);
    }

    @Override
    public Calendar findBy(String id) {
        return repository.findOne(id);
    }

    @Override
    public void remove(String id) {
        repository.delete(id);
    }

    @Override
    public void replace(Calendar calendar) {
        repository.save(calendar);

    }
}
