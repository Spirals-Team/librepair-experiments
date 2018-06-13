package com.tul.ta.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleResourceDto {

    @JsonProperty(value = "Schedule")
    List<ScheduleDto> schedule;
}
