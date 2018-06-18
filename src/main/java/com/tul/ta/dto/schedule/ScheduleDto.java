package com.tul.ta.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScheduleDto {

    @JsonProperty(value = "TotalJourney")
    public TotalJourneyDto totalJourney;

    @JsonProperty(value = "Flight")
    public FlightDto flight;
}
