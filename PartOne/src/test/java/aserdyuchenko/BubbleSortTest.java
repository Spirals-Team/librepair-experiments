package aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест для выполнения алгоритма пузырьковой сортировки.
 * Подробное описание алгоритма, расписано в классе BubbleSort.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.08.2016
 */
public class BubbleSortTest {
/**
 * @param NUMBERONE - NUMBERONE.
 */
public static final int NUMBERONE = 5;
/**
 * @param NUMBERTWO - NUMBERTWO.
 */
public static final int NUMBERTWO = 1;
/**
 * @param NUMBERTREE - NUMBERTREE.
 */
public static final int NUMBERTREE = 3;
/**
 * @param NUMBERFOUR - NUMBERFOUR.
 */
public static final int NUMBERFOUR = 2;
/**
 * @param NUMBERFIVE- NUMBERFIVE.
 */
public static final int NUMBERFIVE = 6;
/**
 * @param NUMBERSIX - NUMBERSIX.
 */
public static final int NUMBERSIX = 9;
/**
 * whenUsingBubbleSortAlgorithm().
 */
    @Test
    public void whenUsingBubbleSortAlgorithm() {
        BubbleSort bubble = new BubbleSort();
        int[] result = bubble.bubbleSort(new int[]{NUMBERONE, NUMBERTWO, NUMBERTREE, NUMBERFOUR, NUMBERFIVE, NUMBERSIX});
        assertThat(result, is(new int[]{NUMBERTWO, NUMBERFOUR, NUMBERTREE, NUMBERONE, NUMBERFIVE, NUMBERSIX}));
    }
}