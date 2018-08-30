package ru.job4j.model;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class Address.
 */
public class Address {

    private Long id;
    private String country;
    private String city;
    private String streetAddress;

    public Address() {
    }

    public Address(final String country, final String city,
                   final String streetAddress) {
        this.country = country;
        this.city = city;
        this.streetAddress = streetAddress;
    }

    public Address(final Long id, final String country, final String city,
                   final String streetAddress) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.streetAddress = streetAddress;
    }

    public Long getId() {
        return id;
    }

    public Address setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(final String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(final String city) {
        this.city = city;
        return this;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Address setStreetAddress(final String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public boolean isEmpty() {
        return this.id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address that = (Address) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.country, that.country)
            && Objects.equals(this.city, that.city)
            && Objects.equals(this.streetAddress, that.streetAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, streetAddress);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("country = " + country)
            .add("city = " + city)
            .add("streetAddress = " + streetAddress)
            .toString();
    }
}
