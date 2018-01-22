package net.mirwaldt.jcomparison.core.object.impl;

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
