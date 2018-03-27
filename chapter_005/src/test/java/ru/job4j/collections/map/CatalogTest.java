package ru.job4j.collections.map;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests.
 * @author Hincu Andrei (andreih1981@gmail.com)on 16.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class CatalogTest {
    /**
     * Каталог.
     */
    private Catalog<String, String> catalog;
    /**
     * Инициализация.
     */
    @Before
    public void start() {
        catalog = new Catalog<>();
        catalog.insert("Ivanov", "Ivan");
        catalog.insert("Petrov", "Nicolai");
    }

    /**
     * Сравниваем колличество добавленных элементов с колличеством
     * которое возвращает итератор.
     */
    @Test
    public void whenWasAddedTwoElementsThenIteratorReturnTwoElements() {
        Iterator it = catalog.iterator();
        int count = 0;
        while (it.hasNext()) {
            it.next();
            count++;
        }
        assertThat(count, is(2));
    }

    /**
     * Метод проверяет изменение значения при добавлении элемента с существующим ключом.
     */
    @Test
    public void whenAddedElementWithExistingKey() {
        catalog.insert("Petrov", "Petr");
        String petr = catalog.get("Petrov");
        assertThat(petr, is("Petr"));
    }

    /**
     * Метод получает значения по ключу.
     */
    @Test
    public void whenAddedTwoElementsThenWasCalledByKey() {
        String ivan = catalog.get("Ivanov");
        assertThat(ivan, is("Ivan"));
        String petrov = catalog.get("Petrov");
        assertThat(petrov, is("Nicolai"));
        catalog.insert(null, "Sidorov");
        String nullKey = catalog.get(null);
        assertThat(nullKey, is("Sidorov"));
    }

    /**
     * Метод удаляет элементы по ключу.
     */
    @Test
    public void whenElementWasDeleted() {
        boolean delete = catalog.delete("Ivanov");
        assertThat(delete, is(true));
        String ivanov = catalog.get("Ivanov");
        assertThat(null, is(ivanov));
    }

}