package com.rgross.model;

import javax.persistence.*;

/**
 * Created by ryan_gross on 11/18/17.
 */
@Entity
@Table(name = "NAICS_CODES")
public class NaicsCode {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name= "ID", nullable=false, unique=true)
    private long id;

    @Column(name = "CODE")
    private Integer naicsCode;

    @Column(name = "DESCRIPTION")
    private String codeDescription;

    public Integer getNaicsCode() {
        return this.naicsCode;
    }

    public String getCodeDescription() {
        return this.codeDescription;
    }

    public NaicsCode(Integer naicsCode, String codeDescription) {
        this.naicsCode = naicsCode;
        this.codeDescription = codeDescription;
    }

}
