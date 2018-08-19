package ua.epam.spring.hometask.domain.form;

import java.util.List;

public class PurchasingTicketsForm {
    private List<Long> seats;

    public List<Long> getSeats() {
        return seats;
    }

    public void setSeats(List<Long> seats) {
        this.seats = seats;
    }
}
