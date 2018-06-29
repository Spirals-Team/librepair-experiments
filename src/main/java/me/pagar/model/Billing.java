package me.pagar.model;

import com.google.gson.annotations.Expose;

public class Billing extends PagarMeModel<Integer> {

    @Expose
    private String name;

    @Expose
    private Address address;

    public Billing() {
        super();
    }

    public Billing(final String name, final Address address) {
        this();
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }
}
