package net.coljate.cache.eviction;

import java.util.Iterator;
import java.util.function.BiFunction;

import net.coljate.util.Arrays;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public class CombinedCacheEvictionPolicy implements CacheEvictionPolicy {

    public static CacheEvictionPolicy create(final CacheEvictionPolicy policy, final CacheEvictionPolicy... rest) {
        if (rest.length == 0) {
            return policy;
        } else {
            return new CombinedCacheEvictionPolicy(Arrays.concat(policy, rest));
        }
    }

    private final CacheEvictionPolicy[] policies;

    protected CombinedCacheEvictionPolicy(final CacheEvictionPolicy[] policies) {
        this.policies = policies;
    }

    @Override
    public Iterator<Object> notifyRead(final Object key) {
        return this.operateOnAll(key, CacheEvictionPolicy::notifyRead);
    }

    @Override
    public Iterator<Object> notifyWrite(final Object key) {
        return this.operateOnAll(key, CacheEvictionPolicy::notifyWrite);
    }

    @Override
    public Iterator<Object> notifyRemove(final Object key) {
        return this.operateOnAll(key, CacheEvictionPolicy::notifyRemove);
    }

    private Iterator<Object> operateOnAll(final Object key, final BiFunction<CacheEvictionPolicy, Object, Iterator<Object>> op) {
        @SuppressWarnings({"unchecked", "rawtypes"})
        final Iterator<Object>[] iterators = new Iterator[policies.length];
        for (int i = 0; i < policies.length; i++) {
            iterators[i] = op.apply(policies[i], key);
        }
        return Iterators.concat(iterators);
    }

    @Override
    public void notifyClear() {
        Arrays.consume(policies, CacheEvictionPolicy::notifyClear);
    }

}
