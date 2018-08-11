package net.coljate.cache.impl;

import java.util.function.Function;

import net.coljate.cache.MutableCacheTest;
import net.coljate.map.SameObjectEntryCreator;

import org.junit.jupiter.api.Nested;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ConcurrentMutableMapBackedCacheTest {

    abstract class BaseTest extends SameObjectEntryCreator implements MutableCacheTest<Object, Object> {

        @Override
        public abstract ConcurrentMutableMapBackedCache<Object, Object> createTestCollection();

    }

    @Nested
    class ZeroEntryTest
            extends BaseTest
            implements MutableCacheTest.ZeroEntryTests<Object, Object> {

        @Override
        public ConcurrentMutableMapBackedCache<Object, Object> createTestCollection() {
            return ConcurrentMutableMapBackedCache.create(Function.identity());
        }

    }

    @Nested
    class OneEntryTest
            extends BaseTest
            implements MutableCacheTest.OneEntryTests<Object, Object> {

        @Override
        public ConcurrentMutableMapBackedCache<Object, Object> createTestCollection() {
            final ConcurrentMutableMapBackedCache<Object, Object> cache = ConcurrentMutableMapBackedCache.create(Function.identity());
            cache.add(this.getCollectionElement());
            return cache;
        }

    }

}
