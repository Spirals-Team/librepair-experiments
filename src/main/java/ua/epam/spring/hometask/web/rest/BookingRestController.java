package ua.epam.spring.hometask.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.domain.UserAccount;
import ua.epam.spring.hometask.domain.to.TicketList;
import ua.epam.spring.hometask.service.BookingService;
import ua.epam.spring.hometask.service.EventService;
import ua.epam.spring.hometask.service.UserAccountService;
import ua.epam.spring.hometask.service.UserService;
import ua.epam.spring.hometask.web.rest.dto.BookingDetails;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
public class BookingRestController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserAccountService accountService;

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
    }


    @GetMapping(value = "/purchased/{eventId}/{dateTime}", produces = {"application/json"})
    public List<Ticket> getPurchasedTickets(@PathVariable("eventId") long eventId,
                                            @PathVariable("dateTime") LocalDateTime dateTime) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserByEmail(email);

        Event event = eventService.getById(eventId);
        List<Ticket> result = bookingService.getPurchasedTicketsForEvent(event, dateTime)
                .stream()
                .filter(t -> t.getUser().equals(user))
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping(value = "/purchased/{eventId}/{dateTime}", produces = {"application/pdf"})
    public TicketList getPurchasedTicketsInPdf(@PathVariable("eventId") long eventId,
                                               @PathVariable("dateTime") LocalDateTime dateTime,
                                               HttpServletResponse response) {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=tickets.pdf");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserByEmail(email);

        Event event = eventService.getById(eventId);
        Set<Ticket> tickets = bookingService
                .getPurchasedTicketsForEvent(event, dateTime)
                .stream()
                .filter(t -> t.getUser().equals(user))
                .collect(Collectors.toSet());
        TicketList result = new TicketList();
        result.setTickets(new ArrayList<>(tickets));
        return result;
    }


    @PostMapping("/getPrice/{eventId}/{dateTime}")
    public double getPrice(@PathVariable("eventId") long eventId,
                           @PathVariable("dateTime") LocalDateTime dateTime, @RequestBody Set<Long> seats) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserByEmail(email);
        Event event = eventService.getById(eventId);

        return bookingService.getTicketsPrice(event, dateTime, user, seats);
    }

    @PostMapping("/book/{eventId}/{dateTime}")
    public void bookTickets(@PathVariable("eventId") long eventId,
                            @PathVariable("dateTime") LocalDateTime dateTime,
                            @RequestBody BookingDetails details) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getUserByEmail(email);

        Event event = eventService.getById(eventId);
        Set<Ticket> tickets = details.getSeats().stream()
                .map(seat -> new Ticket(user, event, dateTime, seat))
                .collect(Collectors.toSet());

        UserAccount account = accountService.getById(details.getAccountId(), user.getId());
        bookingService.bookTickets(tickets, user.getId(), account, details.getPrice());
    }


}
