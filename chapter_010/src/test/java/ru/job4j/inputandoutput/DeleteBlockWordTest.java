package ru.job4j.inputandoutput;

import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class DeleteBlockWordTest {
    private static final Logger LOG = Logger.getLogger(DeleteBlockWordTest.class);
    DeleteBlockWord deleteBlockWord = new DeleteBlockWord();

    @Test
    public void whenTestGetWordOfBytes() {
        String text = "Test";
        byte[] bytes = text.getBytes();
        List<Integer> list = new ArrayList<>();
        for (byte value: bytes) {
            list.add((int) value);
        }
        String expect = deleteBlockWord.getWord(list);
        assertEquals(text, expect);
    }

    @Test
    public void whenTestMetodSearchWordInBlackList() {
        String[] blackList = {"Tost"};
        assertTrue(deleteBlockWord.isGoodWord("Test", blackList));
        assertFalse(deleteBlockWord.isGoodWord("Tost", blackList));
    }

    @Test
    public void whenGetNewWordOfBytesAndAddInOutputStream() throws IOException {
        String text = "Test";
        byte[] bytes = text.getBytes();
        List<Integer> list = new ArrayList<>();
        for (byte value: bytes) {
            list.add((int) value);
        }
        String[] blackList = {"Tost"};
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        deleteBlockWord.work(list, byteArrayOutputStream, blackList);
        String  expect = byteArrayOutputStream.toString();
        assertThat(expect, is(text));
    }

    @Test
    public void whenTestBlockListInInputAndOutputStream() {
        ByteArrayInputStream testIn = new ByteArrayInputStream(new String("Test tost cust").getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String[] strings = {"tost"};
        deleteBlockWord.dropAbuses(testIn, byteArrayOutputStream, strings);
        assertThat(byteArrayOutputStream.toString(), is("Test  cust"));
    }
}