package me.pagarme.factory;

import me.pagar.model.Phone;

public class PhoneFactory {

    public static final String DEFAULT_DDD = "11";
    public static final String DEFAULT_NUMBER = "87654321";
    public Phone create(){
        Phone phone = new Phone(DEFAULT_DDD, DEFAULT_NUMBER);
        return phone;
    }
}
