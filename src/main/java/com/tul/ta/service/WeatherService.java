package com.tul.ta.service;

import com.tul.ta.model.weather.Weather;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService {

    Weather getWeatherByCity(String city);
    Weather getWeatherByCityWithDate(String city, String date);
}
