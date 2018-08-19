package ua.epam.spring.hometask.domain.to;

import java.time.LocalDateTime;

/**
 * Created by Vladimir_Vysokomorny on 05-Dec-17.
 */
public class PurchasingTicketDTO {

    private String eventName;
    private LocalDateTime dateTime;
    private long seat;

    public PurchasingTicketDTO() {
    }

    public PurchasingTicketDTO(String eventName, LocalDateTime dateTime, long seat) {
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.seat = seat;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public long getSeat() {
        return seat;
    }

    public void setSeat(long seat) {
        this.seat = seat;
    }
}
