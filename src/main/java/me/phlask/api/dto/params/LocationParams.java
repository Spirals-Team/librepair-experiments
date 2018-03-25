package me.phlask.api.dto.params;

public class LocationParams {
    public double longitude;
    public double latitude;


    public LocationParams(String longitude, String latitude) {
        this.longitude = Double.parseDouble(longitude);
        this.latitude = Double.parseDouble(latitude);
    }

}
