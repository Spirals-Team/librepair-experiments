package net.coljate.map.impl;

import net.coljate.map.Entry;
import net.coljate.map.MutableEntry;
import net.coljate.map.MutableMap;
import net.coljate.util.iterator.CovariantIterator;

import org.apache.commons.collections4.Trie;

/**
 *
 * @author ollie
 */
public class MutableCommonsTrie<V>
        extends CommonsTrie<V>
        implements MutableMap<String, V> {

    protected MutableCommonsTrie(final Trie<String, V> trie) {
        super(trie);
    }

    @Override
    public CovariantIterator<Entry<String, V>, ? extends MutableEntry<String, V>> iterator() {
        return MutableMap.super.iterator();
    }

    @Override
    public MutableEntry<String, V> getEntry(final Object key) {
        return ViewEntry.viewOf(key, this); 
    }

    @Override
    public V put(final String key, final V value) {
        return trie.put(key, value);
    }

    @Override
    public boolean remove(final Object key, final Object value) {
        return trie.remove(key, value);
    }

}
