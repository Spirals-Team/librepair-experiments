package ru.job4j.wordindex;

import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Trie {
    private int sizeWord = 0;
    private int sizeNodes = 0;
    private TrieNode head = new TrieNode();
    private TrieNode current;

    public void addNew(byte value) {
        current = head;
        add(value);
    }

    public void add(byte value) {
        List<TrieNode> childrenList;
        if (current.getChildren() == null) {
            childrenList = new ArrayList<>();
            TrieNode newNode = new TrieNode(value, null, false, null);
            childrenList.add(newNode);
            current.setChildren(childrenList);
            current = newNode;
            this.sizeNodes++;
        } else {
            childrenList = current.getChildren();
            TrieNode findNode = find(value, childrenList);
            if (findNode == null) {
                TrieNode newNode = new TrieNode(value, null, false, null);
                childrenList.add(newNode);
                current = newNode;
                this.sizeNodes++;
            } else {
                current = findNode;
            }
        }

    }

    private TrieNode find(byte value, List<TrieNode> list) {
        for (TrieNode trieNode: list) {
            if (trieNode.getValue().equals(value)) {
                return trieNode;
            }
        }
        return null;
    }

    public void addEnd(int index) {
        if (!current.isWord()) {
            current.setWord(true);
        }
        Set<Integer> set = current.getSetIndex();
        if (set == null) {
            set = new TreeSet<>();
        }
        set.add(index);
        current.setSetIndex(set);
        this.sizeWord++;
    }

    public Set<Integer> find(String searchWord) {
        byte[] bytes = searchWord.getBytes();
        TrieNode searchNode = head;
        List<TrieNode> children = head.getChildren();
        for (int i = 0; i < bytes.length; i++) {
            boolean flagSearch = false;
            for (TrieNode trieNode: children) {
                if (trieNode.getValue().equals(bytes[i])) {
                    flagSearch = true;
                    searchNode = trieNode;
                    break;
                }
            }
            if (!flagSearch) {
                return null;
            }
            children = searchNode.getChildren();
        }
        if (searchNode.isWord()) {
            return searchNode.getSetIndex();
        } else {
            return null;
        }
    }

    public int getSizeWord() {
        return sizeWord;
    }

    public int getSizeNodes() {
        return sizeNodes;
    }
}
