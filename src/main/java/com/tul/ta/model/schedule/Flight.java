package com.tul.ta.model.schedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Flight {

    public Departure departure;
    public Arrival arrival;
    //public MarketingCarrier marketingCarrier;
}
