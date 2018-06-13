package com.tul.ta.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StopsDto {

    @JsonProperty(value = "StopQuantity")
    public Integer stopQuantity;
}
