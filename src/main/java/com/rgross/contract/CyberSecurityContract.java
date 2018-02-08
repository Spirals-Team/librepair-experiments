package com.rgross.contract;

import com.rgross.model.NaicsCode;

import javax.persistence.*;

/**
 * Created by ryan_gross on 12/26/17.
 */

@Entity
@Table(name = "CYBERSECURITY_CONTRACT")
public class CyberSecurityContract {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name= "ID", nullable=false, unique=true)
    private long id;

    @Column(name = "DUNS")
    private String dunsNumber;

    @Column(name = "DOLLARS_OBLIGATED")
    private double dollarsObligated;

    @Column(name = "FISCAL_YEAR")
    private int fiscalYear;

    @OneToOne
    @JoinColumn(name="NAICS_CODE", referencedColumnName = "ID")
    private NaicsCode naicsCode;

    public CyberSecurityContract(String dunsNumber, double dollarsObligated, int fiscalYear, NaicsCode naicsCode) {
        this.dunsNumber = dunsNumber;
        this.dollarsObligated = dollarsObligated;
        this.fiscalYear = fiscalYear;
        this.naicsCode = naicsCode;
    }

    public String getDunsNumber() {
        return dunsNumber;
    }

    public double getDollarsObligated() {
        return dollarsObligated;
    }

    public NaicsCode getNaicsCode() {
        return naicsCode;
    }

    public int getFiscalYear() {
        return fiscalYear;
    }
}
