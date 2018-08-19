package ua.epam.spring.hometask.web.rest.dto;

import java.util.Set;

public class BookingDetails {

    private Set<Long> seats;
    private long accountId;
    private double price;

    public BookingDetails() {
    }

    public Set<Long> getSeats() {
        return seats;
    }

    public void setSeats(Set<Long> seats) {
        this.seats = seats;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
