package com.epam.brest.course.model;

import java.util.Date;
import java.util.Objects;

/**
 * This class is a model for orders of petrol in this project.
 */
public class Order {
    /**
     * order id.
     */
    private Integer orderId;
    /**
     *petrol qty.
     */
    private Double petrolQty;

    /**
     *order_date.
      */
    private Date orderDate;
    /**
     * truck id from truck class.
     */
    private Integer truckId;

    /**
     * no args.
     */
    public Order() {
    }

    /**
     *
     * @param orderId .
     * @param qty .
     * @param date .
     * @param id .
     */
    public Order(Integer orderId, Double qty, Date date, Integer id) {
        this.orderId = orderId;
        this.petrolQty = qty;
        this.orderDate = date;
        this.truckId = id;
    }

    /**
     *
     * @return orderId.
     */
    public final Integer getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId1 for setter.
     */
    public final void setOrderId(final Integer orderId1) {
        this.orderId = orderId1;
    }

    /**
     *
     * @return petrol qty.
     */
    public final Double getPetrolQty() {
        return petrolQty;
    }

    /**
     *
     * @param qty for setter.
     */
    public final void setPetrolQty(final Double qty) {
        this.petrolQty = qty;
    }

    /**
     *
     * @return order_date.
     */
    public final Date getOrderDate() {
        return orderDate;
    }

    /**
     *
     * @param orderDate1 for setter.
     */
    public final void setOrderDate(final Date orderDate1) {
        this.orderDate = orderDate1;
    }

    /**
     *
     * @return truckId.
     */
    public final Integer getTruckId() {
        return truckId;
    }

    /**
     *
     * @param id for setter.
     */
    public final void setTruckId(final Integer id) {
        this.truckId = id;
    }


    /**
     * @param qty qty.
     * @param date date of order.
     * @param id id of truck.
     */
    public Order(final Double qty,
                 final Date date, final Integer id) {

        this.petrolQty = qty;
        this.orderDate = date;
        this.truckId = id;
    }

    /**
     *
     * @param o object.
     * @return boolean.
     */
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(getOrderId(), order.getOrderId())
                && Objects.equals(getPetrolQty(), order.getPetrolQty())
                && Objects.equals(getOrderDate(), order.getOrderDate())
                && Objects.equals(getTruckId(), order.getTruckId());
    }

    /**
     *
     * @return hashCode.
     */
    @Override
    public final int hashCode() {

        return Objects.hash(getOrderId(),
                getPetrolQty(),
                getOrderDate(),
                getTruckId());
    }
}
