package ru.job4j.calculator;

/**
 * двоичная система счисления
 */
public class Binary {
    public static void main(String[] args) {
        Integer res = new Binary().binaty();

    }
    public Integer binaty() {
        int x = 0b100111;
        int y = 0b1011;
        int z = x / y;


        System.out.println(Integer.toBinaryString(x) + "/" + Integer.toBinaryString(y)
                + "=" + Integer.toBinaryString(z));

        return z;
    }
}
