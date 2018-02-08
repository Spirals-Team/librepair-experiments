package com.rgross.model;

import javax.persistence.*;

/**
 * Created by ryan_gross on 9/26/17.
 */

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name="COUNTY_PERCENTAGE")
    private double countyPercentage;

    @Column(name="CITY")
    private String city;

    @Column(name="ZIP_CODE")
    private String zipCode;

    @Column(name="STATE")
    private String state;

    @OneToOne
    @JoinColumn(name="COUNTY", referencedColumnName = "ID")
    private County county;

//    @Column(name="COUNTRY")
//    private String country;

    public Location(String city, String stateCode, String zipCode, County county, Double countyPercentage) {
        this.city = city;
        this.state = stateCode;
        this.zipCode = zipCode;
        this.county = county;
        this.countyPercentage = countyPercentage;
    }

    public Location(double countyPercentage) {
        this.countyPercentage = countyPercentage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStateCode() {
        return state;
    }

    public void setStateCode(String stateCode) {
        this.state = stateCode;
    }



    public double getCountyPercentage() {
        return countyPercentage;
    }

    public void setCountyPercentage(double countyPercentage) {
        this.countyPercentage = countyPercentage;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    @Override
    public String toString() {
        return "Location{" +
                "countyPercentage=" + countyPercentage +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", state='" + state + '\'' +
                ", county=" + county +
                '}';
    }
}
