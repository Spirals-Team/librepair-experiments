package ru.job4j.calculator;

public class Min {

    public static void main(String[] args) {
        Integer res = new Min().min(1, 2, 3, 4);
        System.out.println(res);
    }

    public int min(int first, int seconds) {
        return first < seconds ? first : seconds;
    }
    public int min(int first, int seconds, int third, int fir) {
        return min(min(min(first, seconds), third), fir);
    }
}
