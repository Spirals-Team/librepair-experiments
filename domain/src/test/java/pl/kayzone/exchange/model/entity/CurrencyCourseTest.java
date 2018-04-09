package pl.kayzone.exchange.model.entity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.kayzone.exchange.model.helper.TestClassCreator;

import java.math.BigDecimal;

public class CurrencyCourseTest {
    //Field id of type ObjectId - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    //Field date of type LocalDateTime - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    //Field validTo of type LocalDateTime - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    @Mock
    BigDecimal bid;
    @Mock
    BigDecimal ask;
    @InjectMocks
    CurrencyCourse currencyCourse;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToString() throws Exception {
        String result = (new TestClassCreator()).getCurrency().toString();
        Assert.assertEquals("Currency{idCode='USD', name='dolar amerykański'," +
                " urlNbp='http://api.nbp.pl/api/exchangerates/rates/{table}/{code}/', tablesType='A', rates=1.0}", result);
    }

    @Test
    public void testEquals() throws Exception {
        boolean result = currencyCourse.equals(null);
        Assert.assertEquals(false, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = currencyCourse.hashCode();
        Assert.assertNotEquals(0, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme