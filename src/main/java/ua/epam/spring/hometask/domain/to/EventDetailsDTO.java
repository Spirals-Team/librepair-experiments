package ua.epam.spring.hometask.domain.to;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by Vladimir on 28.10.2017.
 */
public class EventDetailsDTO {
    private Long eventId;
    private String auditoriumName;
    private LocalDateTime airDate;

    public EventDetailsDTO(Long eventId, String auditoriumName, LocalDateTime airDate) {
        this.eventId = eventId;
        this.auditoriumName = auditoriumName;
        this.airDate = airDate;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getAuditoriumName() {
        return auditoriumName;
    }

    public void setAuditoriumName(String auditoriumName) {
        this.auditoriumName = auditoriumName;
    }

    public LocalDateTime getAirDate() {
        return airDate;
    }

    public void setAirDate(LocalDateTime airDate) {
        this.airDate = airDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, auditoriumName, airDate);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EventDetailsDTO other = (EventDetailsDTO) obj;
        if (eventId == null) {
            if (other.eventId != null) {
                return false;
            }
        } else if (!eventId.equals(other.eventId)) {
            return false;
        }
        if (auditoriumName == null) {
            if (other.auditoriumName != null) {
                return false;
            }
        } else if (!auditoriumName.equals(other.auditoriumName)) {
            return false;
        }
        if (airDate == null) {
            if (other.airDate != null) {
                return false;
            }
        } else if (!airDate.equals(other.airDate)) {
            return false;
        }
        return true;
    }

}
