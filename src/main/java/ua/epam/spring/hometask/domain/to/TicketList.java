package ua.epam.spring.hometask.domain.to;

import ua.epam.spring.hometask.domain.Ticket;

import java.util.List;

public class TicketList {
    private List<Ticket> tickets;

    public TicketList() {
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
