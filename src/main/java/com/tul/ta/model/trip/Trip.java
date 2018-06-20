package com.tul.ta.model.trip;

import com.tul.ta.model.schedule.Flight;
import com.tul.ta.model.weather.Weather;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Trip {
    private List<Flight> flights;
    private Weather weather;


}
