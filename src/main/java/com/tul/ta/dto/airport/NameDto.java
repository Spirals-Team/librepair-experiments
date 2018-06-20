package com.tul.ta.dto.airport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NameDto {

    @JsonProperty(value = "$")
    public String cityName;
}
