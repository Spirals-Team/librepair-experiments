package com.tul.ta.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EquipmentDto {

    @JsonProperty(value = "AircraftCode")
    public String aircraftCode;
}
