package com.tul.ta.dto.airport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AirportsDto {
    @JsonProperty(value = "Airport")
    public List<AirportDto> airport;
}
