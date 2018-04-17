package ru.job4j.tracker;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class StubInput implements Input {

    private final String[] value;

    private int position;

    public StubInput(final String[] value) {
        this.value = value;
    }

    @Override
    public String ask(String question) {
        return this.value[this.position++];
    }

    @Override
    public int ask(String question, int[] range) {
        return -1;
    }
}
