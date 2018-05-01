/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.entities;

import java.util.List;

/**
 *
 * @author Stanislav
 */
public class Car {

    private String logo;
    private String company;
    private String category;
    private String picture;
    private String make;
    private String model;
    private int year;
    private String regno;
    private int seats;
    private int doors;
    private String gear;
    private boolean aircondition;
    private String location;
    private int priceperday;
    private List<Reservation> reservations;

    public Car(String logo, String company, String category, String picture, String make, String model, int year, String regno, int seats, int doors, String gear, boolean aircondition, String location, int priceperday, List<Reservation> reservations) {
        this.logo = logo;
        this.company = company;
        this.category = category;
        this.picture = picture;
        this.make = make;
        this.model = model;
        this.year = year;
        this.regno = regno;
        this.seats = seats;
        this.doors = doors;
        this.gear = gear;
        this.aircondition = aircondition;
        this.location = location;
        this.priceperday = priceperday;
        this.reservations = reservations;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public String getGear() {
        return gear;
    }

    public void setGear(String gear) {
        this.gear = gear;
    }

    public boolean isAircondition() {
        return aircondition;
    }

    public void setAircondition(boolean aircondition) {
        this.aircondition = aircondition;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPriceperday() {
        return priceperday;
    }

    public void setPriceperday(int priceperday) {
        this.priceperday = priceperday;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

}
