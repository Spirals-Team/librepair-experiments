package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 20.12.17
 * @ version: java_kurs_standart
 */
public class TwoWordsTest {
    /**
     * Проверим слова которые состоят из одинаковых букв.
     * @throws Exception исключение.
     */
    @Test
    public void whenWordsConsistOfIdenticalLettersThenTrue() throws Exception {
        TwoWords twoWords = new TwoWords();
        String word1 = "mama";
        String word2 = "amam";
        Assert.assertTrue(twoWords.isWordsConsistOfIdenticalLetters(word1, word2));
    }
    /**
     * Проверим слова которые не состоят из одинаковых букв.
     * @throws Exception исключение.
     */
    @Test
    public void whenWordsNotConsistOfIdenticalLettersThenFalse() throws Exception {
        TwoWords twoWords = new TwoWords();
        String word1 = "mama";
        String word2 = "ammm";
        Assert.assertFalse(twoWords.isWordsConsistOfIdenticalLetters(word1, word2));
    }
    /**
     * Проверим слова разной длины.
     * @throws Exception исключение.
     */
    @Test
    public void whenWordsNotEqualsLength() throws Exception {
        TwoWords twoWords = new TwoWords();
        String word1 = "mama";
        String word2 = "ammmv";
        Assert.assertFalse(twoWords.isWordsConsistOfIdenticalLetters(word1, word2));
    }

}