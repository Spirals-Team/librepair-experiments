package com.epam.brest.course.utility.data;

import java.util.Date;
/**
 * class is the dto for ui , for order.
 */
public class OrderDto {
    /**
     * order id.
     */
    private Integer orderId;
    /**
     * qty petrol.
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
    public OrderDto() {
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
    public OrderDto(final Double qty,
                 final Date date, final Integer id) {

        this.petrolQty = qty;
        this.orderDate = date;
        this.truckId = id;
    }

}
