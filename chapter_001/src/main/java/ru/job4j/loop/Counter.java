package ru.job4j.loop;

public class Counter {
    public int add(int start, int finish) {
        int result = 0;
        for (start = 0;  start <= finish; start++) {
            if (start % 2 == 0) {
                result += start;
            }
        }
        return result;
    }
}
