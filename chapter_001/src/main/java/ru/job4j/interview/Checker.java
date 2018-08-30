package ru.job4j.interview;

import java.util.HashMap;

/**
 * Checker, class check symbol occurrences.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class Checker {

    public boolean checkSymbolOccurrences(String first, String second) {
        HashMap<Character, Integer> counter = new HashMap<>();
        boolean result = false;
        if (isLengthEqual(first, second)) {
            for (char symbol : first.toCharArray()) {
                counter.put(symbol,
                    !counter.containsKey(symbol) ? 1 : counter.get(symbol) + 1);
            }
            for (char symbol : second.toCharArray()) {
                if (!counter.containsKey(symbol)) {
                    break;
                }
                decreaseCounter(counter, symbol);
            }
            result = counter.isEmpty();
        }
        return result;
    }

    private boolean isLengthEqual(String first, String second) {
        return first != null && second != null
            && first.length() == second.length();
    }

    private void decreaseCounter(HashMap<Character, Integer> counter, char symbol) {
        if (counter.get(symbol) > 1) {
            counter.put(symbol, counter.get(symbol) - 1);
        } else {
            counter.remove(symbol);
        }
    }
}
