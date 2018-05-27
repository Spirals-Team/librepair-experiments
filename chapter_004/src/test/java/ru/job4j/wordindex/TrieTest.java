package ru.job4j.wordindex;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TrieTest {
    @Test
    public void whenCreateNewTrieAndAddValues() {
        Trie trie = new Trie();
        assertNotNull(trie);
        assertThat(trie.getSizeWord(), is(0));
        assertThat(trie.getSizeNodes(), is(0));
        byte[] test = new String("test").getBytes();
        trie.addNew(test[0]);
        assertThat(trie.getSizeWord(), is(0));
        assertThat(trie.getSizeNodes(), is(1));
        trie.add(test[1]);
        assertThat(trie.getSizeWord(), is(0));
        assertThat(trie.getSizeNodes(), is(2));
        trie.addEnd(0);
        assertThat(trie.getSizeWord(), is(1));
        assertThat(trie.getSizeNodes(), is(2));
        trie.addNew(test[0]);
        assertThat(trie.getSizeWord(), is(1));
        assertThat(trie.getSizeNodes(), is(2));
        trie.add(test[1]);
        assertThat(trie.getSizeWord(), is(1));
        assertThat(trie.getSizeNodes(), is(2));
        trie.add(test[2]);
        assertThat(trie.getSizeWord(), is(1));
        assertThat(trie.getSizeNodes(), is(3));
        trie.add(test[3]);
        assertThat(trie.getSizeWord(), is(1));
        assertThat(trie.getSizeNodes(), is(4));
        trie.addEnd(0);
        assertThat(trie.getSizeWord(), is(2));
        assertThat(trie.getSizeNodes(), is(4));
    }
}