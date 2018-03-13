package ru.job4j.start;

/**
 *
 */
public class StubInput implements Input {

    /**
     *
     */
    private String[] answers;
    /**
     *
     */
    private int position = 0;

    /**
     *
     * @param answers answ.
     */
    public StubInput(String[] answers) {
        this.answers = answers;
    }

    /**
     *
     * @param question String question
     * @return answers.
     */
    @Override
    public String ask(String question) {
        return answers[position++];
    }

    @Override
    public int ask(String question, int[] range) {
        return -1;
    }
}
