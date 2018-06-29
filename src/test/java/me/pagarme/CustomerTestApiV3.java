package me.pagarme;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.model.Customer;
import me.pagarme.factory.CustomerFactory;


public class CustomerTestApiV3 extends BaseTest{
    private final CustomerFactory customerFactory = new CustomerFactory();

    @Before
    public void SetUpV3() {
        super.setUp("2017-08-28");
        customer = new Customer();
    }

    @Test
    public void testCreateCustomer() throws Throwable {
        customer = customerFactory.createApiV3();
        customer.save();
        
        Assert.assertNotNull(customer.getId());
    }
    
    @Test
    public void testCustomerFind() throws Throwable {
        customer = customerFactory.createApiV3();
        customer.save();
        
        Customer foundCustomer = customer.find(customer.getId());
        this.assertCustomer(foundCustomer);
    }
}
