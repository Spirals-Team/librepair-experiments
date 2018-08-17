package ru.job4j.threads;

/**
 * abstract class WordCheck.
 */
public abstract class WordCheck implements Runnable {
    /**
     * array char
     */
    protected char[] str;

    /**
     * Constrcutor.
     * @param word word for check
     */
    public WordCheck(String word) {
        this.str = word.toCharArray();
    }

    /**
     * Override method run.
     */
    @Override
    public abstract void run();
}
