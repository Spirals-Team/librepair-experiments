package me.phlask.api.dto.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class TapRequest {
    private double latitude;
    private double longitude;
    private String name;
    private String description;


    public double getLatitude() {
        return latitude;
    }

    @JsonSetter("latitude")
    public TapRequest setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    @JsonGetter("latitude")
    public double getLongitude() {
        return longitude;
    }

    @JsonSetter("longitude")
    public TapRequest setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public TapRequest setName(String name) {
        this.name = name;
        return this;
    }

    @JsonGetter("description")
    public String getDescription() {
        return description;
    }

    @JsonSetter("description")
    public TapRequest setDescription(String description) {
        this.description = description;
        return this;
    }
}
