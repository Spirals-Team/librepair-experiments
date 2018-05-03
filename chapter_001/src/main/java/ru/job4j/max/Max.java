package ru.job4j.max;

/**
  *@autor Alexandr Kaleganov (alexmur07@mail.ru)
 *@version Max 1.000
 *@since 28.01.2018
 */

public class Max {

    public int max(int first, int seconds) {
        return first > seconds ? first : seconds;
    }
    public int max3(int first, int seconds, int third) {
        return max(max(first, seconds), third);
    }
}
