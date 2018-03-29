package ru.job4j.loop;

public class Counter {
    public int add(int start, int finish) {
        int result = 0;
        for (int i = start; i < finish + 1; i++) {
            if (i % 2 == 0)
                result += i;
        }
        return result;
    }
}
