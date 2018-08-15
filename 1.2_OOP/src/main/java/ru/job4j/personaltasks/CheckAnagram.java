package ru.job4j.personaltasks;

import java.util.LinkedList;
import java.util.List;
/**
 *Class CheckAnagram.
 *@author ifedorenko
 *@since 16.08.2017
 *@version 1
 */
public class CheckAnagram {
    /**
     * Method check.
     * @param original string
     * @param anagram string
     * @return result of check
     */
    public boolean check(String original, String anagram) {
        boolean result = false;
        List<Character> orig = new LinkedList<>();
        for (char c : original.toCharArray()) {
            orig.add(c);
        }

        List<Character> anag = new LinkedList<>();
        for (char c : anagram.toCharArray()) {
            anag.add(c);
        }

        if (orig.size() == anag.size()) {
            for (Character or : orig) {
                for (Character an : anag) {
                    if (or.equals(an)) {
                        anag.remove(an);
                        if (anag.size() == 0) {
                            result = true;
                        }
                        break;
                    }
                }
            }
        }
        return result;
    }
}
