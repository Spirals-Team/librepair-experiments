package backend.repository;

import backend.entity.AppUser;
import backend.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository  extends MongoRepository<Event,String> {
    Event findById(@Param("id") String id);
    void deleteById(@Param("id") String id);

    @Override
    List<Event> findAll();
}
