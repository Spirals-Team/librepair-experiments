package ru.job4j.wordindex;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TrieNodeTest {
    @Test
    public void whenCreateNewTrieNodeAdnAddValues() {
        TrieNode trieNode = new TrieNode();
        assertNotNull(trieNode);
        assertNull(trieNode.getValue());
        assertNull(trieNode.getChildren());
        assertNull(trieNode.getSetIndex());
        assertFalse(trieNode.isWord());
        byte expectValue = 1;
        trieNode.setValue(expectValue);
        assertNotNull(trieNode.getValue());
        assertThat(trieNode.getValue(), is(expectValue));
        List<TrieNode> expectList = new ArrayList<>();
        trieNode.setChildren(expectList);
        assertNotNull(trieNode.getChildren());
        assertThat(trieNode.getChildren(), is(expectList));
        Set<Integer> expectSet = new TreeSet<>();
        trieNode.setSetIndex(expectSet);
        assertNotNull(trieNode.getSetIndex());
        assertThat(trieNode.getSetIndex(), is(expectSet));
        boolean expectBoolWord = true;
        trieNode.setWord(expectBoolWord);
        assertTrue(trieNode.isWord());
    }
}