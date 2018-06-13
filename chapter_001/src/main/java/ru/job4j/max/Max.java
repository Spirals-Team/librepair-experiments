package ru.job4j.max;

public class Max {
    public int max(int first, int second) {
        return second > first ? second : first;
    }

    public int max(int first, int second, int third) {
        return max(max(first, second), third);
    }
}
