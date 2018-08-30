package ru.job4j.dao;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.DbConnector;
import ru.job4j.model.Address;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AddressDaoImplTest {

    private Address defaultAddress;
    private AddressDaoImpl addressDao;

    @Before
    public void setUp() {
        DbConnector connector = DbConnector.getInstance();
        connector.cleanDB();
        connector.initDB();
        defaultAddress = new Address(1L, "Belgium", "Ghent", "Jakobijnenstraat 6");
        addressDao = new AddressDaoImpl();
    }

    @Test
    public void findOne() {
        assertThat(addressDao.findOne(1L), is(defaultAddress));
    }

    @Test
    public void findAll() {
        List<Address> address = new ArrayList<>();
        address.add(defaultAddress);
        List<Address> all = addressDao.findAll();
        assertThat(all, hasSize(equalTo(1)));
        assertThat(all,
            containsInAnyOrder(address.toArray(new Address[0])));
    }

    @Test
    public void create() {
        Address test = new Address("France", "Toulouse", "54 rue Peyrolieres");
        assertThat(addressDao.create(test), is(2L));
        assertThat(addressDao.findAll(), hasSize(equalTo(2)));
        Address newAddress = addressDao.findOne(2L);
        assertThat(newAddress.getCountry(), is(test.getCountry()));
        assertThat(newAddress.getCity(), is(test.getCity()));
        assertThat(newAddress.getStreetAddress(), is(test.getStreetAddress()));
    }

    @Test
    public void update() {
        Address prevAddress = addressDao.findOne(1L);
        boolean result = addressDao.update(new Address(1L, "Test", "Test", "Test"));
        Address currentAddress = addressDao.findOne(1L);
        assertThat(result, is(true));
        assertThat(addressDao.findAll(), hasSize(equalTo(1)));
        assertThat(prevAddress, not(currentAddress));
        assertThat(currentAddress.getCountry(), is("Test"));
        assertThat(currentAddress.getCity(), is("Test"));
        assertThat(currentAddress.getStreetAddress(), is("Test"));
    }

    @Test
    public void delete() {
        boolean result = addressDao.delete(defaultAddress);
        Address address = addressDao.findOne(defaultAddress.getId());
        assertThat(result, is(true));
        assertThat(addressDao.findAll(), is(emptyCollectionOf(Address.class)));
        assertThat(address.isEmpty(), is(true));
    }

    @Test
    public void deleteById() {
        boolean result = addressDao.deleteById(defaultAddress.getId());
        Address address = addressDao.findOne(defaultAddress.getId());
        assertThat(result, is(true));
        assertThat(addressDao.findAll(), is(emptyCollectionOf(Address.class)));
        assertThat(address.isEmpty(), is(true));
    }
}