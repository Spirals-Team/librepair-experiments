package net.coljate.cache;

import net.coljate.collection.Collection;
import net.coljate.list.List;
import net.coljate.map.Entry;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 * @author Ollie
 */
public interface MutableListMultimapTest<K, V>
        extends ListMultimapTest<K, V>, MutableMultimapTest<K, V> {

    @Override
    MutableListMultimap<K, V> createTestCollection();

    interface ZeroEntryTests<K, V>
            extends MutableListMultimapTest<K, V>, ListMultimapTest.ZeroEntryTests<K, V>, MutableMultimapTest.ZeroEntryTests<K, V> {

        @Test
        @Override
        default void testAdd() {

            final Entry<K, Collection<V>> entry = this.createTestObject();
            final MutableListMultimap<K, V> multimap = this.createTestCollection();

            assertTrue(multimap.add(entry));
            assertTrue(multimap.add(entry));

            final List<V> got = multimap.get(entry.key());
            assertThat(got.count()).isEqualTo(2);

        }

    }

    interface OneEntryTests<K, V>
            extends MutableListMultimapTest<K, V>, ListMultimapTest.OneEntryTests<K, V>, MutableMultimapTest.OneEntryTests<K, V> {

    }

}
