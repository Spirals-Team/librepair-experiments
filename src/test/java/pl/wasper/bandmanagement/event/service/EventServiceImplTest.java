package pl.wasper.bandmanagement.event.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.wasper.bandmanagement.event.dto.EventDto;
import pl.wasper.bandmanagement.event.dto.mapper.EventMapper;
import pl.wasper.bandmanagement.event.model.Event;
import pl.wasper.bandmanagement.event.repository.EventRepository;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;
import pl.wasper.bandmanagement.helper.FakeFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EventServiceImplTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private EventMapper mapper;

    @Mock
    private EventRepository repository;

    private EventService service;

    @Before
    public void before() {
        service = new EventServiceImpl(repository, mapper);
    }

    @Test
    public void itShouldReturnEventsList() {
        List<Event> events = Arrays.asList(
                FakeFactory.prepareEventWithId(1L, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000")),
                FakeFactory.prepareEventWithId(2L, LocalDateTime.of(2018, Month.JANUARY, 2, 2, 2), "Kraków", new BigDecimal("2000"))
        );

        List<EventDto> eventDtos = Arrays.asList(
                FakeFactory.prepareEventDtoWithId(1L, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000")),
                FakeFactory.prepareEventDtoWithId(2L, LocalDateTime.of(2018, Month.JANUARY, 2, 2, 2), "Kraków", new BigDecimal("2000"))
        );

        when(repository.findAll()).thenReturn(events);
        when(mapper.mapAll(events)).thenReturn(eventDtos);

        assertEquals(service.getEvents(), eventDtos);
    }

    @Test
    public void itShouldReturnEventById() throws ElementNotFoundException {
        Long id = 1L;
        Event event = FakeFactory.prepareEventWithId(id, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000"));
        EventDto eventDto = FakeFactory.prepareEventDtoWithId(id, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000"));

        when(repository.findOne(id)).thenReturn(event);
        when(mapper.map(event)).thenReturn(eventDto);

        assertEquals(service.findOneById(id), eventDto);
    }

    @Test(expected = ElementNotFoundException.class)
    public void itShouldThrowExceptionWhenEventDoesNotExists() throws ElementNotFoundException {
        Long id = 1L;

        when(repository.findOne(id)).thenReturn(null);

        service.findOneById(id);
    }

    @Test
    public void itShouldAddEvent() {
        Event event = FakeFactory.prepareEvent(LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000"));
        EventDto eventDto = FakeFactory.prepareEventDto(LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000"));

        Event eventAdded = FakeFactory.prepareEventWithId(1L, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000"));
        EventDto eventDtoAdded = FakeFactory.prepareEventDtoWithId(1L, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000"));

        when(mapper.map(eventDto)).thenReturn(event);
        when(repository.save(event)).thenReturn(eventAdded);
        when(mapper.map(eventAdded)).thenReturn(eventDtoAdded);

        assertEquals(service.save(eventDto), eventDtoAdded);
    }

    @Test
    public void itShouldUpdateEvent() throws ElementNotFoundException {
        Long id = 1L;
        Event event = FakeFactory.prepareEventWithId(id, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000"));

        EventDto eventDto = FakeFactory.prepareEventDtoWithId(id, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000"));
        EventDto eventDtoUpdated = FakeFactory.prepareEventDtoWithId(id, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Krakow", new BigDecimal("1000"));

        when(mapper.map(eventDto)).thenReturn(event);
        when(repository.findOne(id)).thenReturn(event);

        event.setPlace("Krakow");

        when(repository.save(event)).thenReturn(event);
        when(mapper.map(event)).thenReturn(eventDtoUpdated);

        assertEquals(service.update(eventDto), eventDtoUpdated);
    }

    @Test(expected = ElementNotFoundException.class)
    public void itShouldThrowExceptionWhenUpdatedEventDoesNotExists() throws ElementNotFoundException {
        EventDto eventDto = FakeFactory.prepareEventDtoWithId(1L, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000"));

        when(repository.findOne(eventDto.getId())).thenReturn(null);

        service.update(eventDto);
    }

    @Test(expected = ElementNotFoundException.class)
    public void itShouldThrowExceptionWhenDeletedEventDoesNotExists() throws ElementNotFoundException {
        EventDto eventDto = FakeFactory.prepareEventDtoWithId(1L, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000"));

        when(repository.findOne(eventDto.getId())).thenReturn(null);

        service.delete(eventDto.getId());
    }
}
