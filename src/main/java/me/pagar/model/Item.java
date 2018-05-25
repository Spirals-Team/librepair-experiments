package me.pagar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @Expose
    private String id;

    @Expose
    private String title;

    @Expose
    @SerializedName("unit_price")
    private Integer unitPrice;

    @Expose
    private Integer quantity;

    @Expose
    private Boolean tangible;

    public Item() {
        super();
    }

    public Item(final String id, final String title, final int unitPrice, final int quantity, final Boolean tangible) {
        this();
        this.id = id;
        this.title = title;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.tangible = tangible;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Boolean getTangible() {
        return tangible;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTangible(Boolean tangible) {
        this.tangible = tangible;
    }
}
