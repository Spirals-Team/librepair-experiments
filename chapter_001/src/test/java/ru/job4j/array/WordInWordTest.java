package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class WordInWordTest {
    @Test
    public void whenThereIsTheRightWordForWord() {
        WordInWord necessaryWord = new WordInWord();
        String in = "Здравствуйте";
        String out = "дра";
        Boolean result = necessaryWord.contains(in, out);
        assertThat(result, is(true));
    }

    @Test
    public void whenIsNoNecessaryTheRightWordForWord() {
        WordInWord word = new WordInWord();
        String input = "Здравствуйте";
        String output = "ствуйтем";
        Boolean result = word.contains(input, output);
        assertThat(result, is(false));
    }
}
