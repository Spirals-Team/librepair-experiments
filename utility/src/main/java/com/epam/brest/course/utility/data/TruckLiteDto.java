package com.epam.brest.course.utility.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import java.util.Date;

public class TruckLiteDto {
    /**
     * id .
     */
    private Integer truckId;
    /**
     * truck_code it is unique.
     */
    @Valid
    private String truckCode;
    /**
     * this can be purchase date or when truck was added to company.
     */
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date purchasedDate;

    public TruckLiteDto() {
    }

    /**
     * this is descriptions.
     */
    private String descriptions;

    public Integer getTruckId() {
        return truckId;
    }

    public void setTruckId(Integer truckId) {
        this.truckId = truckId;
    }

    public String getTruckCode() {
        return truckCode;
    }

    public void setTruckCode(String truckCode) {
        this.truckCode = truckCode;
    }

    public Date getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(Date purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return "TruckLiteDto{" +
                "truckId=" + truckId
                + ", truckCode='" + truckCode + '\''
                + ", purchasedDate=" + purchasedDate
                + ", descriptions='" + descriptions + '\''
                + '}';
    }

}
