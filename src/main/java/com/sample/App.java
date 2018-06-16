package com.sample;

public class App {

    public static void main(String[] args) {
        App app = new App();
        int sum = app.sum(2, 5);
        System.out.println("2 + 5 = " + sum);
    }

    public int sum(int a, int b) {
        return a + b;
    }

}