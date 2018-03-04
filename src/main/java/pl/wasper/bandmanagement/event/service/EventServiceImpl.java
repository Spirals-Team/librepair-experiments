package pl.wasper.bandmanagement.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wasper.bandmanagement.event.dto.EventDto;
import pl.wasper.bandmanagement.event.dto.mapper.EventMapper;
import pl.wasper.bandmanagement.event.model.Event;
import pl.wasper.bandmanagement.event.repository.EventRepository;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private EventRepository repository;
    private EventMapper mapper;

    @Autowired
    public EventServiceImpl(EventRepository repository, EventMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<EventDto> getEvents() {
        return mapper.mapAll(repository.findAll());
    }

    @Override
    public EventDto findOneById(Long id) throws ElementNotFoundException {
        Event event = repository.findOne(id);

        if (event == null) {
            throw new ElementNotFoundException(String.format("Unable to find event with id %d", id));
        }

        return mapper.map(event);
    }

    @Override
    public EventDto save(EventDto event) {
        Event savedEvent = repository.save(mapper.map(event));

        return mapper.map(savedEvent);
    }

    @Override
    public EventDto update(EventDto event) throws ElementNotFoundException {
        if (repository.findOne(event.getId()) == null) {
            throw new ElementNotFoundException(String.format("Unable to find event with id %d", event.getId()));
        }

        Event updatedEvent = repository.save(mapper.map(event));

        return mapper.map(updatedEvent);
    }

    @Override
    public void delete(Long id) throws ElementNotFoundException {
        if (repository.findOne(id) == null) {
            throw new ElementNotFoundException(String.format("Unable to find event with id %d", id));
        }

        repository.delete(id);
    }
}
