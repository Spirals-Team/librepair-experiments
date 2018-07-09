
package coaching.arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example code of arrays of native/primitive types.
 **/
public class NativeTypesArray {

    private static final Logger LOG = LoggerFactory.getLogger(NativeTypesArray.class);

    private static final int ARRAY_SIZE = 10;

    /**
     * iterate the elements of an array for display.
     */
    public void iterateArray() {

        long[] values;
        values = new long[ARRAY_SIZE];

        values[0] = 0L;

        for (int i = 1; i < ARRAY_SIZE; i++) {
            // assign a value to an element of array
            values[i] = i ^ i;
            LOG.info("{} = {}", i, values[i]);
        }
    }

    /**
     * Display matrix, two dimensional array by nested iteration.
     */
    public void displayMatrix() {

        long[][] matrix;
        matrix = new long[ARRAY_SIZE][ARRAY_SIZE];

        LOG.info(looping(matrix));

    }

    /**
     * Display.
     */
    public void display() {
        final long[][] vector = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } };
        LOG.info(looping(vector));
    }

    /**
     * Looping.
     *
     * @param vector
     *            the vector
     * @return the string
     */
    private String looping(final long[][] vector) {
        final StringBuilder stringBuffer = new StringBuilder();
        for (int firstIndex = 0; firstIndex < vector.length; firstIndex++) {
            for (int secondIndex = 0; secondIndex < vector[firstIndex].length; secondIndex++) {
                vector[firstIndex][secondIndex] = firstIndex * secondIndex;
                stringBuffer.append(vector[firstIndex][secondIndex]);
                stringBuffer.append(',');
            }
            stringBuffer.append('\n');
        }
        return stringBuffer.toString();
    }
}
