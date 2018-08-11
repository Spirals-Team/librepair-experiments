package net.coljate.set.impl;

import net.coljate.set.MutableSet;

import org.apache.commons.collections4.Trie;

/**
 *
 * @author ollie
 */
public class MutableCommonsTrieSet
        extends ComonsTrieSet
        implements MutableSet<String> {

    private static final Object VALUE = new Object();

    protected MutableCommonsTrieSet(final Trie<String, Object> trie) {
        super(trie);
    }

    @Override
    public boolean add(final String element) {
        return trie.putIfAbsent(element, VALUE) == null;
    }

    @Override
    public boolean remove(final Object element) {
        return trie.remove(element) != null;
    }

}
