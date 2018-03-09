package me.pagarme.factory;

import me.pagar.model.Shipping;

public class ShippingFactory {
    public static final String DEFAULT_NAME = "Qwe qwe qwe";
    public static final int DEFAULT_FEE = 100;
    public static final String DEFAULT_DELIVERY_DATE = "2020-12-31";
    public static final Boolean DEFAULT_EXPEDITED = true;

    private AddressFactory addressFactory = new AddressFactory();

    public Shipping create() {
        Shipping shipping = new Shipping();
        shipping.setName(DEFAULT_NAME);
        shipping.setFee(DEFAULT_FEE);
        shipping.setDeliveryDate(DEFAULT_DELIVERY_DATE);
        shipping.setExpedited(DEFAULT_EXPEDITED);
        shipping.setAddress(addressFactory.create());

        return shipping;
    }
}
