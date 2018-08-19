package ua.epam.spring.hometask.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import ua.epam.spring.hometask.domain.Auditorium;

import java.time.LocalDateTime;

/**
 * Created by Vladimir_Vysokomorny on 08-Dec-17.
 */
public class AirDateAuditoriumForm {
    /*2nd approach with EventForm for uploading event's file*/
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime airDate;
    private Auditorium auditorium;

    public AirDateAuditoriumForm() {
    }

    public AirDateAuditoriumForm(LocalDateTime airDate, Auditorium auditorium) {
        this.airDate = airDate;
        this.auditorium = auditorium;
    }

    public LocalDateTime getAirDate() {
        return airDate;
    }

    public void setAirDate(LocalDateTime airDate) {
        this.airDate = airDate;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }
}
