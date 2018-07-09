
package coaching.tuples;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The TripleTest class.
 */
public class TripleTest {

    /**
     * The Space Class.
     */
    public class Space extends Triple<Double, Double, Double> {

        /**
         * Instantiates a new space.
         */
        public Space() {
            super();
        }

        /**
         * Instantiates a new space.
         *
         * @param x
         *            the x
         * @param y
         *            the y
         * @param z
         *            the z
         */
        public Space(final Double x, final Double y, final Double z) {
            super(x, y, z);
        }
    }

    /**
     * Unit Test to space.
     */
    @Test
    public void testSpace() {
        final Space space = new Space();
        assertNotNull(space);
        final Double x = space.setX(Double.MAX_VALUE).getX();
        assertEquals(Double.MAX_VALUE, x, Double.MIN_VALUE);
        final Double y = space.setY(Double.MAX_VALUE).getY();
        assertEquals(Double.MAX_VALUE, y, Double.MIN_VALUE);
        final Double z = space.setZ(Double.MAX_VALUE).getZ();
        assertEquals(Double.MAX_VALUE, z, Double.MIN_VALUE);
    }

    /**
     * Unit Test to create.
     */
    @Test
    public void testCreate() {
        final Triple<Object, Object, Object> triple = Triple.create();
        assertNotNull(triple);
    }

    /**
     * Unit Test to triple XYZ.
     */
    @Test
    public void testTripleXYZ() {
        final Triple<Double, Double, Double> triple = new Triple<>();
        assertNotNull(triple);
        final Double x = triple.setX(Double.MAX_VALUE).getX();
        assertEquals(Double.MAX_VALUE, x, Double.MIN_VALUE);
        final Double y = triple.setY(Double.MAX_VALUE).getY();
        assertEquals(Double.MAX_VALUE, y, Double.MIN_VALUE);
        final Double z = triple.setZ(Double.MAX_VALUE).getZ();
        assertEquals(Double.MAX_VALUE, z, Double.MIN_VALUE);
    }

}
