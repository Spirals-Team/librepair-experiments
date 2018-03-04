package pl.wasper.bandmanagement.event.dto.mapper;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import pl.wasper.bandmanagement.event.dto.EventDto;
import pl.wasper.bandmanagement.event.model.Event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EventMapperTest {
    private EventMapper mapper;

    @Before
    public void before() {
        mapper = new EventMapper(new ModelMapper());
    }

    @Test
    public void itShouldMapEventtoEventDto() {
        Event event = prepareEvent(
                1L,
                LocalDateTime.of(2018, Month.FEBRUARY, 26, 13, 30),
                "ul. Kościuszki Gorlice",
                "Description",
                new BigDecimal("2000"),
                new BigDecimal("500"),
                true,
                true);

        EventDto eventDto = mapper.map(event);

        assertEquals(eventDto.getId(), event.getId());
        assertEquals(eventDto.getPlace(), event.getPlace());
        assertEquals(eventDto.getDate(), event.getDate());
        assertEquals(eventDto.getPlace(), event.getPlace());
        assertEquals(eventDto.getPrice(), event.getPrice());
        assertEquals(eventDto.getAdvance(), event.getAdvance());
        assertEquals(eventDto.isAdvancePaid(), event.isAdvancePaid());
        assertEquals(eventDto.isPaid(), event.isPaid());
    }

    @Test
    public void itShouldMapEventDtoToEvent() {
        EventDto eventDto = prepareEventDto(
                1L,
                LocalDateTime.of(2018, Month.FEBRUARY, 26, 13, 30),
                "ul. Kościuszki Gorlice",
                "Description",
                new BigDecimal("2000"),
                new BigDecimal("500"),
                true,
                true);

        Event event = mapper.map(eventDto);

        assertEquals(event.getId(), eventDto.getId());
        assertEquals(event.getPlace(), eventDto.getPlace());
        assertEquals(event.getDate(), eventDto.getDate());
        assertEquals(event.getPlace(), eventDto.getPlace());
        assertEquals(event.getPrice(), eventDto.getPrice());
        assertEquals(event.getAdvance(), eventDto.getAdvance());
        assertEquals(event.isAdvancePaid(), eventDto.isAdvancePaid());
        assertEquals(event.isPaid(), eventDto.isPaid());
    }

    @Test
    public void itShouldMapEventsListToEventDtosList() {
        Event eventOne = prepareEvent(
                1L,
                LocalDateTime.of(2020, Month.JANUARY, 01, 01, 01),
                "ul. Kościuszki Gorlice 1",
                "Description 1",
                new BigDecimal("1000"),
                new BigDecimal("100"),
                true,
                true);

        Event eventTwo = prepareEvent(
                2L,
                LocalDateTime.of(2021, Month.FEBRUARY, 02, 02, 02),
                "ul. Kościuszki Gorlice 2",
                "Description 2",
                new BigDecimal("2000"),
                new BigDecimal("200"),
                false,
                false);

        List<Event> events = Arrays.asList(eventOne, eventTwo);

        List<EventDto> eventDtos = mapper.mapAll(events);

        assertEquals(eventDtos.size(), events.size());
        assertEquals(eventDtos.get(0).getId(), events.get(0).getId());
        assertEquals(eventDtos.get(1).getDate(), events.get(1).getDate());
        assertEquals(eventDtos.get(0).getPlace(), events.get(0).getPlace());
        assertEquals(eventDtos.get(1).getPrice(), events.get(1).getPrice());
        assertEquals(eventDtos.get(0).getAdvance(), events.get(0).getAdvance());
        assertTrue(eventDtos.get(0).isAdvancePaid());
        assertTrue(eventDtos.get(0).isPaid());
        assertFalse(eventDtos.get(1).isAdvancePaid());
        assertFalse(eventDtos.get(1).isPaid());
    }

    @Test
    public void itShouldMapEventDtosListToEventsList() {
        EventDto eventDtoOne = prepareEventDto(
                1L,
                LocalDateTime.of(2020, Month.JANUARY, 01, 01, 01),
                "ul. Kościuszki Gorlice 1",
                "Description 1",
                new BigDecimal("1000"),
                new BigDecimal("100"),
                true,
                true);

        EventDto eventDtoTwo = prepareEventDto(
                2L,
                LocalDateTime.of(2021, Month.FEBRUARY, 02, 02, 02),
                "ul. Kościuszki Gorlice 2",
                "Description 2",
                new BigDecimal("2000"),
                new BigDecimal("200"),
                false,
                false);

        List<EventDto> eventDtos = Arrays.asList(eventDtoOne, eventDtoTwo);

        List<Event> events = mapper.mapAllDto(eventDtos);

        assertEquals(events.size(), eventDtos.size());
        assertEquals(events.get(0).getId(), eventDtos.get(0).getId());
        assertEquals(events.get(1).getDate(), eventDtos.get(1).getDate());
        assertEquals(events.get(0).getPlace(), eventDtos.get(0).getPlace());
        assertEquals(events.get(1).getPrice(), eventDtos.get(1).getPrice());
        assertEquals(events.get(0).getAdvance(), eventDtos.get(0).getAdvance());
        assertTrue(events.get(0).isAdvancePaid());
        assertTrue(events.get(0).isPaid());
        assertFalse(events.get(1).isAdvancePaid());
        assertFalse(events.get(1).isPaid());
    }

    private Event prepareEvent(
            Long id,
            LocalDateTime date,
            String place,
            String description,
            BigDecimal price,
            BigDecimal advance,
            boolean isAdvancePaid,
            boolean isPaid) {
        Event event = new Event();
        event.setId(id);
        event.setDate(date);
        event.setPlace(place);
        event.setDescription(description);
        event.setPrice(price);
        event.setAdvance(advance);
        event.setAdvancePaid(isAdvancePaid);
        event.setPaid(isPaid);

        return event;
    }

    private EventDto prepareEventDto(
            Long id,
            LocalDateTime date,
            String place,
            String description,
            BigDecimal price,
            BigDecimal advance,
            boolean isAdvancePaid,
            boolean isPaid) {
        EventDto eventDto = new EventDto();
        eventDto.setId(id);
        eventDto.setDate(date);
        eventDto.setPlace(place);
        eventDto.setDescription(description);
        eventDto.setPrice(price);
        eventDto.setAdvance(advance);
        eventDto.setAdvancePaid(isAdvancePaid);
        eventDto.setPaid(isPaid);

        return eventDto;
    }
}
