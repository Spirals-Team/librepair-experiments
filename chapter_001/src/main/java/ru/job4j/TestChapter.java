package ru.job4j;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 01.09.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class TestChapter {
    /**
     * Метод проверяет содержится ли в слове origin слово sub.
     * @param origin  - слово в котором производим поиск.
     * @param sub - исомое слово.
     * @return - true or false;
     */
    public boolean contains(String origin, String sub) {
        boolean con = false;
        char[] originChar = origin.toLowerCase().toCharArray();
        char[] subChar = sub.toLowerCase().toCharArray();
        for (int i = 0; i < originChar.length; i++) {
           int iI = i;
           int count = 0;
           int j = 0;
            while (j < subChar.length && originChar[i] == subChar[j]) {
                i++;
                count++;
                j++;
            }
            if (count == subChar.length) {
                con = true;
            } else {
                i = iI;
            }
        }
        return con;
    }
}
