package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class GlueArrayTest {

    @Test
    public void testAddFerstArrayAndSeconArrayInThreadArray() {
        GlueArray glueArray = new GlueArray();
        int[] arrOne = {1, 3, 6};
        int[] arrTwo = {1, 2, 7, 9};
        int[] rslArray = glueArray.glueArray(arrOne, arrTwo);
        int[] expectArray = {1, 1, 2, 3, 6, 7, 9};
        assertThat(rslArray, is(expectArray));
    }
}