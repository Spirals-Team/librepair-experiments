package ru.job4j.threads;

public class AsyncOperations {

    public static void main(String[] args) throws InterruptedException {
        String str = "This is a string to operate.";
        CountSpaces countSpaces = new CountSpaces(str);
        CountWords countWords = new CountWords(str);
        Print start = new Print("Start to count spaces and words.");
        Print end = new Print("Process finished.");

        Thread spaces = new Thread(countSpaces);
        Thread words = new Thread(countWords);

        start.run();

        spaces.start();
        words.start();
        spaces.join();
        words.join();

        end.run();
    }
}
