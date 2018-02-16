package ru.job4j.array;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * Merge arrays.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class MergeArraysTest {
    /**
     * Merge two arrays.
     */
    @Test
    public void whenMergeTwoArrays() {
        MergeArrays mergeArrays = new MergeArrays();
        int[] leftArr = {1, 2, 3};
        int[] rightArr = {4, 5, 6};
        int[] resultArr = mergeArrays.merge(leftArr, rightArr);
        int[] expectArr = {1, 2, 3, 4, 5, 6};
        assertThat(resultArr, is(expectArr));
    }
    /**
     * Merge two arrays when first array longer than second.
     */
    @Test
    public void whenFirstArrayLongerThanSecond() {
        MergeArrays mergeArrays = new MergeArrays();
        int[] leftArr = {2, 3, 4};
        int[] rightArr = {1, 7};
        int[] resultArr = mergeArrays.merge(leftArr, rightArr);
        int[] expectArr = {1, 2, 3, 4, 7};
        assertThat(resultArr, is(expectArr));
    }
}
