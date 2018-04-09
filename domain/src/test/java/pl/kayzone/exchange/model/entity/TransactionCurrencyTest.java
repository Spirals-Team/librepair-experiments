package pl.kayzone.exchange.model.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class TransactionCurrencyTest {
    @Mock
    CurrencyCourse currencyCourse;
    @Mock
    BigDecimal course;
    @Mock
    BigDecimal quantity;
    @InjectMocks
    TransactionCurrency transactionCurrency;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = transactionCurrency.equals(null);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = transactionCurrency.hashCode();
        Assert.assertEquals(956253842, result);
    }
}
