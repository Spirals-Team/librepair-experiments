package aserdyuchenko;

/**
 * 4.2. Создать программу вычисляющую факториал.
 * Написать программу для вычисления факториала. Использовать циклы.
 * Факториал числа n - это произведение чисел от 1 до n.
 *
 * @author Anton Serdyuchenko (anton415@gmail.com)
 * @version $Id$
 * @since 08.09.2016
 */
public class Factorial {
/**
 * @param fac - fac.
 */
    private float result = 1;
/**
 * Преобразование оригинальной строки в массив.
 * @param fac - fac.
 * @return result - result.
 */
    public float calculation(float fac) {
        for (float index = 1; index <= fac; index++) {
            result *= index;
        }
        return result;
    }
}
