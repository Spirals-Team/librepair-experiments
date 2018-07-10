package ru.job4j.map;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HashMapTest {

    HashMap<String, Integer> hm;

    @Before
    public void setUp() {
        hm = new HashMap<>();
    }

    @Test
    public void createHashMap() {
        assertTrue(hm != null);
        assertThat(hm.size(), is(0));
    }

    @Test
    public void addFirstItemToBucket() {
        assertTrue(hm.insert("key1", 1));
        assertTrue(hm.insert("key2", 2));
    }

    @Test(expected = NullPointerException.class)
    public void addDuplicateKey() {
        assertTrue(hm.insert("key1", 1));
        assertFalse(hm.insert("key1", 1));
        assertThat(hm.size(), is(1));
        hm.insert(null, 2);
    }

    @Test
    public void addSecondItemToBucket() {
        assertTrue(hm.insert("key1", 1));
        assertTrue(hm.insert("key2", 1));
        assertTrue(hm.insert("key3", 1));
        assertThat(hm.size(), is(3));
    }

    @Test
    public void deleteSingleItemFromBucket() {
        hm.insert("key1", 1);
        assertTrue(hm.delete("key1"));
    }

    @Test
    public void deleteSecondItemFromBucket() {
        assertTrue(hm.insert("key1", 1));
        assertTrue(hm.insert("key2", 1));
        assertTrue(hm.insert("key3", 1));
        assertThat(hm.size(), is(3));
        assertTrue(hm.delete("key1"));
        assertThat(hm.size(), is(2));
    }

    @Test
    public void deleteFirstItemFromBucket() {
        assertTrue(hm.insert("key1", 1));
        assertTrue(hm.insert("key2", 1));
        assertTrue(hm.insert("key3", 1));
        assertThat(hm.size(), is(3));
        assertTrue(hm.delete("key3"));
        assertThat(hm.size(), is(2));
    }

    @Test(expected = NullPointerException.class)
    public void getByNullKey() {
        hm.get(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void getByNonExistKey() {
        hm.get("key2");
    }

    @Test
    public void getItemByKey() {
        hm.insert("key1", 1);
        assertThat(hm.get("key1"), is(1));
        hm.insert("key2", 2);
        hm.insert("key3", 3);
        assertThat(hm.get("key3"), is(3));
    }

    @Test
    public void testIteratorHasNextOnEmptyHashMap() {
        Iterator it = hm.iterator();
        assertFalse(it.hasNext());
    }

    @Test
    public void testIteratorHasNextOnHashMapWithOneElement() {
        Iterator it = hm.iterator();
        hm.insert("key1", 1);
        assertTrue(it.hasNext());
    }

    @Test
    public void testIteratorHasNextOnFilledHashMap() {
        Iterator it = hm.iterator();
        hm.insert("key1", 1);
        hm.insert("key2", 2);
        hm.insert("key3", 3);
        assertTrue(it.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorHasOnEmptyHashMap() {
        Iterator it = hm.iterator();
        it.next();
    }

    @Test
    public void testIteratorHasOnHashMapWithOneElement() {
        Iterator it = hm.iterator();
        hm.insert("key1", 1);
        hm.insert("key2", 2);
        assertThat(((HashMap.Item) it.next()).getValue(), is(1));
        assertThat(((HashMap.Item) it.next()).getValue(), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorHasOnFilledHashMap() {
        Iterator it = hm.iterator();
        hm.insert("key1", 1);
        hm.insert("key2", 2);
        hm.insert("key3", 3);
        assertThat(((HashMap.Item) it.next()).getValue(), is(1));
        assertThat(((HashMap.Item) it.next()).getValue(), is(2));
        assertThat(((HashMap.Item) it.next()).getValue(), is(3));
        it.next();
    }

    @Test
    public void testResizeHashMap() {
        hm.insert("key1", 1);
        hm.insert("key2", 2);
        assertThat(hm.toString(), is("{key1=1, key2=2}"));
        hm.insert("key3", 3);
        hm.insert("key4", 4);
        hm.insert("key5", 5);
        assertThat(hm.size(), is(5));
        assertThat(hm.toString(),
            is("{key1=1, key2=2, key3=3, key4=4, key5=5}"));
        assertThat(hm.toString(),
            is("{key1=1, key2=2, key3=3, key4=4, key5=5}"));
        hm.insert("key6", 6);
        assertThat(hm.size(), is(6));
        assertThat(hm.toString(),
            is("{key1=1, key2=2, key3=3, key4=4, key5=5, key6=6}"));
    }
}