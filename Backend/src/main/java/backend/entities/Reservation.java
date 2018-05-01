/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.entities;

import java.util.Date;

/**
 *
 * @author Stanislav
 */
public class Reservation {
    private String companyTag;
    private String customerMail;
    private Date fromDate;
    private Date toDate;

    public Reservation(String companyTag, String customerMail, Date fromDate, Date toDate) {
        this.companyTag = companyTag;
        this.customerMail = customerMail;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    public String getCompanyTag() {
        return companyTag;
    }

    public void setCompanyTag(String companyTag) {
        this.companyTag = companyTag;
    }

    public String getCustomerMail() {
        return customerMail;
    }

    public void setCustomerMail(String customerMail) {
        this.customerMail = customerMail;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

}
