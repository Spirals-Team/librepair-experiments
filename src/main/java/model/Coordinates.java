package model;

import java.util.Objects;

public class Coordinates {
    double lat;
    double lng;
    public Coordinates(double lat, double lng){
        this.lat=lat;
        this.lng=lng;
    }

    @Override
    public String toString() {
        return "{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.lat, lat) == 0 &&
                Double.compare(that.lng, lng) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(lat, lng);
    }
}
