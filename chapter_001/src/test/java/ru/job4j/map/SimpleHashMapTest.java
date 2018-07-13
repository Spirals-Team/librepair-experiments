package ru.job4j.map;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

/**
 * Тесты базовых методов реализации хешмепа
 */
public class SimpleHashMapTest {
    // Определяем хешмеп и инициализируем его значениями
    SimpleHashMap<String, Integer> test = new SimpleHashMap<String, Integer>(){{
        insert("alex", 50);
        insert("dima", 44);
        insert("oleg", 32);
        insert("maks", 77);
        insert("anna", 68);
    }};

    @Test
    /**
     * Тест тестирует метод возвращающий значение по ключу
     * Проверяем значения у 3х элементов
     */
    public void testGetMethod() {
        assertThat(test.get("maks"), is(77));
        assertThat(test.get("oleg"), is(32));
        assertThat(test.get("alex"), is(50));
    }

    @Test
    /**
     * Тест тестирует метод вставки элемента в хранилище
     * Пытаемся вставить элемент с существующим ключом.. Проверям что он не вставился
     * Вставляем новый элемент.. Проверяем что он вставился
     */
    public void testInsertMethod() {
        assertThat(test.insert("oleg", 22), is(false));
        assertThat(test.get("oleg"), is(32));
        assertThat(test.insert("olga", 22), is(true));
        assertThat(test.get("olga"), is(22));
    }

    @Test
    /**
     * Тест тестирует метод удаления элемента по ключу
     * Пытаемся удалить несуществующий элемент..Потом добавляем новый элемент..
     * Проверяем что он вставился.. Удаляем этот элемент..
     * Проверяем что он удалился
     */
    public void testDeleteMethod() {
        assertThat(test.delete("olga"), is(false));
        assertThat(test.insert("olga", 22), is(true));
        assertThat(test.get("olga"), is(22));
        assertThat(test.delete("olga"), is(true));
        assertThat(test.get("olga"), is(nullValue()));
    }

    @Test
    /**
     * Тест тестирует работоспособность итератора
     * Создаем итератор.. Проверяем hasNext..
     * Сдвигаем указатель на конец хранилища.. проверяем hasNext..
     */
    public void testIterator() {
        Iterator<String> iter = test.iterator();
        assertThat(iter.hasNext(), is(true));
        iter.next();
        iter.next();
        iter.next();
        iter.next();
        iter.next();
        assertThat(iter.hasNext(), is(false));
    }
}