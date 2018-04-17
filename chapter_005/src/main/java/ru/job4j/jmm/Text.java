package ru.job4j.jmm;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Text {
    public void print(String text) {
        System.out.format("[%s", text);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Прерван");
        }
        System.out.println("]");
    }
}
