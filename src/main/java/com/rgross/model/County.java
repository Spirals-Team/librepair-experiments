package com.rgross.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by ryan_gross on 11/11/17.
 */
@Entity
@Table(name = "counties")
public class County {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name="FIPS")
    private String fipsNumber;

    @Column(name="NAME")
    private String countyName;

    public County(String fipsNumber, String countyName) {
        this.fipsNumber = fipsNumber;
        this.countyName = countyName;
    }

    public County() {};

    public String getFipsNumber() {
        return fipsNumber;
    }

    public String getCountyName() {
        return countyName;
    }


}
