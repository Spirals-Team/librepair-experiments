package ua.epam.spring.hometask.domain.to;

import java.time.LocalDateTime;

/**
 * Created by Vladimir_Vysokomorny on 05-Dec-17.
 */
public class PurchasedTicketDTO {
    private Long id;
    private String eventName;
    private LocalDateTime dateTime;
    private long seat;

    public PurchasedTicketDTO() {
    }

    public PurchasedTicketDTO(Long id, String eventName, LocalDateTime dateTime, long seat) {
        this.id = id;
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.seat = seat;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
