package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test finding one word in another.
 * @author Afonin Sergey (mailto:afonin1c@mail.ru).
 * @version 1
 * @since 13.10.2017
 */
public class FindWordTest {
    /**
     * Test finding one word in another
     */
    @Test
    public void whenFindsInAnoterWord() {
        //напишите здесь тест, проверяющий сортировку массива из 10 элементов методом пузырька, например {1, 5, 4, 2, 3, 1, 7, 8, 0, 5}.
        String testWord = "Привет";
        String subTestWord = "При";
        FindWord findWord = new FindWord();
        boolean result = findWord.findSubWordInWord(testWord, subTestWord); //FindSubWordInWord(testWord, subTestWord);
        boolean expected = true;
        assertThat(result, is(expected));
    }

}
