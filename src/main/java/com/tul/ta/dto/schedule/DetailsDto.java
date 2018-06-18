package com.tul.ta.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DetailsDto {

    @JsonProperty(value = "Stops")
    public StopsDto stops;

    @JsonProperty(value = "DaysOfOperation")
    public Integer daysOfOperation;

    @JsonProperty(value = "DatePeriod")
    public DatePeriodDto datePeriod;
}
