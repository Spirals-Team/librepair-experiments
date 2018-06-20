package com.tul.ta.model.weather;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Weather {

    private int lowTemperature;
    private int highTemperature;
    private String description;
    private String date;
    private String city;

    public Weather(int lowTemperature, int highTemperature, String description, Date date, String city) {
        this.lowTemperature = lowTemperature;
        this.highTemperature = highTemperature;
        this.description = description;
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(date);
        this.city = city;
    }

    @Override
    public String toString() {
        return "[" + date + "] " + city  + " " + description + " lowest temperature: " + lowTemperature + " Highest temperature: " + highTemperature;
    }
}
