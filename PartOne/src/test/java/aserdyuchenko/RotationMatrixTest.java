package aserdyuchenko;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест для поворота матрицы на 90 градусов.
 * Подробное описание алгоритма, расписано в классе RotationMatrix.
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 28.08.2016
 */
public class RotationMatrixTest {
/**
 * @param NUMBERTHREE - NUMBERTHREE.
 */
public static final int NUMBERTHREE = 3;
/**
 * @param NUMBERFOUR - NUMBERFOUR.
 */
public static final int NUMBERFOUR = 4;
/**
 * @param NUMBERFIVE - NUMBERFIVE.
 */
public static final int NUMBERFIVE = 5;
/**
 * @param NUMBERSIX - NUMBERSIX.
 */
public static final int NUMBERSIX = 6;
/**
 * @param NUMBERSEVEN- NUMBERSEVEN.
 */
public static final int NUMBERSEVEN = 7;
/**
 * @param NUMBEREIGHT - NUMBEREIGHT.
 */
public static final int NUMBEREIGHT = 8;
/**
 * @param NUMBERNINE - NUMBERNINE.
 */
public static final int NUMBERNINE = 9;
/**
 * whenMatrixTurnNinetyDegrees().
 */
    @Test
    public void whenMatrixTurnNinetyDegrees() {
        RotationMatrix rotation = new RotationMatrix();
        int[][] result = rotation.rotationMatrix(new int[][]{{1, 2, NUMBERTHREE}, {NUMBERFOUR, NUMBERFIVE, NUMBERSIX}, {NUMBERSEVEN, NUMBEREIGHT, NUMBERNINE}});
        assertThat(result, is(new int[][]{{NUMBERTHREE, NUMBERSIX, NUMBERNINE}, {2, NUMBERFIVE, NUMBEREIGHT}, {1, NUMBERFOUR, NUMBERSEVEN}}));
    }
}