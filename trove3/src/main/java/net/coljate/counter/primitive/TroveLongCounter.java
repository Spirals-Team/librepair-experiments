package net.coljate.counter.primitive;

import net.coljate.counter.AbstractCounter;
import net.coljate.set.primitive.LongSet;
import net.coljate.set.primitive.TroveLongSet;

import gnu.trove.map.TLongLongMap;

/**
 *
 * @author Ollie
 */
public class TroveLongCounter extends AbstractCounter<Long> implements LongCounter {

    private final TLongLongMap map;

    public TroveLongCounter(final TLongLongMap map) {
        this.map = map;
    }

    @Override
    public long count(final long value) {
        return map.containsKey(value)
                ? map.get(value)
                : 0;
    }

    @Override
    public LongSet elements() {
        return TroveLongSet.viewOf(map.keySet());
    }

    @Override
    public LongIterator iterator() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public MutableLongCounter mutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public ImmutableLongCounter immutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

}
