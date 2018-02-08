package com.rgross.contract;

import com.rgross.model.Location;
import com.rgross.model.NaicsCode;
import com.rgross.model.PlaceOfPerformance;
import com.rgross.model.Vendor;

import javax.persistence.*;

/**
 * Created by ryan_gross on 9/9/17.
 */
@Entity
@Table(name = "CONTRACTS")
public class GeneralContract {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name= "ID", nullable=false, unique=true)
    private long id;

    @Column(name="DOLLARS_OBLIGATED")
    private double dollarsObligated;

    @Column(name="DUNS")
    private String dunsNumber;

    @Column(name="FISCAL_YEAR")
    private int fiscalYear;

    @OneToOne
    @JoinColumn(name="NAICS_CODE", referencedColumnName = "ID")
    private NaicsCode naicsCode;

    @OneToOne
    @JoinColumn(name="PLACE_OF_PERFORMANCE_LOCATION", referencedColumnName = "ID")
    private Location placeOfPerformanceLocation;

    @OneToOne
    @JoinColumn(name="VENDOR", referencedColumnName = "ID")
    private Vendor vendor;

    @Column(name="CYBERSECURITY_FLAG")
    private boolean isCyberSecurityContract;

    public GeneralContract() {}

    public GeneralContract(int dollarsObligated, String dunsNumber, int fiscalYear,
                           Vendor vendor, NaicsCode naicsCode) {

        this.dollarsObligated = dollarsObligated;
        this.dunsNumber = dunsNumber;
        this.fiscalYear = fiscalYear;
        this.vendor = vendor;
        this.naicsCode = naicsCode;
    }


    public void setFiscalYear(int fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public void setDollarsObligated(double dollarsObligated) {
        this.dollarsObligated = dollarsObligated;
    }

    public void setDunsNumber(String dunsNumber) {
        this.dunsNumber = dunsNumber;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Location getPlaceOfPerformanceLocation() {
        return placeOfPerformanceLocation;
    }

    public void setPlaceOfPerformanceLocation(Location placeOfPerformanceLocation) {
        this.placeOfPerformanceLocation = placeOfPerformanceLocation;
    }

    public void setNaicsCode(NaicsCode naicsCode) {
        this.naicsCode = naicsCode;
    }

    public boolean getIsCyberSecurityContract() {
        return isCyberSecurityContract;
    }

    public void setIsCyberSecurityContract(boolean cyberSecurity) {
        isCyberSecurityContract = cyberSecurity;
    }
}
