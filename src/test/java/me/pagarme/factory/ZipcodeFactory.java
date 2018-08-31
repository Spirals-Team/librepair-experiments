package me.pagarme.factory;

import me.pagar.model.Zipcode;

/**
 *
 * @author jonatasmaxi
 */
public class ZipcodeFactory {

    public Zipcode create() throws Throwable {
        Zipcode zipcode = new Zipcode();
        zipcode.checkZipcode("04250000");
        return zipcode;
    }
}
