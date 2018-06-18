package de.swt.inf.model;

public class Location {

    private String name;

    private int zip;

    private String houseNr;

    private WeatherReport weatherReport;

    private Termin termin;

    public Location(String name, int zip, String houseNr) {
        this.name = name;
        this.zip = zip;
        this.houseNr = houseNr;
    }

    public int getZip() {
        return this.zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseNr() {
        return this.houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

}
