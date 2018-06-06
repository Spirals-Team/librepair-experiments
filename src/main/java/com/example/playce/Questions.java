package com.example.playce;

public class Questions {

    private final String name;
    private final int price;
    private final double rating;
    private final String address;

    public Questions(String name, int price, double rating, String address) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.address = address;
    }

    public String getName() {
        return name;
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
}
