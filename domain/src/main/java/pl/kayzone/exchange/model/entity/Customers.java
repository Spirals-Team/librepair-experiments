package pl.kayzone.exchange.model.entity;

import org.mongodb.morphia.annotations.Entity;

import java.io.Serializable;
import java.util.Objects;

@Entity(value = "customers")
public class Customers extends BaseEntity implements Serializable {


    private String name;
    private String firstName;
    private String surname;
    private String address;
    private String zip;
    private String city;
    private String country;
    private String nip;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customers)) return false;
        Customers customers = (Customers) o;
        return Objects.equals(getId(), customers.getId()) &&
                Objects.equals(getName(), customers.getName()) &&
                Objects.equals(getFirstName(), customers.getFirstName()) &&
                Objects.equals(getSurname(), customers.getSurname()) &&
                Objects.equals(getAddress(), customers.getAddress()) &&
                Objects.equals(getZip(), customers.getZip()) &&
                Objects.equals(getCity(), customers.getCity()) &&
                Objects.equals(getCountry(), customers.getCountry()) &&
                Objects.equals(getNip(), customers.getNip());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getFirstName(), getSurname(), getAddress(), getZip(), getCity(), getCountry(), getNip());
    }

    @Override
    public String toString() {
        return "Customers{" +
                //"id=" + super.getId().toString() +
                "name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", nip='" + nip + '\'' +
                '}';
    }

}
