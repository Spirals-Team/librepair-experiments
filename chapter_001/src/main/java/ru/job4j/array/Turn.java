package ru.job4j.array;
/**
 * Turn.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class Turn {
    /**
     * Метот разворачивает массив.
     * @param array - входящий массив.
     * @return - развернутый массив.
     */
    public int[] back(int[] array) {
        int[] b = array;
        if (b.length % 2 == 0) {
            for (int i = 0; i < b.length / 2; i++) {
                int temp = b[i];
                b [i] = b[b.length - i - 1];
                b [b.length - i - 1] = temp;
            }
        } else {
            for (int i = 0; i < (b.length - 1) / 2; i++) {
                int temp = b[i];
                b [i] = b[b.length - i - 1];
                b [b.length - i - 1] = temp;
            }
        }
        return b;
    }
}
