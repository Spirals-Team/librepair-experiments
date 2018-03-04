package ru.job4j.array;

/**
 * String contains a substring.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class StringContains {
    /**
     * String contains a substring.
     * @param origin - origin string
     * @param sub - substring
     * @return true if string contains a substring or false
     */
    public boolean contains(String origin, String sub) {
        char[]originArray = origin.toCharArray();
        char[]subArray = sub.toCharArray();
        int index = 0;
        for (char letter : originArray) {
            index = (letter == subArray[index]) ? index + 1 : 0;
            if (index == subArray.length) {
                return true;
            }
        }
        return false;
    }
}
