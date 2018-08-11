package net.coljate.table.impl;

import net.coljate.table.Cell;
import net.coljate.table.TableTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SymmetricTableTest {

    abstract class OneCellTest implements TableTest.OneCellTests<Object, Object, Object> {

        private Cell<Object, Object, Object> testCell;

        @BeforeEach
        public final void resetTestCell() {
            testCell = this.createCell();
        }

        abstract Cell<Object, Object, Object> createCell();

        Cell<Object, Object, Object> getTestCell() {
            return testCell;
        }

        @Override
        @Deprecated
        public Cell<Object, Object, Object> getCollectionElement() {
            return this.getTestCell();
        }

        @Override
        public SymmetricTable<Object, Object> createTestCollection() {
            return SymmetricTable.of(this.getTestCell());
        }

    }

    @Nested
    class OneDiagonalCellTest extends OneCellTest {

        @Override
        Cell<Object, Object, Object> createCell() {
            final Object key = new Object();
            final Object value = new Object();
            return Cell.of(key, key, value);
        }

    }

    @Nested
    class OneOffDiagonalCellTest extends OneCellTest {

        @Override
        Cell<Object, Object, Object> createCell() {
            final Object row = new Object();
            final Object column = new Object();
            final Object value = new Object();
            return Cell.of(row, column, value);
        }

        @Override
        @Disabled //FIXME
        public void testIterator() {
            super.testIterator();
        }

        @Test
        @Override
        public void testCount() {
            assertThat(this.createTestCollection().count()).isEqualTo(2);
        }

        @Override
        @Disabled //FIXME
        public void testIterator_HasNextRepeatable() {
            super.testIterator_HasNextRepeatable();
        }

        @Override
        @Disabled //FIXME
        public void testIterator_NextWithoutHaveNext() {
            super.testIterator_NextWithoutHaveNext();
        }

    }

}
