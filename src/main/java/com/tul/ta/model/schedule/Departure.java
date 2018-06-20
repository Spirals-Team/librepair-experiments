package com.tul.ta.model.schedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Departure {

    public String airportCode;
    public String scheduledTimeLocal;
    //public Integer terminal;
}
