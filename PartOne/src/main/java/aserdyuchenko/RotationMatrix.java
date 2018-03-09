package aserdyuchenko;

/**
 * 5.2. Создание программы поворота квадратного массива.
 * Дано. Двухмерный квадратный массив int values[][] - нужно написать метод, который будет поворачивать данный массив на 90 градусов.
 * Поворот квадратного массива происходит против часовой стрелки на 90 градусов.
 *
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 11.09.2016
 */
public class RotationMatrix {
    /**
     * rotationMatrix(int[][] values) .
     * @param values - values.
     * @return values - values.
     */
    int[][] rotationMatrix(int[][] values) {
        for (int externalIndex = 0; externalIndex < values.length / 2; externalIndex++) {
            for (int internalIndex = externalIndex; internalIndex < values.length - 1 - externalIndex; internalIndex++) {
                int t = values[externalIndex][internalIndex];
                values[externalIndex][internalIndex] = values[internalIndex][values.length - 1 - externalIndex];
                values[internalIndex][values.length - 1 - externalIndex] = values[values.length - 1 - externalIndex][values.length - 1 - internalIndex];
                values[values.length - 1 - externalIndex][values.length - 1 - internalIndex] = values[values.length - 1 - internalIndex][externalIndex];
                values[values.length - 1 - internalIndex][externalIndex] = t;
            }
        }
        return values;
    }
}
