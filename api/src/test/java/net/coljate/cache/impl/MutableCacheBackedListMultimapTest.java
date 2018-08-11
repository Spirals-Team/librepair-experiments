//package net.coljate.cache.impl;
//
//import net.coljate.cache.MutableListMultimap;
//import net.coljate.cache.MutableListMultimapTest;
//import net.coljate.collection.Collection;
//import net.coljate.list.ImmutableList;
//import net.coljate.list.MutableList;
//import net.coljate.map.Entry;
//import net.coljate.map.ImmutableEntry;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//
///**
// *
// * @author Ollie
// */
//@SuppressWarnings({"unchecked", "rawtypes"})
//public class MutableCacheBackedListMultimapTest {
//
//    abstract class BaseTest implements MutableListMultimapTest<Object, Object> {
//
//        @Override
//        public Entry<Object, Collection<Object>> createTestObject() {
//            return ImmutableEntry.of(new Object(), ImmutableList.of(new Object()));
//        }
//
//    }
//
//    @Nested
//    class ZeroEntryTest
//            extends BaseTest
//            implements MutableListMultimapTest.ZeroEntryTests<Object, Object> {
//
//        @Override
//        public MutableListMultimap<Object, Object> createTestCollection() {
//            return MutableCacheBackedListMultimap.createLinkedListMultimap();
//        }
//
//        @Test
//        public void testGet_Missing() {
//            final MutableList<Object> list = this.createTestCollection().get(new Object());
//            assertNotNull(list);
//            assertTrue(list.isEmpty());
//        }
//
//    }
//
//    @Nested
//    class OneEntryTest
//            extends BaseTest
//            implements MutableListMultimapTest.OneEntryTests<Object, Object> {
//
//        private Entry<Object, Collection<Object>> element;
//
//        @BeforeEach
//        public final void resetEntry() {
//            element = this.createTestObject();
//        }
//
//        @Override
//        public Entry<Object, Collection<Object>> getCollectionElement() {
//            return element;
//        }
//
//        @Override
//        public MutableListMultimap<Object, Object> createTestCollection() {
//            final MutableListMultimap<Object, Object> multimap = MutableListMultimap.createLinkedListMultimap();
//            multimap.add(this.getCollectionElement());
//            return multimap;
//        }
//
//    }
//
//}
