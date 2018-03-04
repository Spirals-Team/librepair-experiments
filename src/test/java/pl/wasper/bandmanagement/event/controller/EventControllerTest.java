package pl.wasper.bandmanagement.event.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.wasper.bandmanagement.event.dto.EventDto;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;
import pl.wasper.bandmanagement.helper.FakeFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EventController.class)
public class EventControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    EventController controller;

    private ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Test
    public void itShouldReturnEventsList() throws Exception {
        List<EventDto> eventDtos = Arrays.asList(
                FakeFactory.prepareEventDtoWithId(1L, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000")),
                FakeFactory.prepareEventDtoWithId(2L, LocalDateTime.of(2018, Month.JANUARY, 2, 2, 2), "Kraków", new BigDecimal("2000"))
        );

        given(controller.eventList()).willReturn(eventDtos);

        mockMvc.perform(get("/events").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].date", is("2018-01-01 01:01")))
                .andExpect(jsonPath("$[0].place", is("Gorlice")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].date", is("2018-01-02 02:02")))
                .andExpect(jsonPath("$[1].place", is("Kraków")));
    }

    @Test
    public void itShouldReturnEventById() throws Exception {
        EventDto eventDto = FakeFactory.prepareEventDtoWithId(1L, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000.123"));

        given(controller.findEvent(1L)).willReturn(eventDto);

        mockMvc.perform(get("/events/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.date", is("2018-01-01 01:01")))
                .andExpect(jsonPath("$.place", is("Gorlice")))
                .andExpect(jsonPath("$.price", is(1000.12)));
    }

    @Test
    public void itShouldReturnErrorWhenSearchElementNotFound() throws Exception {
        given(controller.findEvent(1L)).willThrow(new ElementNotFoundException("Unable to find event with id 1"));

        mockMvc.perform(get("/events/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.method", is("GET")))
                .andExpect(jsonPath("$.message", is("Unable to find event with id 1")))
                .andExpect(jsonPath("$.url", is("http://localhost/events/1")));
    }


    @Test
    public void itShouldAddEvent() throws Exception {
        EventDto event = FakeFactory.prepareEventDto(LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000.234"));
        String eventJson = mapper.writeValueAsString(event);

        EventDto eventAdded = FakeFactory.prepareEventDtoWithId(1L, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000.234"));

        given(controller.addEvent(any(EventDto.class))).willReturn(eventAdded);

        mockMvc.perform(post("/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(eventJson)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.date", is("2018-01-01 01:01")))
                .andExpect(jsonPath("$.place", is("Gorlice")))
                .andExpect(jsonPath("$.price", is(1000.23)));
    }

    @Test
    public void itShouldReturnErrorWhenDateStringIsNotValid() throws Exception {
        given(controller.addEvent(any(EventDto.class)))
                .willThrow(new HttpMessageNotReadableException("Test", new JsonMappingException("Wrong date format. Try with 'yyyy-MM-dd HH:mm'")));

        mockMvc.perform(post("/events").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.method", is("POST")))
                .andExpect(jsonPath("$.message", is("Wrong date format. Try with 'yyyy-MM-dd HH:mm'")))
                .andExpect(jsonPath("$.url", is("http://localhost/events")));
    }

    @Test
    public void itShouldUpdateEvent() throws Exception {
        EventDto event = FakeFactory.prepareEventDtoWithId(1L, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000.234"));
        String eventJson = mapper.writeValueAsString(event);

        given(controller.updateEvent(any(EventDto.class))).willReturn(event);

        mockMvc.perform(put("/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(eventJson)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.date", is("2018-01-01 01:01")))
                .andExpect(jsonPath("$.place", is("Gorlice")))
                .andExpect(jsonPath("$.price", is(1000.23)));
    }

    @Test
    public void itShouldReturnErrorWhenUpdatedElementNotFound() throws Exception {
        EventDto event = FakeFactory.prepareEventDtoWithId(1L, LocalDateTime.of(2018, Month.JANUARY, 1, 1, 1), "Gorlice", new BigDecimal("1000.234"));
        String eventJson = mapper.writeValueAsString(event);

        given(controller.updateEvent(any(EventDto.class))).willThrow(new ElementNotFoundException("Unable to find event with id 1"));

        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventJson)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.method", is("PUT")))
                .andExpect(jsonPath("$.message", is("Unable to find event with id 1")))
                .andExpect(jsonPath("$.url", is("http://localhost/events")));
    }

    @Test
    public void itShouldRemoveEvent() throws Exception {
        doNothing().when(controller).removeEvent(1L);

        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void itShouldReturnErrorWhenRemovedEventDoesNotExists() throws Exception {
        doThrow(new ElementNotFoundException("Unable to find event with id 1")).when(controller).removeEvent(1L);

        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.method", is("DELETE")))
                .andExpect(jsonPath("$.message", is("Unable to find event with id 1")))
                .andExpect(jsonPath("$.url", is("http://localhost/events/1")));
    }
}
