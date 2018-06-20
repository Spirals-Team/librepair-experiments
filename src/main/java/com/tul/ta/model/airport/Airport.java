package com.tul.ta.model.airport;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@EqualsAndHashCode()
@Data
@Table(name = "airports")
public class Airport {

    @Id
    @Column(name = "airport_code")
    public String airportCode;
    @Column(name = "city_name")
    public String cityName;
    @Column(name = "city_code")
    public String cityCode;
    @Column(name = "country_code")
    public String countryCode;
    @Column(name = "utc_offset")
    public Integer utcOffset;
    @Column(name = "time_zone_id")
    public String timeZoneId;

    @Builder
    public Airport(String airportCode, String cityName, String cityCode, String countryCode, Integer utcOffset, String timeZoneId) {
        this.airportCode = airportCode;
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
        this.utcOffset = utcOffset;
        this.timeZoneId = timeZoneId;
    }

    @Tolerate
    public Airport() {

    }

    public String getOneCityName(){
        String arr[] = new String[2];
        if(cityName.contains("/")) {
            arr = cityName.split("/", 2);
            return arr[0];
        } if(cityName.contains(" ")) {
            arr = cityName.split(" ", 2);
            return arr[0];
        } else {
            return cityName;
        }
    }
}
