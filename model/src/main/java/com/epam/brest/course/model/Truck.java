package com.epam.brest.course.model;

import java.util.Date;
import java.util.Objects;

/**
 * truck class.
 */
public class Truck {

    /**
     * truck id.
     */
    private Integer truckId;
    /**
     * truck_code it is unique.
     */
    private String truckCode;
    /**
     * this can be purchase date or when truck was added to company.
     */
    private Date purchasedDate;
    /**
     * this is descriptions.
     */
    private String descriptions;

    /**
     * default.
     */
    public Truck() {
    }

    /**
     *
     * @param truckCode1 .
     * @param purchasedDate1 .
     * @param description1 .
     */
    public Truck(final String truckCode1, final Date purchasedDate1,
                 final String description1) {
        this.truckCode = truckCode1;
        this.purchasedDate = purchasedDate1;
        this.descriptions = description1;
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
     * @return descriptions.
     */
    public final String getDescriptions() {
        return descriptions;
    }

    /**
     *
     * @param description1 for setter.
     */
    public final void setDescriptions(final String description1) {
        this.descriptions = description1;
    }


    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Truck)) {
            return false;
        }
        Truck truck = (Truck) o;
        return Objects.equals(getTruckId(), truck.getTruckId())
                && Objects.equals(getTruckCode(), truck.getTruckCode())
                && Objects.equals(getPurchasedDate(), truck.getPurchasedDate())
                && Objects.equals(getDescriptions(), truck.getDescriptions());
    }

    @Override
    public final int hashCode() {

        return Objects.hash(getTruckId(),
                getTruckCode(), getPurchasedDate(),
                getDescriptions());
    }

    @Override
    public final String toString() {
        return "Truck{"
                + "truckId=" + truckId
                + ", truckCode='" + truckCode + '\''
                + ", purchasedDate=" + purchasedDate
                + ", descriptions='" + descriptions + '\''
                + '}';
    }
}
