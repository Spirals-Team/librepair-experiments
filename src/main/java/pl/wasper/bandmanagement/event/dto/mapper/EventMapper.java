package pl.wasper.bandmanagement.event.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.wasper.bandmanagement.event.dto.EventDto;
import pl.wasper.bandmanagement.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    private ModelMapper mapper;

    @Autowired
    public EventMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public EventDto map(Event event) {
        return mapper.map(event, EventDto.class);
    }

    public Event map(EventDto eventDto) {
        return mapper.map(eventDto, Event.class);
    }

    public List<EventDto> mapAll(List<Event> events) {
        return events.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<Event> mapAllDto(List<EventDto> eventDtos) {
        return eventDtos.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }
}
