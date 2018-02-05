package io.descoped.client.external.google;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class GeoLocation {

    private static final Logger log = LoggerFactory.getLogger(GeoLocation.class);

    private final String json;
//    private Map<String,String> locMap = new HashMap<>();
    private Double centerLongitude;
    private Double centerLatitude;
    private Double northeastLongitude;
    private Double northeastLatitude;
    private Double southwestLongitude;
    private Double southwestLatitude;

    public GeoLocation(String json) {
        this.json = json;
        load();
    }

    private void load() {
        DocumentContext ctx = JsonPath.parse(json);
        Map<String,Map<String,Map<String,String>>> node = ctx.read("$.results[0].geometry");

        Map<String, Map<String,String>> loc = node.get("location");
        centerLongitude = Double.valueOf(String.valueOf(loc.get("lng")));
        centerLatitude = Double.valueOf(String.valueOf(loc.get("lat")));

        Map<String, Map<String,String>> bounds = node.get("bounds");
        Map<String, String> northeast = bounds.get("northeast");
        northeastLongitude = Double.valueOf(String.valueOf(northeast.get("lng")));
        northeastLatitude = Double.valueOf(String.valueOf(northeast.get("lat")));

        Map<String, String> southwest = bounds.get("southwest");
        southwestLongitude = Double.valueOf(String.valueOf(southwest.get("lng")));
        southwestLatitude = Double.valueOf(String.valueOf(southwest.get("lat")));
    }

    public String getJson() {
        return json;
    }

    public Double getCenterLongitude() {
        return centerLongitude;
    }

    public Double getCenterLatitude() {
        return centerLatitude;
    }

    public Double getNortheastLongitude() {
        return northeastLongitude;
    }

    public Double getNortheastLatitude() {
        return northeastLatitude;
    }

    public Double getSouthwestLongitude() {
        return southwestLongitude;
    }

    public Double getSouthwestLatitude() {
        return southwestLatitude;
    }

    public Double getNorthEastRadius() {
        return distance(centerLatitude, centerLongitude, northeastLatitude, northeastLongitude) * 1000;
    }

    public Double getSouthWestRadius() {
        return distance(centerLatitude, centerLongitude, southwestLatitude, southwestLongitude) * 1000;
    }

    // https://dzone.com/articles/distance-calculation-using-3
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        String sr = "K";
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515; // miles
        if (sr.equals("K")) { // km
            dist = dist * 1.609344;
        } else if (sr.equals("N")) { // nautical miles
            dist = dist * 0.8684;
        }
        return (dist);
    }

    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }


    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public String toString() {
        return "GeoLocation{" +
                "centerLongitude='" + centerLongitude + '\'' +
                ", centerLatitude='" + centerLatitude + '\'' +
                ", northeastLongitude='" + northeastLongitude + '\'' +
                ", northeastLatitude='" + northeastLatitude + '\'' +
                ", southwestLongitude='" + southwestLongitude + '\'' +
                ", southwestLatitude='" + southwestLatitude + '\'' +
                ", northeastRadius='" + getNorthEastRadius() + '\'' +
                ", southwestRadius='" + getSouthWestRadius() + '\'' +
                '}';
    }
}
