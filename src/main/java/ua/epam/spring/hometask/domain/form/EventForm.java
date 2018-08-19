package ua.epam.spring.hometask.domain.form;

import ua.epam.spring.hometask.domain.EventRating;

import java.util.List;

/**
 * Created by Vladimir_Vysokomorny on 08-Dec-17.
 */
public class EventForm {
    private Long id;
    private String name;
    private double basePrice;
    private EventRating rating;
    private List<AirDateAuditoriumForm> airDateAuditoriums;

    public EventForm() {
    }

    public EventForm(Long id, String name, double basePrice, EventRating rating, List<AirDateAuditoriumForm> airDateAuditoriums) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.rating = rating;
        this.airDateAuditoriums = airDateAuditoriums;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(EventRating rating) {
        this.rating = rating;
    }

    public List<AirDateAuditoriumForm> getAirDateAuditoriums() {
        return airDateAuditoriums;
    }

    public void setAirDateAuditoriums(List<AirDateAuditoriumForm> airDateAuditoriums) {
        this.airDateAuditoriums = airDateAuditoriums;
    }
}
