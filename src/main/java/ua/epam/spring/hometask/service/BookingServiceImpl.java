package ua.epam.spring.hometask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.epam.spring.hometask.domain.*;
import ua.epam.spring.hometask.repository.TicketRepository;
import ua.epam.spring.hometask.util.exception.BookingException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private TicketRepository ticketRepository;
    private DiscountService discountService;
    private AuditoriumService auditoriumService;

    @Autowired
    private UserAccountService accountService;

    public BookingServiceImpl(TicketRepository ticketRepository, DiscountService discountService, AuditoriumService auditoriumService) {
        this.ticketRepository = ticketRepository;
        this.discountService = discountService;
        this.auditoriumService = auditoriumService;
    }

    @PreAuthorize("hasAuthority('BOOKING_MANAGER')")
    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime, @Nullable User user, @Nonnull Set<Long> seats) {
        double movieRateIndex = 1;
        EventRating rating = event.getRating();
        if (rating == EventRating.LOW) {
            movieRateIndex = 0.8;
        } else if (rating == EventRating.HIGH) {
            movieRateIndex = 1.2;
        }

        double price = 0;
        Auditorium auditorium = auditoriumService.getByName(event.getAuditoriums().get(dateTime).getName());
        for (long seat : seats) {
            double ticket_price = event.getBasePrice();
            //if (event.getAuditoriums().get(dateTime).getVipSeats().contains(seat)) {
            if (auditorium.getVipSeats().contains(seat)) {
                ticket_price *= 2;
            }
            price += ticket_price;
        }
        return movieRateIndex * price * (100 - discountService.getDiscount(user, event, dateTime, seats.size())) / 100;

    }

    @PreAuthorize("hasAuthority('BOOKING_MANAGER')")
    @Transactional
    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets, long userId, UserAccount account, double price) {
        if (!ticketRepository.bookTickets(tickets)) {
            throw new BookingException("Can't book tickets for userId: " + userId + " via account: " + account + ". Price: " + price);
        }
        accountService.buy(account, price);
    }

    @PreAuthorize("hasAuthority('BOOKING_MANAGER')")
    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return ticketRepository.getAll()
                .stream()
                .filter(ticket -> ticket.getEvent().getName().equals(event.getName()) &&
                        ticket.getDateTime().equals(dateTime))
                .collect(Collectors.toSet());
    }
}
