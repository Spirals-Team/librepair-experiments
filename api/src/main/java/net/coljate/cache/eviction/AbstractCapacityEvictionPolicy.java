package net.coljate.cache.eviction;

/**
 *
 * @author Ollie
 */
public abstract class AbstractCapacityEvictionPolicy implements CacheEvictionPolicy {

    protected abstract int count();

    protected abstract int capacity();

    protected int evictions() {
        return this.count() - this.capacity();
    }

    @Override
    public String toString() {
        return this.getClass() + ":count=" + this.count() + "/capacity=" + this.capacity();
    }

}
