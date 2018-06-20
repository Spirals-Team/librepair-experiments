package com.tul.ta.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FlightDto {

    @JsonProperty(value = "Departure")
    public DepartureDto departure;

    @JsonProperty(value = "Arrival")
    public ArrivalDto arrival;

    @JsonProperty(value = "MarketingCarrier")
    public MarketingCarrierDto marketingCarrier;

    @JsonProperty(value = "Equipment")
    public EquipmentDto equipment;

    @JsonProperty(value = "Details")
    public DetailsDto details;
}
