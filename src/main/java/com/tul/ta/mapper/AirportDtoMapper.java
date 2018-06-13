package com.tul.ta.mapper;

import com.tul.ta.dto.airport.AirportDto;
import com.tul.ta.model.airport.Airport;
import org.springframework.stereotype.Component;

@Component
public class AirportDtoMapper implements Mapper<Airport, AirportDto> {

    @Override
    public Airport mapToEntity(AirportDto dto) {
        return Airport.builder()
                .airportCode(dto.getAirportCode())
                .cityName(dto.getNames().getName().getCityName())
                .cityCode(dto.getCityCode())
                .countryCode(dto.getCountryCode())
                .utcOffset(dto.getUtcOffset())
                .timeZoneId(dto.getTimeZoneId())
                .build();
    }

    @Override
    public AirportDto mapToDto(Airport entity) {
        return AirportDto.builder()
                .build();
    }
}
