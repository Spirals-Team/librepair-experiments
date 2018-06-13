package com.tul.ta.dto.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DatePeriodDto {

    @JsonProperty(value = "Effective")
    public String effective;

    @JsonProperty(value = "Expiration")
    public String expiration;
}
