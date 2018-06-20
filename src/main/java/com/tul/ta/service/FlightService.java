package com.tul.ta.service;

import com.tul.ta.dto.schedule.FlightSchedulesDto;
import com.tul.ta.enums.AirportCode;

public interface FlightService {
    FlightSchedulesDto getSchedules(AirportCode origin,
                                    AirportCode destination,
                                    String departureDate,
                                    Boolean directFlight);
}
