package net.coljate.cache;

/**
 *
 * @author Ollie
 */
public interface ListMultimapTest<K, V>
        extends MultimapTest<K, V> {

    @Override
    ListMultimap<K, V> createTestCollection();

    interface ZeroEntryTests<K, V> extends ListMultimapTest<K, V>, MultimapTest.ZeroEntryTests<K, V> {

    }

    interface OneEntryTests<K, V> extends ListMultimapTest<K, V>, MultimapTest.OneEntryTests<K, V> {

    }

}
