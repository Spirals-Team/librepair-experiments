package ru.job4j.start;

/**
 * @author Sergey Afonin
 */
public class StartUITest {
    /**
     *
     * @param args args.
     */
    public static void main(String[] args) {
        Input input = new StubInput(new String[] {"create stub task"}); // ConsoleInput();
        new StartUi(input).init();
    }

}
