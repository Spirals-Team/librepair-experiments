package net.mirwaldt.jcomparison.object.impl;

public class DummyAddress {
    private final String streetName;
    private final int houseNumber;

    public DummyAddress(String streetName, int houseNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }
}
