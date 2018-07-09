
package patterns.builder;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * class DirectorTest.
 */
public class DirectorTest {

    /**
     * Unit Test to director.
     */
    @Test
    public void testDirector() {
        final Director director = new Director();

        final BuilderOne builderOne = new BuilderOne();
        assertNotNull(builderOne);
        director.add(builderOne);

        final BuilderTwo builderTwo = new BuilderTwo();
        assertNotNull(builderTwo);
        director.add(builderTwo);

        final Product product = director.constructProduct();
        assertNotNull(product);

    }

}
