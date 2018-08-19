package ua.epam.spring.hometask.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.EventDeserializer;
import ua.epam.spring.hometask.domain.form.AirDateAuditoriumForm;
import ua.epam.spring.hometask.domain.form.EventForm;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.EventService;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private AuditoriumService auditoriumService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
            }

            @Override
            public String getAsText() throws IllegalArgumentException {
                return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDateTime) getValue());
            }
        });
        binder.registerCustomEditor(Auditorium.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                Auditorium auditorium = new Auditorium();
                auditorium.setName(text);
                setValue(auditorium);
            }

            @Override
            public String getAsText() {
                return ((Auditorium) getValue()).getName();
            }
        });
    }

    @RequestMapping("/event/id")
    public ModelAndView getById(@RequestParam long id) {

        ModelAndView mav = new ModelAndView("event");

        Event event = eventService.getById(id);

        List<AirDateAuditoriumForm> dateAuditoriumList = new ArrayList<>();
        event.getAuditoriums().forEach((dateTime, auditorium) -> dateAuditoriumList.add(new AirDateAuditoriumForm(dateTime, auditorium)));

        EventForm eventForm = new EventForm(
                event.getId(),
                event.getName(),
                event.getBasePrice(),
                event.getRating(),
                dateAuditoriumList);


        mav.addObject("event", eventForm);
        mav.addObject("auditoriums", auditoriumService.getAll()
                .stream()
                .sorted(Comparator.comparing(Auditorium::getName))
                .collect(Collectors.toList()));

        return mav;
    }

    @RequestMapping("/event/add")
    public ModelAndView addEvent() {

        ModelAndView mav = new ModelAndView("event");

        mav.addObject("event", new EventForm());
        mav.addObject("auditoriums", auditoriumService.getAll()
                .stream()
                .sorted(Comparator.comparing(Auditorium::getName))
                .collect(Collectors.toList()));

        return mav;
    }

    @RequestMapping(value = "/event/addEvent", method = RequestMethod.POST)
    public String submit(@ModelAttribute("event") EventForm event, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }
        Event createdEvent = new Event();
        createdEvent.setId(event.getId());
        createdEvent.setName(event.getName());
        createdEvent.setBasePrice(event.getBasePrice());
        createdEvent.setRating(event.getRating());

        NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
        event.getAirDateAuditoriums().forEach(entry -> auditoriums.put(entry.getAirDate(), entry.getAuditorium()));

        createdEvent.setAuditoriums(auditoriums);
        createdEvent.setAirDates(new TreeSet<>(auditoriums.keySet()));
        eventService.save(createdEvent);

        return "redirect:/events";
    }

    /*1st approach with EventDeserializer*/
    @RequestMapping(value = "/event/addEventsByFile", method = RequestMethod.POST)
    public String uploadEvents(@RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String json = new String(file.getBytes());
            System.out.println(json);
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            SimpleModule module = new SimpleModule();
            module.addDeserializer(Event.class, new EventDeserializer());
            mapper.registerModule(module);

            List<Event> events = mapper.readValue(json, new TypeReference<List<Event>>() {
            });
            events.forEach(event -> eventService.save(event));


        }

        return "redirect:/events";
    }

    /*2nd approach with @JsonFormat(pattern = "yyyy-MM-dd HH:mm") in AirDateAuditoriumForm*/
//    @RequestMapping(value = "/event/addEventsByFile", method = RequestMethod.POST)
//    public String uploadEvents(@RequestParam("file") MultipartFile file) throws IOException {
//
//        if (!file.isEmpty()) {
//            String json = new String(file.getBytes());
//            System.out.println(json);
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.findAndRegisterModules();
//
//
//            List<EventForm> events = mapper.readValue(json, new TypeReference<List<EventForm>>() {
//            });
//            events.forEach(event -> {
//                Event createdEvent = new Event();
//                createdEvent.setId(event.getId());
//                createdEvent.setName(event.getName());
//                createdEvent.setBasePrice(event.getBasePrice());
//                createdEvent.setRating(event.getRating());
//
//                NavigableMap<LocalDateTime, Auditorium> auditoriums = new TreeMap<>();
//                event.getAirDateAuditoriums().forEach(entry -> auditoriums.put(entry.getAirDate(), entry.getAuditorium()));
//
//                createdEvent.setAuditoriums(auditoriums);
//                createdEvent.setAirDates(new TreeSet<>(auditoriums.keySet()));
//                eventService.save(createdEvent);
//            });
//        }
//
//        return "redirect:/events";
//    }


    @RequestMapping("/events")
    public ModelAndView getAll() {

        ModelAndView mav = new ModelAndView("events");
        List<Event> events = new ArrayList<>(eventService.getAll());
        mav.addObject("events", events);

        return mav;
    }

    @RequestMapping(value = "/event/removeEvent")
    public String remove(@RequestParam("id") long eventId) {

        Event e = new Event();
        e.setId(eventId);
        eventService.remove(e);

        return "redirect:/events";
    }

}
