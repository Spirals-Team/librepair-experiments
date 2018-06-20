package com.tul.ta.dto.airport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FlightAirportsDto {

    @JsonProperty(value = "AirportResource")
    public AirportResourceDto airportResource;
}
