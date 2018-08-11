//package net.coljate.cache.impl;
//
//import java.util.function.Function;
//
//import net.coljate.cache.MutableCache;
//import net.coljate.cache.MutableMultimap;
//import net.coljate.collection.Collection;
//import net.coljate.collection.MutableCollection;
//import net.coljate.map.MutableEntry;
//import net.coljate.util.Functions;
//
///**
// *
// * @author Ollie
// */
//public abstract class AbstractMutableCacheBackedMultimap<K, V, C extends MutableCollection<V>>
//        extends AbstractMapBackedMultimap<K, V, C>
//        implements MutableMultimap<K, V> {
//
//    private final MutableCache<K, C> cache;
//    final Function<? super Collection<V>, ? extends C> convertCollection;
//
//    protected AbstractMutableCacheBackedMultimap(
//            final MutableCache<K, C> cache,
//            final Function<? super Collection<V>, ? extends C> convertCollection) {
//        super(cache);
//        this.cache = cache;
//        this.convertCollection = convertCollection;
//    }
//
//    @Override
//    protected MutableCache<K, C> mutableCacheCopy(final Function<C, C> makeMutable) {
////        final MutableCache<K, C> copy = cache.mutableCopy();
////        cache.forEach(entry -> copy.put(entry));
////        return copy;
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public MutableMultimapEntry<K, V, C> getEntry(final Object key) {
//        return Functions.ifNonNull(cache.getEntry(key), MutableDelegatedEntry::new);
//    }
//
//    @Override
//    public C put(final K key, final Collection<V> value) {
//        return cache.put(key, convertCollection.apply(value));
//    }
//
//    @Override
//    public boolean remove(final Object key, final Object value) {
//        return cache.remove(key, value);
//    }
//
//    @Override
//    public void clear() {
//        cache.clear();
//    }
//
//    protected class MutableDelegatedEntry
//            extends WrappedEntry<MutableEntry<K, C>>
//            implements MutableMultimapEntry<K, V, C> {
//
//        MutableDelegatedEntry(final MutableEntry<K, C> entry) {
//            super(entry);
//        }
//
//        @Override
//        public void setValue(final Collection<V> value) {
//            entry.setValue(convertCollection.apply(value));
//        }
//
//    }
//
//}
