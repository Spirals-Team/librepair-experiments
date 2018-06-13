package com.tul.ta.dto.airport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PositionDto {

    @JsonProperty(value = "Coordinate")
    public CoordinateDto coordinate;
}
