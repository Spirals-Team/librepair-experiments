package net.coljate.set.impl;

import java.util.Iterator;

import net.coljate.set.AbstractSet;

import org.apache.commons.collections4.Trie;

/**
 *
 * @author ollie
 */
public class ComonsTrieSet extends AbstractSet<String> {

    final Trie<String, Object> trie;

    protected ComonsTrieSet(final Trie<String, Object> trie) {
        this.trie = trie;
    }

    @Override
    public boolean contains(final Object object) {
        return trie.containsKey(object);
    }

    @Override
    public Iterator<String> iterator() {
        return trie.keySet().iterator();
    }

}
