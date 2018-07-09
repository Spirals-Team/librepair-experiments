
package coaching.arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example code of arrays of objects.
 **/
public class ObjectTypesArray {

    private static final Logger LOG = LoggerFactory.getLogger(ObjectTypesArray.class);

    /** The Constant ARRAY_SIZE. */
    private static final int ARRAY_SIZE = 10;

    /**
     * iterate the elements of an array for display.
     */
    public void iterateArray() {
        Long[] values;
        values = new Long[ARRAY_SIZE];
        values[0] = 0L;

        for (int i = 1; i < ARRAY_SIZE; i++) {
            // assign a value to an element of array
            values[i] = (long) (i ^ i);
            LOG.info("{}){}", i, values[i]);
        }
    }

    /**
     * Display matrix, two dimensional array by nested iteration.
     */
    public void displayMatrix() {
        Long[][] matrix;
        matrix = new Long[ARRAY_SIZE][ARRAY_SIZE];
        LOG.info(looping(matrix));

    }

    /**
     * Display.
     */
    public void display() {
        final Long[][] vector = { { 0L, 1L, 2L }, { 3L, 4L, 5L }, { 6L, 7L, 8L } };
        LOG.info(looping(vector));
    }

    /**
     * Looping.
     *
     * @param vector
     *            the vector
     * @return the string
     */
    private String looping(final Long[][] vector) {
        final StringBuilder stringBuffer = new StringBuilder();
        for (int firstIndex = 0; firstIndex < vector.length; firstIndex++) {
            for (int secondIndex = 0; secondIndex < vector[firstIndex].length; secondIndex++) {
                vector[firstIndex][secondIndex] = (long) (firstIndex * secondIndex);
                stringBuffer.append(vector[firstIndex][secondIndex]);
                stringBuffer.append(',');
            }
            stringBuffer.append('\n');
        }
        return stringBuffer.toString();
    }
}
