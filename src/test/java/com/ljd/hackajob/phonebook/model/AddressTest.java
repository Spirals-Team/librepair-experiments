package com.ljd.hackajob.phonebook.model;

import static com.ljd.hackajob.phonebook.util.TestUtils.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AddressTest extends ModelTest {

    private String type;
    private String street;
    private String city;
    private String county;
    private String postcode;
    
    private Address address;
    
    @Before
    public void beforeEach() {
        type = generateRandomAlphanumericString(8);
        street = generateRandomAlphanumericString(12);
        city = generateRandomAlphanumericString(6);
        county = generateRandomAlphanumericString(10);
        postcode = generateRandomAlphanumericString(6);
        
        address = new Address.AddressBuilder(type)
                .withStreet(street)
                .withCity(city)
                .withCounty(county)
                .withPostcode(postcode)
                .build();
    }
    
    @Test
    public void testGetType() {
        assertEquals(type, address.getType());
    }       
    
    @Test
    public void testGetStreet() {
        assertEquals(street, address.getStreet());
    }  
    
    @Test
    public void testGetCity() {
        assertEquals(city, address.getCity());
    }    
    
    @Test
    public void testGetCounty() {
        assertEquals(county, address.getCounty());
    }
    
    @Test
    public void testGetPostcode() {
        assertEquals(postcode, address.getPostcode());
    }
    
    @Test
    public void testSerialization() throws Exception {
        String json = mapper.writeValueAsString(address);
        assertTrue(json.contains(type));
        assertTrue(json.contains(street));
        assertTrue(json.contains(city));
        assertTrue(json.contains(county));
        assertTrue(json.contains(postcode));
        System.out.println(json);
    }
    
    @Test
    public void testDeserialization() throws Exception {        
        String json = String.format("{\"type\":\"%s\",\"street\":\"%s\",\"city\":\"%s\",\"county\":\"%s\",\"postcode\":\"%s\"}", type, street, city, county, postcode);
        Address deserialized = mapper.readValue(json, Address.class);
        assertEquals(type, deserialized.getType());
        assertEquals(street, deserialized.getStreet());
        assertEquals(city, deserialized.getCity());
        assertEquals(county, deserialized.getCounty());
        assertEquals(postcode, deserialized.getPostcode());
    }
}
