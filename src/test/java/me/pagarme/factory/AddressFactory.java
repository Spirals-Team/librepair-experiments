package me.pagarme.factory;

import me.pagar.model.Address;

public class AddressFactory {

    public static final String DEFAULT_CITY = "Cidade";
    public static final String DEFAULT_COMPLEMENTARY = "Complemento";
    public static final String DEFAULT_COUNTRY = "br";
    public static final String DEFAULT_NEIGHBORHOOD = "bairro";
    public static final String DEFAULT_STATE = "Estado";
    public static final String DEFAULT_STREET = "rua";
    public static final String DEFAULT_STREETNUMBER = "123";
    public static final String DEFAULT_ZIPCODE = "06350270";

    public Address create(){
        Address address = new Address();
        address.setCity(DEFAULT_CITY);
        address.setComplementary(DEFAULT_COMPLEMENTARY);
        address.setCountry(DEFAULT_COUNTRY);
        address.setNeighborhood(DEFAULT_NEIGHBORHOOD);
        address.setState(DEFAULT_STATE);
        address.setStreet(DEFAULT_STREET);
        address.setStreetNumber(DEFAULT_STREETNUMBER);
        address.setZipcode(DEFAULT_ZIPCODE);
        return address;
    }
}
