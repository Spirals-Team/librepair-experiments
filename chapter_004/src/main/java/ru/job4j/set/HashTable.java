package ru.job4j.set;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class HashTable<E> {

    private class NullClass<E> { } //класс для пометки удаленного элемента, что не прерывать последовательность элементов.

    private int arraySize;
    private Object[] hashArray;
    private int elems = 0;

    public HashTable() {
        this.arraySize = 10; //первоначальный размер хэш-таблицы
        this.hashArray = new Object[arraySize];
    }

    /*
    * Приватный конструктор для создания хэш таблицы
    * заданного размера внутри данного класса
     */
    private HashTable(int arraySize) {
        this.arraySize = arraySize;
        this.hashArray = new Object[arraySize];
    }

    private Object[] getHashArray() {
        return hashArray;
    }

    private int hashFunc(int key) {
        return key % arraySize;
    }

    public boolean insert(E e) {
        if (this.checkSize()) {
            System.gc(); //вызов сборщика мусора для удаления вспомогательного объекта
        }
        int key = e.hashCode() > 0 ? e.hashCode() : Math.abs(e.hashCode());
        int hashVal = hashFunc(key);
        while (hashArray[hashVal] != null && !(hashArray[hashVal] instanceof NullClass)) {
            hashVal++;
            hashVal %= arraySize;
        }
        hashArray[hashVal] = e;
        elems++;
        return true;
    }

    public boolean delete(E e) {
        boolean result = false;
        int hashVal = hashFunc(e.hashCode() > 0 ? e.hashCode() : Math.abs(e.hashCode()));
        while (hashArray[hashVal] != null && !(hashArray[hashVal] instanceof NullClass)) {
            if (hashArray[hashVal].equals(e)) {
                hashArray[hashVal] = new NullClass();
                elems--;
                result = true;
                break;
            }
            hashVal++;
            hashVal %= arraySize;
        }
        return result;
    }

    public boolean find(E e) {
        boolean result = false;
        int hashVal = hashFunc(e.hashCode() > 0 ? e.hashCode() : Math.abs(e.hashCode()));
        while (hashArray[hashVal] != null) {
            if (hashArray[hashVal].equals(e)) {
                result = true;
                break;
            }
            hashVal++;
            hashVal %= arraySize;
        }
        return result;
    }

    /*
    * Проверка размера хэш таблицы, если коэффициент заполенния больше 0,5
    * хэш таблица перестраивается и ее размер в 2-а раза больше.
     */
    @SuppressWarnings("unchecked")
    private boolean checkSize() {
        boolean result = false;
        if (elems > 0.5 * this.arraySize) {
            this.arraySize *= 2;
            HashTable newHashTable = new HashTable(this.arraySize);
            for (Object item : this.hashArray) {
                if (item != null && !(item instanceof NullClass)) {
                    newHashTable.insert(item);
                }
            }
            Object[] newTable = new Object[this.arraySize];
            System.arraycopy(newHashTable.getHashArray(), 0, newTable, 0, this.arraySize);
            this.hashArray = newTable;
            result = true;
        }
        return result;
    }
}
