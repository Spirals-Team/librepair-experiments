package pl.sternik.ss.zadania.zad7;


import java.io.InputStream;
import java.util.Scanner;

class MyScanner {

    Scanner scanner;
    int ostatniInt;

    public MyScanner(InputStream in) {
        scanner = new Scanner(in);
    }

    public MyScanner(Scanner input) {
        scanner = input;
    }

    public int nextInt() {
        ostatniInt = scanner.nextInt();
        return ostatniInt;
    }

    public void close() {
        scanner.close();
    }

}