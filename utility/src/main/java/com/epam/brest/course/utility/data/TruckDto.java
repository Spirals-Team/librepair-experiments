package com.epam.brest.course.utility.data;

import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.Valid;
import java.util.Date;

/**
 * Dto class for truck.
 */
public class TruckDto {
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
    /**
     * this is descriptions.
     */
    private String descriptions;

    /**
     *for avg per month.
     */
    private Double avgPerMonth;


    /**
     * default.
     */
    public TruckDto() {
    }
    /**
     *
     * @return date.
     */
    public final Date getPurchasedDate() {
        return purchasedDate;
    }

    /**
     *
     * @param purchasedDate1 for setter.
     */
    public final void setPurchasedDate(final Date purchasedDate1) {
        this.purchasedDate = purchasedDate1;
    }

    /**
     *
     * @return truck id.
     */
    public final Integer getTruckId() {
        return truckId;
    }

    /**
     *
     * @param truckId1 for setter.
     */
    public final void setTruckId(final Integer truckId1) {
        this.truckId = truckId1;
    }

    /**
     *
     * @return truck_code.
     */
    public final String getTruckCode() {
        return truckCode;
    }

    /**
     *
     * @param truckCode1 for setter.
     */
    public final void setTruckCode(final String truckCode1) {
        this.truckCode = truckCode1;
    }

    /**
     *
     * @return dsc.
     */
    public final String getDescriptions() {
        return descriptions;
    }

    /**
     *
     * @param descriptions setter.
     */
    public final void setDescriptions(final String descriptions) {
        this.descriptions = descriptions;
    }

    /**
     *
     * @return avg per month.
     */
    public Double getAvgPerMonth() {
        return avgPerMonth;
    }

    /**
     *
     * @param avgPerMonth setter.
     */
    public void setAvgPerMonth(Double avgPerMonth) {
        this.avgPerMonth = avgPerMonth;
    }
}
