package com.example.playce;

import java.util.*;
import java.lang.*;

public class Result{

    private final String name;
    private final int price;
    private final double rating;
    private final String address;
    private final String category;
    private final double latitude;
    private final double longitude;
    private double distance;

    public Result(String name, int price, double rating, String address, double latitude, double longitude, String category) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.address = address;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean isEqualTO(Result r) {
        return (this.name.equals(r.name)
            && this.price == r.price
            && this.rating == r.rating
            && this.address.equals(r.address)
            && this.category.equals(r.category)
            && this.latitude == r.latitude
            && this.longitude == r.longitude
            && this.distance == r.distance);
    }

    public String getName() {
        return name;
    }
    public void setDistance(double distance){
        this.distance = distance;
    }
    public double getDistance(){
        return distance;
    }
    public int getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }
    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public double getLatitude() {
       return latitude;
    }

    public double getLongitude() {
       return longitude;
    }
    //implementing a Ccmparetor to sort and overriding the compareTo to compare distances
}
class SortByDistance implements Comparator <Result>{
    public int compare(Result a, Result b){
        if (a == null || b == null){
            if (a == null)
                return 1;
            else
                return -1;
        }

        if (a.getDistance() > b.getDistance()){
            return 1;
        }else if (a.getDistance() == b.getDistance()){
            return 0;
        }else{
            return -1;
        }
    }
}
