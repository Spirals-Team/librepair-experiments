package ua.epam.spring.hometask.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventDeserializer;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.web.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventRestController {

    @Autowired
    private EventService eventService;

    @PostMapping("/event")
    @ResponseStatus(HttpStatus.CREATED)
    public Event create(@RequestBody Event event) {
        return eventService.save(event);
    }

    @GetMapping("/event/{id}")
    public Event getById(@PathVariable long id) {
        Event event = eventService.getById(id);
        if (event == null) {
            throw new ResourceNotFoundException("Event", "id", id);
        }
        return eventService.getById(id);
    }

    @PutMapping("/event/{id}")
    public Event update(@PathVariable("id") long id, @RequestBody Event eventDetails) {
        Event event = eventService.getById(id);
        if (event == null) {
            throw new ResourceNotFoundException("Event", "id", id);
        }

        event.setName(eventDetails.getName());
        event.setBasePrice(eventDetails.getBasePrice());
        event.setTicketPrice(eventDetails.getTicketPrice());
        event.setRating(eventDetails.getRating());
        event.setAirDates(eventDetails.getAirDates());
        event.setAuditoriums(eventDetails.getAuditoriums());

        return eventService.save(event);
    }


    @DeleteMapping("/event/{id}")
    public void delete(@PathVariable("id") long id) {
        Event event = eventService.getById(id);
        if (event == null) {
            throw new ResourceNotFoundException("Event", "id", id);
        }
        eventService.remove(event);
    }

    @GetMapping("/event")
    public Collection<Event> getAll() {
        return eventService.getAll();
    }

    @PostMapping("event/upload")
    public void uploadEvents(@RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String json = new String(file.getBytes());
            System.out.println(json);
            ObjectMapper mapper = new ObjectMapper();
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Event.class, new EventDeserializer());
            mapper.registerModule(module);

            List<Event> events = mapper.readValue(json, new TypeReference<List<Event>>() {
            });
            events.forEach(event -> eventService.save(event));

        }
    }

}
