package me.pagarme;

import me.pagar.model.Address;
import me.pagar.model.Customer;
import me.pagar.model.PagarMeException;
import me.pagar.model.Phone;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest extends BaseTest {

    @Before
    public void setUp() {
        super.setUp();
        customer = new Customer();
    }
    
    @Test
    public void testDatesExistence() throws PagarMeException{
        Customer customer = this.customerCreateCommon();
        customer.save();
        Assert.assertNotNull(customer.getCreatedAt());
    }

    @Test
    public void testCreateCustomer() {

        Customer customer = this.customerCreateCommon();

        try {
            customer.save();

            Address customerAddress = customer.getAddress();
            Phone customerPhone = customer.getPhone();

            this.assertCustomer(customer, customerAddress, customerPhone);

        } catch (PagarMeException ex) {
            throw new UnsupportedOperationException(ex);
        }

    }

    @Test
    public void testCustomerFind() {

        Customer customer = this.customerCreateCommon();

        try {

            customer.save();

            Integer customerId = customer.getId();
            customer.find(customerId);

            Address customerAddress = customer.getAddress();
            Phone customerPhone = customer.getPhone();

           this.assertCustomer(customer, customerAddress, customerPhone);

        } catch (PagarMeException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }
}