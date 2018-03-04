package pl.wasper.bandmanagement.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wasper.bandmanagement.event.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
