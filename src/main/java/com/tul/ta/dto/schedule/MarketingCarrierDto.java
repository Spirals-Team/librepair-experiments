package com.tul.ta.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MarketingCarrierDto {

    @JsonProperty(value = "AirlineID")
    public String airlineID;

    @JsonProperty(value = "FlightNumber")
    public Integer flightNumber;
}
