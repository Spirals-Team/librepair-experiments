//package net.coljate.cache.impl;
//
//import java.util.function.Function;
//
//import net.coljate.cache.ImmutableListMultimap;
//import net.coljate.cache.MutableCache;
//import net.coljate.cache.MutableListMultimap;
//import net.coljate.collection.Collection;
//import net.coljate.list.MutableList;
//import net.coljate.list.impl.ImmutableNativeArray;
//import net.coljate.list.impl.MutableLinkedList;
//import net.coljate.map.ImmutableMap;
//
///**
// *
// * @author Ollie
// */
//public class MutableCacheBackedListMultimap<K, V, C extends MutableList<V>>
//        extends AbstractMutableCacheBackedMultimap<K, V, C>
//        implements MutableListMultimap<K, V> {
//
//    public static <K, V> MutableCacheBackedListMultimap<K, V, MutableLinkedList<V>> createLinkedListMultimap() {
//        return new MutableCacheBackedListMultimap<>(
//                MutableCache.create(k -> MutableLinkedList.create()),
//                MutableLinkedList::copyOf,
//                MutableLinkedList::mutableCopy);
//    }
//
//    private final Function<C, C> mutableCopy;
//
//    protected MutableCacheBackedListMultimap(
//            final MutableCache<K, C> cache,
//            final Function<? super Collection<V>, ? extends C> convertCollection,
//            final Function<C, C> mutableCopy) {
//        super(cache, convertCollection);
//        this.mutableCopy = mutableCopy;
//    }
//
//    @Override
//    public MutableListMultimap<K, V> mutableCopy() {
//        final MutableCache<K, C> cache = this.mutableCacheCopy(mutableCopy);
//        return new MutableCacheBackedListMultimap<>(cache, convertCollection, mutableCopy);
//    }
//
//    @Override
//    public ImmutableListMultimap<K, V> immutableCopy() {
//        final ImmutableMap<K, ImmutableNativeArray<V>> map = this.immutableCacheCopy(ImmutableNativeArray::copyOf);
//        return new ImmutableNativeArrayMultimap<>(map);
//    }
//
//}
