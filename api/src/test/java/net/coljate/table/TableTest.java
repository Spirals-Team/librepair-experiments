package net.coljate.table;

import net.coljate.set.SetTest;

/**
 *
 * @author Ollie
 */
public interface TableTest<R, C, V> extends SetTest<Cell<R, C, V>> {

    @Override
    Table<R, C, V> createTestCollection();

    interface OneCellTests<R, C, V> extends TableTest<R, C, V>, SetTest.OneElementTests<Cell<R, C, V>> {

    }

}
