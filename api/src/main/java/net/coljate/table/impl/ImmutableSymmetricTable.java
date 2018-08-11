package net.coljate.table.impl;

import net.coljate.map.ImmutableMap;
import net.coljate.table.Cell;
import net.coljate.table.ImmutableCell;
import net.coljate.table.ImmutableTable;
import net.coljate.util.iterator.UnmodifiableIterator;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 */
public class ImmutableSymmetricTable<K, V>
        extends SymmetricTable<K, V>
        implements ImmutableTable<K, K, V> {

    protected ImmutableSymmetricTable(final ImmutableMap<OneOrTwoSet<K>, V> map) {
        super(map);
    }

    @Override
    public ImmutableCell<K, K, V> cellIfPresent(Object row, Object column) {
        return Functions.ifNonNull(super.cellIfPresent(row, column), Cell::immutableCopy);
    }

    @Override
    public UnmodifiableIterator<Cell<K, K, V>> iterator() {
        return UnmodifiableIterator.wrap(super.iterator());
    }

}
