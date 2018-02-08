package com.rgross.model;

import javax.persistence.*;

/**
 * Created by ryan_gross on 9/26/17.
 */
@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="VENDOR_NM")
    private String vendorName;

    @Column(name="ST_ADDR")
    private String streetAddress;

    @OneToOne(targetEntity = Location.class)
    @JoinColumn(name="LOCATION", referencedColumnName = "ID")
    private Location vendorLocation;

    @Column(name = "IS_OUT_OF_STATE")
    private boolean isOutOfState;

    public boolean isOutOfState() {
        return isOutOfState;
    }

    public void setOutOfState(boolean outOfState) {
        isOutOfState = outOfState;
    }

    public Vendor() {}

    public Vendor(String vendorName, String streetAddress, Location vendorLocation) {
        this.vendorName = vendorName;
        this.streetAddress = streetAddress;
        this.vendorLocation = vendorLocation;
    }

    public String getVendorName() {
        return vendorName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public Location getVendorLocation() {
        return vendorLocation;
    }

    public void setVendorLocation(Location vendorLocation) {
        this.vendorLocation = vendorLocation;
    }

    public boolean getIsOutOfState() {
        return this.isOutOfState;
    }

    public void setIsOutOfState(boolean isOutOfState) {
        this.isOutOfState = isOutOfState;
    }

    public boolean setIsOutOfState(String popState, String vendorState) {
        return popState.equalsIgnoreCase(vendorState);
    }

}
