package net.coljate.map;

import net.coljate.set.SetTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ollie
 */
public interface MapTest<K, V> extends SetTest<Entry<K, V>> {

    @Override
    Map<K, V> createTestCollection();

    default boolean entriesEqual(final Entry<?, ?> e1, final Entry<?, ?> e2) {
        return Objects.equals(e1, e2);
    }

    @Test
    default void testGetEntry_Missing() {
        assertNull(this.createTestCollection().getEntry(new Object()));
    }

    @Test
    default void testContains_Missing() {
        assertFalse(this.createTestCollection().contains(new Object()));
        assertFalse(this.createTestCollection().containsKey(new Object()));
        assertFalse(this.createTestCollection().containsValue(new Object()));
    }

    interface ZeroEntryTests<K, V> extends MapTest<K, V>, SetTest.ZeroElementTests<Entry<K, V>> {

        @Test
        default void testGetIfPresent() {
            final Map<K, V> map = this.createTestCollection();
            final Object key = new Object();
            assertNull(map.getIfPresent(key));
            assertFalse(map.containsKey(key));
        }

    }

    interface OneEntryTests<K, V> extends MapTest<K, V>, SetTest.OneElementTests<Entry<K, V>> {

        @Test
        default void testGetEntry() {
            final Entry<K, V> entry = this.getCollectionElement();
            assertNotNull(entry);
            final Map<K, V> map = this.createTestCollection();
            assertNotNull(map);
            assertThat(map.getEntry(entry.key())).isEqualTo(entry);
        }

        @Test
        default void testContainsEntry() {
            final Map<K, V> map = this.createTestCollection();
            final Entry<K, V> entry = this.getCollectionElement();
            assertTrue(map.containsEntry(entry));
        }

        @Test
        default void testKeys() {
            final Map<K, V> map = this.createTestCollection();
            assertThat(map.keys().count()).isEqualTo(1);
            assertTrue(map.keys().contains(this.getCollectionElement().key()), map.keys() + " should contain " + this.getCollectionElement().key());
            assertFalse(map.keys().contains(this.getCollectionElement().value()));
        }

    }

    interface TwoEntryTests<K, V> extends MapTest<K, V>, SetTest.MultiElementTests<Entry<K, V>> {

        @Override
        default Map<K, V> createTestCollection() {
            return this.createTestCollection(java.util.Collections.emptyList());
        }

        @Override
        Map<K, V> createTestCollection(List<Entry<K, V>> entries);

    }

}
