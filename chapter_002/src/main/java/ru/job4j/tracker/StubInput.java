package ru.job4j.tracker;
/**
 * Stub input.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class StubInput implements Input {
    /*** Array of answers.*/
    private String[] answers;
    /*** Position in array.*/
    private int position = 0;
    /**
     * Constructor.
     * @param answers - array of answers
     */
    StubInput(String[] answers) {
        this.answers = answers;
    }
    /**
     * Ask.
     * @param question - question
     * @return answer
     */
    @Override
    public String ask(String question) {
        return this.answers[this.position++];
    }

    @Override
    public int ask(String question, int[] range) {
        return Integer.parseInt(this.ask(question));
    }
}
