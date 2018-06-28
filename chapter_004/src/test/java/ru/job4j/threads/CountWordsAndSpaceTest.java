package ru.job4j.threads;

import org.junit.Test;

import static org.junit.Assert.*;

public class CountWordsAndSpaceTest {

    @Test
    public void whenPutStringThenCountWordsAndSpaces() {
        CountWordsAndSpace s = new CountWordsAndSpace("123 123 123   ");
    }
}