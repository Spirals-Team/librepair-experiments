package net.coljate.map.impl;

/**
 *
 * @author ollie
 */
public class CommonsTrie<V> extends WrappedMap<String, V> {

    final org.apache.commons.collections4.Trie<String, V> trie;

    public CommonsTrie(final org.apache.commons.collections4.Trie<String, V> trie) {
        super(trie);
        this.trie = trie;
    }

}
