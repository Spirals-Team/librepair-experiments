package com.tul.ta.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DepartureDto {

    @JsonProperty(value = "AirportCode")
    public String airportCode;

    @JsonProperty(value = "ScheduledTimeLocal")
    public ScheduledTimeLocalDto scheduledTimeLocal;

    @JsonProperty(value = "Terminal")
    public TerminalDto terminal;
}
