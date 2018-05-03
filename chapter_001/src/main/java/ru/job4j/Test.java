package ru.job4j;


public class Test {
    public static void main(String[] args) {
        Test.Expected.value();
    }

    static class Expected {
        static int l;

        public static void value() {
            System.out.println("локальый класс может содержать статические поля и методы");
        }
    }
}