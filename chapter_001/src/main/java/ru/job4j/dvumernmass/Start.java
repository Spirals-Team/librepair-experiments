package ru.job4j.dvumernmass;

public class Start {
    public static void main(String[] args) {
        int[][] mass = new int[][]{
                {7, 7, 7, 7, 7, 7, 7},
                {7, 5, 5, 5, 5, 5, 7},
                {7, 5, 3, 3, 3, 5, 7},
                {7, 5, 3, 1, 3, 5, 7},
                {7, 5, 3, 3, 3, 5, 7},
                {7, 5, 5, 5, 5, 5, 7},
                {7, 7, 7, 7, 7, 7, 7}
        };
        int proverka = new MetodReshenia().returnresult(mass);
        System.out.println(" ");
        System.out.println("должно получиться  " + proverka);
    }
}
