package ru.job4j.set;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class HashTableTest {

    @Test
    public void whenInsertElementThenHashTableHasTheElement() {
        HashTable<String> hashTable = new HashTable<>();
        hashTable.insert("first");
        assertThat(hashTable.find("first"), is(true));
        assertThat(hashTable.find("second"), is(false));
    }

    @Test
    public void whenDeletElementThenHashTableDoesNotHaveTheElement() {
        HashTable<String> hashTable = new HashTable<>();
        hashTable.insert("first");
        hashTable.insert("second");
        assertThat(hashTable.delete("first"), is(true));
        assertThat(hashTable.delete("first"), is(false));
        assertThat(hashTable.find("first"), is(false));
    }

    @Test
    public void whenAddMoreElementThanHashTableCanContainThenIsCreatedNewHashTableWhichContainsAllTheElementsFromOldHashTable() {
        /*
        * Первоначальный размер хэш-таблицы 10 элементов, при коэффициенте заполнения больше 0,5
        * хэш-таблица увеличивается в 2-а раза и элементы с старой таблицы помещаются в нее.
         */
        HashTable<Character> hashTable = new HashTable<>();
        Character[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'h', 'i', 'j', 'k', 'l'};
        for (Character item : letters) {
            hashTable.insert(item);
        }
        for (Character item : letters) {
            assertThat(hashTable.find(item), is(true));
        }
    }
}
