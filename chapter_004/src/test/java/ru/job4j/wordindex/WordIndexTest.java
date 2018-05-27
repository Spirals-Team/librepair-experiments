package ru.job4j.wordindex;

import org.junit.Test;

import java.io.File;
import java.util.TreeSet;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class WordIndexTest {
    @Test
    public void whenCreateNewTrieNodeAdnAddValues() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test.txt").getFile());
        System.out.println();
        WordIndex wordIndex = new WordIndex();
        wordIndex.loadFile(file.getAbsolutePath());
        assertNull(wordIndex.getIndexes4Word("1"));
        assertNotNull(wordIndex.getIndexes4Word("test"));
        TreeSet<Integer> test = new TreeSet<>();
        test.add(0);
        test.add(33);
        test.add(46);
        assertThat(wordIndex.getIndexes4Word("test"), is(test));
        assertNotNull(wordIndex.getIndexes4Word("trt"));
        TreeSet<Integer> trt = new TreeSet<>();
        trt.add(10);
        trt.add(38);
        trt.add(42);
        assertThat(wordIndex.getIndexes4Word("trt"), is(trt));
    }
}