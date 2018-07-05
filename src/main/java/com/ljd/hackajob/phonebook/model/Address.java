package com.ljd.hackajob.phonebook.model;

import javax.validation.constraints.NotNull;

/**
 *
 * @author leodavison
 *
 */
public class Address {
    @NotNull
    private final String type;
    private String street;
    private String city;
    private String county;
    private String postcode;
    
    private Address() {
        // required for deserialisation
        type = null; //NOSONAR
    }

    private Address(AddressBuilder builder) {
        this.type = builder.type;
        this.street = builder.street;
        this.city = builder.city;
        this.county = builder.county;
        this.postcode = builder.postcode;
    }
    
    public String getType() {
        return type;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCounty() {
        return county;
    }

    public String getPostcode() {
        return postcode;
    }

    public static class AddressBuilder {
        private String type;
        private String street;
        private String city;
        private String county;
        private String postcode;

        public AddressBuilder(String type) {
            this.type = type;
        }

        public AddressBuilder withStreet(String street) {
            this.street = street;
            return this;
        }

        public AddressBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public AddressBuilder withCounty(String county) {
            this.county = county;
            return this;
        }

        public AddressBuilder withPostcode(String postcode) {
            this.postcode = postcode;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}
