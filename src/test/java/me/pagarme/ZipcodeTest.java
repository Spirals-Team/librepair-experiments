package me.pagarme;

import junit.framework.Assert;
import me.pagar.model.Card;
import me.pagar.model.PagarMe;
import me.pagar.model.PagarMeException;
import me.pagar.model.Zipcode;
import me.pagarme.factory.ZipcodeFactory;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author jonatasmaxi
 */
public class ZipcodeTest extends BaseTest {

    public final ZipcodeFactory zipcodeFactory = new ZipcodeFactory();

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testZipcode() throws Throwable {
        Zipcode zipcode = zipcodeFactory.create();
        Assert.assertEquals("São Paulo", zipcode.getCity());
        Assert.assertEquals("SP", zipcode.getState());
        Assert.assertEquals("Vila Marte", zipcode.getNeighborhood());
        Assert.assertEquals("Rua Budapeste", zipcode.getStreet());
    }
}
