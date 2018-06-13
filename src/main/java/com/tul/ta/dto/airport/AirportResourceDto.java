package com.tul.ta.dto.airport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AirportResourceDto {

    @JsonProperty(value = "Airports")
    public AirportsDto airports;
}
