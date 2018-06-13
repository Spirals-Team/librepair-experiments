package com.tul.ta.model.schedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Arrival {

    public String airportCode;
    public String scheduledTimeLocal;
}
