package com.tul.ta.mapper;

import com.tul.ta.dto.schedule.FlightDto;
import com.tul.ta.model.schedule.Arrival;
import com.tul.ta.model.schedule.Departure;
import com.tul.ta.model.schedule.Flight;
import com.tul.ta.model.schedule.MarketingCarrier;
import org.springframework.stereotype.Component;

@Component
public class FlightDtoMapper implements Mapper<Flight, FlightDto> {
    @Override
    public Flight mapToEntity(FlightDto dto) {
        Departure departure = Departure.builder()
                .airportCode(dto.getDeparture().getAirportCode())
                .scheduledTimeLocal(dto.getDeparture().getScheduledTimeLocal().getDateTime()).build();
                //.terminal(dto.getDeparture().getTerminal().getName()).build();

        Arrival arrival = Arrival.builder()
                .airportCode(dto.getArrival().getAirportCode())
                .scheduledTimeLocal(dto.getArrival().getScheduledTimeLocal().getDateTime())
                .build();

//        MarketingCarrier marketingCarrier = MarketingCarrier.builder()
//                .airlineID(dto.getMarketingCarrier().getAirlineID())
//                .flightNumber(dto.getMarketingCarrier().getFlightNumber())
//                .build();


        return Flight.builder()
                .departure(departure)
                .arrival(arrival)
                //.marketingCarrier(marketingCarrier)
                .build();
    }

    @Override
    public FlightDto mapToDto(Flight entity) {
        return null;
    }
}
