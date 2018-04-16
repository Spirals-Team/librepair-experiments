package com.gdc.aerodev.model;

/**
 * {@code Cr} is the company representative. Only checked persons can register as {@code Cr} from his company.
 * Being interested {@code Cr} can make offer to {@code User} to make some {@code Project}. All relationships
 * between {@code Cr} and {@code User} will bind in SQL {@code Offer} descriptor table.
 *
 * @author Yusupov Danil
 */
public class Cr {

    private Long crId;
    private String crName;
    private String crPassword;
    private String crEmail;

    public Cr() {
    }

    public Cr(Long crId, String crName, String crPassword, String crEmail) {
        this.crId = crId;
        this.crName = crName;
        this.crPassword = crPassword;
        this.crEmail = crEmail;
    }

    public Long getCrId() {
        return crId;
    }

    public String getCrName() {
        return crName;
    }

    public String getCrPassword() {
        return crPassword;
    }

    public String getCrEmail() {
        return crEmail;
    }
}
