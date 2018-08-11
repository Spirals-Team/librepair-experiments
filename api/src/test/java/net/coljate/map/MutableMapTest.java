package net.coljate.map;

import net.coljate.set.MutableSetTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ollie
 */
public interface MutableMapTest<K, V> extends MapTest<K, V>, MutableSetTest<Entry<K, V>> {

    @Override
    MutableMap<K, V> createTestCollection();

    interface ZeroEntryTests<K, V> extends MutableMapTest<K, V>, MapTest.ZeroEntryTests<K, V>, MutableSetTest.ZeroElementTests<Entry<K, V>> {

        @Test
        default void testPut() {
            final MutableMap<K, V> map = this.createTestCollection();
            final Entry<K, V> entry = this.createTestObject();
            final V previous = map.put(entry.key(), entry.value());
            assertNull(previous);
            assertThat(map.get(entry.key())).isEqualTo(entry.value());
        }

        @Test
        default void testEvict_NullKey() {
            assertNull(this.createTestCollection().evict(null));
        }

    }

    interface OneEntryTests<K, V> extends MutableMapTest<K, V>, MapTest.OneEntryTests<K, V>, MutableSetTest.OneElementTests<Entry<K, V>> {

        @Test
        default void testGetEntry_View() {

            final MutableMap<K, V> map = this.createTestCollection();
            final Entry<K, V> entryWritten = this.getCollectionElement();
            final Entry<K, V> entryView = map.getEntry(entryWritten.key());

            assertThat(entryView.value()).isEqualTo(entryWritten.value());

            final Entry<K, V> newEntry = this.createTestObject();
            assertThat(newEntry.value()).isNotEqualTo(entryWritten.value());

            map.put(entryWritten.key(), newEntry.value());

            assertThat(entryView.value()).isEqualTo(newEntry.value());

        }

    }

    interface TwoEntryTests<K, V> extends MutableMapTest<K, V>, MapTest.TwoEntryTests<K, V>, MutableSetTest.MultiElementTests<Entry<K, V>> {

        @Override
        MutableMap<K, V> createTestCollection(List<Entry<K, V>> entries);

        @Override
        default MutableMap<K, V> createTestCollection() {
            return this.createTestCollection(java.util.Collections.emptyList());
        }

        @Test
        default void testPut() {

            final Entry<K, V> e1 = this.createTestObject();
            final Entry<K, V> e2 = this.createTestObject();

            final MutableMap<K, V> map = this.createTestCollection();
            map.put(e1);

            assertTrue(map.containsKey(e1.key()));
            assertEquals(e1.value(), map.get(e1.key()));

            assertFalse(map.containsKey(e2.key()));
            assertNull(map.get(e2.key()));

            map.put(e2);
            assertTrue(map.containsKey(e1.key()));
            assertEquals(e1.value(), map.get(e1.key()));
            assertTrue(map.containsKey(e2.key()));
            assertEquals(e2.value(), map.get(e2.key()));

            assertEquals(2, map.count());
            assertEquals(2, map.keys().count());

        }

    }

}
