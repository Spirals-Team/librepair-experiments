package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import interfaces.ICity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class City implements ICity {
    private String name;
    private double lon;
    private double lat;

    public City(){
        
    }
    
    public City(String name, double lat, double lon){
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "City{" + "name=" + name + ", lon=" + lon + ", lat=" + lat + '}';
    }
    
}
