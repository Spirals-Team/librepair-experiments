package net.coljate.map.impl;

import net.coljate.map.Entry;
import net.coljate.map.MutableMap;
import net.coljate.map.MutableMapTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringTrieTest {

    @Nested
    class EmptyTrieTest implements MutableMapTest.ZeroEntryTests<String, Object> {

        @Override
        public StringTrie<Object> createTestCollection() {
            return new StringTrie<>();
        }

        @Override
        public Entry<String, Object> createTestObject() {
            return new SimpleImmutableEntry<>("foo", new Object());
        }

        @Test
        void testNotEmpty() {
            assertFalse(this.createTestCollection().containsKey(null));
            assertFalse(this.createTestCollection().containsKey(""));
        }

    }

    @Nested
    class SingletonTrieTest implements MutableMapTest.OneEntryTests<String, Object> {

        private Entry<String, Object> entry;

        @BeforeEach
        public final void createEntry() {
            this.entry = this.createTestObject();
        }

        @Override
        public Entry<String, Object> createTestObject() {
            return new SimpleImmutableEntry<>("foo", new Object());
        }

        @Override
        public Entry<String, Object> getCollectionElement() {
            return entry;
        }

        @Override
        public MutableMap<String, Object> createTestCollection() {
            return new StringTrie<>(entry);
        }

    }

    @Nested
    class GeneralTrieTests {

        @Test
        void testRemoveSubstring() {

            final StringTrie<Integer> trie = new StringTrie<>();
            trie.put("foo", 1);
            trie.put("foobar", 2);

            assertFalse(trie.containsKey("fo"));
            assertTrue(trie.containsKey("foo"));
            assertTrue(trie.containsKey("foobar"));

            trie.removeKey("foo");

            assertFalse(trie.containsKey("fo"));
            assertFalse(trie.containsKey("foo"));
            assertTrue(trie.containsKey("foobar"));

        }

    }

}