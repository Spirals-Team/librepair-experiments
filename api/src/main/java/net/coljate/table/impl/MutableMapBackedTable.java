package net.coljate.table.impl;

import net.coljate.map.MutableMap;
import net.coljate.table.MutableTable;
import net.coljate.table.Table;

/**
 *
 * @author Ollie
 */
public class MutableMapBackedTable<R, C, V>
        extends MapBackedTable<R, C, V>
        implements MutableTable<R, C, V> {

    public static <R, C, V> MutableMapBackedTable<R, C, V> createBackedByHashMap() {
        return new MutableMapBackedTable<>(MutableMap.createHashMap());
    }

    public static <R, C, V> MutableMapBackedTable<R, C, V> copyOf(final Table<? extends R, ? extends C, ? extends V> that) {
        final MutableMapBackedTable<R, C, V> table = createBackedByHashMap();
        that.forEach(table::addCell);
        return table;
    }

    private final MutableMap<KeyPair<R, C>, V> map;

    protected MutableMapBackedTable(final MutableMap<KeyPair<R, C>, V> map) {
        super(map);
        this.map = map;
    }

    @Override
    public V put(final R rowKey, C columnKey, V value) {
        return map.put(new KeyPair<>(rowKey, columnKey), value);
    }

    @Override
    public boolean add(final R rowKey, final C columnKey, final V value) {
        return map.add(new KeyPair<>(rowKey, columnKey), value);
    }

    @Override
    public V evict(Object rowKey, Object columnKey) {
        return map.evict(new KeyPair<>(rowKey, columnKey));
    }

    @Override
    public boolean remove(final Object rowKey, final Object columnKey, final Object value) {
        return map.remove(new KeyPair<>(rowKey, columnKey), value);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public ImmutableMapBackedTable<R, C, V> immutableCopy() {
        return new ImmutableMapBackedTable<>(map.immutableCopy());
    }

}
