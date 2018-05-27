package ru.iac.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CompareIntInText {
    public static int compareString(String stOne, String stTwo) {
        if (stOne.equalsIgnoreCase(stTwo)) {
            return 0;
        } else {
            return compareArraysChars(stOne.toLowerCase().toCharArray(), stTwo.toLowerCase().toCharArray());
        }
    }

    public static int compareArraysChars(char[] charsOne, char[] charsTwo) {
        List<Object> wordOne = getSymbols(charsOne);
        List<Object> wordTwo = getSymbols(charsTwo);
        int compr = 0;
        for (int i = 0; i < wordOne.size(); i++) {
            if (wordTwo.size() <= i) {
                break;
            }
            Object objOne = wordOne.get(i);
            Object objTwo = wordTwo.get(i);
            if (objOne instanceof Character) {
                if (objTwo instanceof Character) {
                    compr = Character.compare((char) objOne, (char) objTwo);
                    if (compr != 0) {
                        return compr;
                    }
                } else if ((char) objOne == '.') {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (objTwo instanceof Integer) {
                    compr = Integer.compare((int) objOne, (int) objTwo);
                    if (compr != 0) {
                        return compr;
                    }
                } else if ((char) objTwo == '.') {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
        if (wordOne.size() == wordTwo.size()) {
            return 0;
        } else if (wordOne.size() < wordTwo.size()) {
            return -1;
        } else {
            return 1;
        }
    }

    public static List<Object> getSymbols(char[] chars) {
        List<Object> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean flagStart = false;
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if (isNum(ch)) {
                if (!flagStart) {
                    flagStart = true;
                }
                sb.append(ch);
            } else {
                if (flagStart) {
                    list.add(Integer.parseInt(sb.toString()));
                    sb.setLength(0);
                    flagStart = false;
                }
                list.add(ch);
            }
        }
        if (flagStart) {
            list.add(Integer.parseInt(sb.toString()));
        }
        return list;
    }


    public static boolean isNum(char ch) {
        return ch >= 48 && ch <= 58;
    }
}
