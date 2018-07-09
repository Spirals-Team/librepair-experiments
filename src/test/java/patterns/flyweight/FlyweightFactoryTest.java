
package patterns.flyweight;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * The FlyweightFactoryTest class.
 */
public class FlyweightFactoryTest {

    /**
     * Unit Test to create.
     */
    @Test
    public void testCreate() {
        final FlyweightFactory flyweightFactory = new FlyweightFactory();
        assertNotNull(flyweightFactory);
        final FlyWeight flyweight = flyweightFactory.create();
        assertNotNull(flyweight);
    }

}
