package pl.kayzone.exchange.model.entity;

import org.junit.Assert;
import org.junit.Test;
import pl.kayzone.exchange.model.helper.TestClassCreator;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomersTest {

    private TestClassCreator testClassCreator = new TestClassCreator();
    //Field id of type ObjectId - was not mocked since Mockito doesn't mock a Final class when 'mock-maker-inline' option is not set
    Customers customers = new Customers();

    @Test
    public void testEquals() throws Exception {
        boolean result = customers.equals(new Customers());
        Assert.assertEquals(true, result);
    }

    @Test
    public void testHashCode() throws Exception {
        int result = customers.hashCode();
        Assert.assertEquals(customers.hashCode(), result);
    }

    @Test
    public void testToString() throws Exception {

        String result = testClassCreator.getCustomers().toString();
        Assert.assertEquals("Customers{name='Kowalski', firstName='Jan', surname='Kowalski'," +
                " address='ul.Blotna 22', zip='43-334', city='Wrzeszcz', country='Poland', nip='883-220-90-33'}", result);
    }
    @Test
    public void testLongVersion() {
        Customers cust = testClassCreator.getCustomers();

        assertThat(cust.getVersion()).isNotNull();
        assertThat(cust.getVersion()).isGreaterThan(0);
    }
}
