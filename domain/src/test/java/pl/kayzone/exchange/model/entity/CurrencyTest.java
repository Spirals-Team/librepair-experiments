package pl.kayzone.exchange.model.entity;

import org.junit.Assert;
import org.junit.Test;

public class CurrencyTest {
    Currency currency = new Currency("idCode", "name", "urlNbp", "tablesType", Double.valueOf(0));

    @Test
    public void testEquals() throws Exception {
        boolean result = currency.equals(null);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = currency.hashCode();
        Assert.assertEquals(currency.hashCode(), result);
    }

    @Test
    public void testToString() throws Exception {
        String result = currency.toString();
        Assert.assertEquals("Currency{idCode='idCode', name='name', urlNbp='urlNbp', tablesType='tablesType', rates=0.0}", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme