package ru.job4j.calculator;

public class Chetnii {

    public static void main(String[] args) {
        for (int i = 1000; i > 0; i = i - 2) {
            System.out.print(i + "  ");
            if (i % 9 == 0) {
                System.out.println("");
            }
        }
    }
}
