package ru.job4j.tracker;

public class StubInput implements Input {
    private String[] answers;
    private int position = 0;
    private int index = 0;

    public StubInput(String[] answers) {
        this.answers = answers;
    }
    @Override
    public String ask(String question) {
        return answers[position++];
    }
    @Override
    public int ask(String question, int[] range) {
        return Integer.parseInt(answers[position++]);
    }
}
