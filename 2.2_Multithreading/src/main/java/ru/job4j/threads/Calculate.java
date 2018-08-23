package ru.job4j.threads;
import java.io.IOException;

/**
 * Class Calculate.
 */
public class Calculate {

    /**
     * Method calc.
     *
     * @param string string for parse
     * @throws IOException exception
     */
    public void calc(String string) throws IOException {
        System.out.println("Программа для подсчета слов и пробелов в выражении:");
        Thread space = new Thread(new SpaceCount(string));
        Thread word = new Thread(new WordCount(string));
        space.start();
        word.start();
        try {
            word.join();
            space.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Программа завершила свою работу");
    }

    /**
     * method main.
     * @param args args
     * @throws IOException exception
     */
    public static void main(String[] args) throws IOException {
        Calculate calculate = new Calculate();
        String s = "Закрыло пеленой чреду воспоминаний";
        calculate.calc(s);
    }
}
