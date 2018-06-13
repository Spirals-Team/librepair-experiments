package com.tul.ta.dto.airport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CoordinateDto {

    @JsonProperty(value = "Latitude")
    public Double latitude;

    @JsonProperty(value = "Longitude")
    public Double longitude;
}
