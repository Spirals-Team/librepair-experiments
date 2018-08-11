package net.coljate.table.impl;

import net.coljate.map.MutableMap;
import net.coljate.table.MutableTable;

/**
 *
 * @author Ollie
 */
public class MutableSymmetricTable<K, V>
        extends SymmetricTable<K, V>
        implements MutableTable<K, K, V> {

    public static <K, V> MutableSymmetricTable<K, V> createHashBackedTable() {
        return new MutableSymmetricTable<>(MutableMap.createHashMap());
    }

    private final MutableMap<OneOrTwoSet<K>, V> map;

    protected MutableSymmetricTable(final MutableMap<OneOrTwoSet<K>, V> map) {
        super(map);
        this.map = map;
    }

    @Override
    public V put(final K rowKey, final K columnKey, final V value) {
        return map.put(new OneOrTwoSet<>(rowKey, columnKey), value);
    }

    @Override
    public V evict(final Object rowKey, final Object columnKey) {
        return map.evict(new OneOrTwoSet<>(rowKey, columnKey));
    }

    @Override
    public boolean remove(final Object rowKey, final Object columnKey, final Object value) {
        return map.remove(new OneOrTwoSet<>(rowKey, columnKey), value);
    }

    @Override
    public void clear() {
        map.clear();
    }

}
