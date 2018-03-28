package ru.job4j.loop;

public class Factorial {
    int result = 1;

    int calc(int n) {
        if (n == 0)
            return 1;
        if (n >= 0) {
            calc(n - 1);
            result *= n;
        }
        return result;
    }
}
