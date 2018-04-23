package com.epam.brest.course.dto;

import com.epam.brest.course.model.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class TruckFullDetailDto {

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
     * list of orders.
     */
    private List<Order> orderList = new ArrayList<>();
    /**
     * avg per month.
     */
    private Double avgPerMonth;


    public TruckFullDetailDto(Integer truckId, String truckCode, Date purchasedDate,
                              String descriptions,
                              Double avgPerMonth) {
        this.truckId = truckId;
        this.truckCode = truckCode;
        this.purchasedDate = purchasedDate;
        this.descriptions = descriptions;
         this.avgPerMonth = avgPerMonth;
    }

    public TruckFullDetailDto() {
    }

    /**
     * @return id.
     */
    public Integer getTruckId() {
        return truckId;
    }

    /**
     * @param id .
     */
    public void setTruckId(Integer id) {
        this.truckId = id;
    }

    /**
     * @return code.
     */
    public String getTruckCode() {
        return truckCode;
    }

    /**
     * @param code setter.
     */
    public void setTruckCode(String code) {
        this.truckCode = code;
    }

    /**
     * @return date.
     */
    public Date getPurchasedDate() {
        return purchasedDate;
    }

    /**
     * @param date date.
     */
    public void setPurchasedDate(Date date) {
        this.purchasedDate = date;
    }

    /**
     * @return descriptions.
     */
    public String getDescriptions() {
        return descriptions;
    }

    /**
     * @param description setter.
     */
    public void setDescriptions(String description) {
        this.descriptions = description;
    }

    /**
     * @return list.
     */
    public List<Order> getOrderList() {
        return orderList;
    }

    /**
     * @param orderList setter.
     */
    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    /**
     * @return avg.
     */
    public Double getAvgPerMonth() {
        return avgPerMonth;
    }

    /**
     * @param permonth setter.
     */
    public void setAvgPerMonth(Double permonth) {
        this.avgPerMonth = permonth;
    }

    @Override
    public String toString() {
        return "TruckDetail{"
                + "truckId=" + truckId
                + ", truckCode='" + truckCode + '\''
                + ", purchasedDate=" + purchasedDate
                + ", descriptions='" + descriptions + '\''
                + ", orderList=" + orderList
                + ", avgPerMonth=" + avgPerMonth
                + '}';
    }


}
