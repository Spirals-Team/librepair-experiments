package com.tul.ta.dto.airport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class AirportDto {

    @JsonProperty(value = "AirportCode")
    public String airportCode;
    @JsonProperty(value = "Position")
    public PositionDto position;
    @JsonProperty(value = "CityCode")
    public String cityCode;
    @JsonProperty(value = "CountryCode")
    public String countryCode;
    @JsonProperty(value = "LocationType")
    public String locationType;
    @JsonProperty(value = "Names")
    public NamesDto names;
    @JsonProperty(value = "UtcOffset")
    public Integer utcOffset;
    @JsonProperty(value = "TimeZoneId")
    public String timeZoneId;

    //TODO Combined @Data and @Builder hide empty constructor, is it correct?
    @Tolerate
    public AirportDto() {

    }
}
