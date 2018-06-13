package com.tul.ta.model.schedule;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarketingCarrier {

    public String airlineID;
    public Integer flightNumber;
}
