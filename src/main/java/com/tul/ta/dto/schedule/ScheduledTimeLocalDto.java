package com.tul.ta.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScheduledTimeLocalDto {

    @JsonProperty(value = "DateTime")
    public String dateTime;
}
