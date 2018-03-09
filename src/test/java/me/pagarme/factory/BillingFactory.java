package me.pagarme.factory;

import me.pagar.model.Billing;

public class BillingFactory {

    public static final String DEFAULT_NAME = "Qwe qwe qwe";

    private AddressFactory addressFactory = new AddressFactory();

    public Billing create() {
        Billing billing = new Billing();
        billing.setName(DEFAULT_NAME);
        billing.setAddress(addressFactory.create());
        
        return billing;
    }
}
