package net.coljate.table;

import net.coljate.set.MutableSetTest;

/**
 *
 * @author Ollie
 */
public interface MutableTableTest<R, C, V> extends TableTest<R, C, V>, MutableSetTest<Cell<R, C, V>> {

    @Override
    MutableTable<R, C, V> createTestCollection();

}
