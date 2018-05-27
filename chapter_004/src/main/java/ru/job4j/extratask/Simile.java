package ru.job4j.extratask;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 *
 * Сравнить строки и вернуть тру если используются одинаковые буквы
 * Привем МАМА и АМАМ должны вернуть тру.
 */
public class Simile {

    public boolean testOne(String stOne, String stTwo) {
        if (stOne.length() != stTwo.length()) {
            return false;
        }
        ArrayList<Character> listTwo = new ArrayList<>();
        for (int i = 0; i < stTwo.length(); i++) {
            listTwo.add(stTwo.charAt(i));
        }
        for (int i = 0; i < stOne.length(); i++) {
            boolean find = false;
            for (int j = 0; j < listTwo.size(); j++) {
                if (stOne.charAt(i) == listTwo.get(j)) {
                    find = true;
                    listTwo.remove(j);
                    break;
                }
            }
            if (!find) {
                return false;
            }
        }
        return true;
    }

    public boolean testTwo(String stOne, String stTwo) {
        if (stOne.length() != stTwo.length()) {
            return false;
        }
        int[] letters = new int[256];
        char[] charsOne = stOne.toCharArray();
        for (char c : charsOne) {
            letters[c]++;
        }
        for (int i = 0; i < stTwo.length(); i++) {
            int c = (int) stTwo.charAt(i);
            if (--letters[c] < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean testThree(String stOne, String stTwo) {
        if (stOne.length() != stTwo.length()) {
            return false;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < stOne.length(); i++) {
            if (map.get((int) stOne.charAt(i)) == null) {
                map.put((int) stOne.charAt(i), (int) stOne.charAt(i));
            } else {
                map.put((int) stOne.charAt(i), map.get((int) stOne.charAt(i)) + (int) stOne.charAt(i));
            }
        }
        for (int i = 0; i < stTwo.length(); i++) {
            if (map.get((int) stTwo.charAt(i)) == null) {
                return false;
            } else {
                map.put((int) stTwo.charAt(i), map.get((int) stTwo.charAt(i)) - (int) stTwo.charAt(i));
                if (map.get((int) stTwo.charAt(i)) < 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
