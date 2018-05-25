package me.pagar.model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.HttpMethod;
import me.pagar.util.JSONUtils;

/**
 *
 * @author ta-ma
 */
public class Zipcode extends PagarMeModel<String> {

    @Expose
    private String zipcode;
    @Expose
    private String street;
    @Expose
    private String neighborhood;
    @Expose
    private String city;
    @Expose
    private String state;

    public Zipcode() {
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Zipcode checkZipcode(String zipcode) throws PagarMeException {
        final PagarMeRequest request = new PagarMeRequest(HttpMethod.GET, "/zipcodes/" + zipcode);

        final Zipcode other = JSONUtils.getAsObject((JsonObject) request.execute(), Zipcode.class);
        copy(other);
        flush();
        return other;

    }

    private void copy(Zipcode other) {
        super.copy(other);
        this.city = other.city;
        this.neighborhood = other.neighborhood;
        this.street = other.street;
        this.zipcode = other.zipcode;
        this.state = other.state;
    }

}
