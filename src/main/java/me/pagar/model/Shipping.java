package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shipping extends PagarMeModel<Integer>  {

    @Expose
    private String name;

    @Expose
    private int fee;

    @Expose
    @SerializedName("delivery_date")
    private String deliveryDate;

    @Expose
    private Boolean expedited;

    @Expose
    private Address address;

    public Shipping() {
        super();
    }

    public Shipping(final String name, final int fee, final String deliveryDate, final Boolean expedited, final Address address){
        this();
        this.name = name;
        this.fee = fee;
        this.deliveryDate = deliveryDate;
        this.expedited = expedited;
        this.address = address;
    }

    public String getName(){
        return name;
    }

    public int getFee(){
        return fee;
    }

    public String getDeliveryDate(){
        return deliveryDate;
    }

    public Boolean getExpedited(){
        return expedited;
    }

    public Address getAddress(){
        return address;
    }

    public void setName(final String name){
        this.name = name;
    }

    public void setFee(final int fee){
        this.fee = fee;
    }

    public void setDeliveryDate(final String deliveryDate){
        this.deliveryDate = deliveryDate;
    }

    public void setExpedited(Boolean expedited){
        this.expedited = expedited;
    }

    public void setAddress(Address address){
        this.address = address;
    }
}
