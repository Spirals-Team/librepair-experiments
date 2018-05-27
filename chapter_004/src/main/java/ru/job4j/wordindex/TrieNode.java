package ru.job4j.wordindex;

import java.util.List;
import java.util.Set;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TrieNode {
    private Byte value;
    private List<TrieNode> children;
    private boolean isWord;
    private Set<Integer> setIndex;

    public TrieNode() {
    }

    public TrieNode(Byte value, List<TrieNode> children, boolean isWord, Set<Integer> setIndex) {
        this.value = value;
        this.children = children;
        this.isWord = isWord;
        this.setIndex = setIndex;
    }

    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    public List<TrieNode> getChildren() {
        return children;
    }

    public void setChildren(List<TrieNode> children) {
        this.children = children;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }

    public Set<Integer> getSetIndex() {
        return setIndex;
    }

    public void setSetIndex(Set<Integer> setIndex) {
        this.setIndex = setIndex;
    }
}
