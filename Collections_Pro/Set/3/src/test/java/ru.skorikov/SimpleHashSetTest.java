package ru.skorikov;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 03.11.17
 * @ version: java_kurs_standart
 */
public class SimpleHashSetTest {

    /**
     * Добавим один элемент.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenAddElementThenReturnTrue() throws Exception {
        SimpleHashSet<String> set = new SimpleHashSet<>();
        assertTrue(set.add("String"));

    }

    /**
     * Пробуем добавить два одинаковых элемента.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenAddTwoSameElementThenreturnFalse() throws Exception {
        SimpleHashSet<String> set = new SimpleHashSet<>();
        set.add("String");
        assertFalse(set.add("String"));
    }

    /**
     * Добавленный элемент должен содержаться в контейнере.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenContainsElementThenTrue() throws Exception {
        SimpleHashSet<String> set = new SimpleHashSet<>();
        set.add("String");
        assertTrue(set.contains("String"));
    }

    /**
     * Не добавленный элемент не должен содержаться в контейнере.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenNoContainsElementThenFalse() throws Exception {
        SimpleHashSet<String> set = new SimpleHashSet<>();
        set.add("String");
        assertFalse(set.contains("TestString"));
    }

    /**
     * Добавленный элемент может быть удален.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenRemoveElementThenTrue() throws Exception {
        SimpleHashSet<String> set = new SimpleHashSet<>();
        set.add("String");
        assertTrue(set.remove("String"));
    }

    /**
     * Несуществующий элемент не может быть удален.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenNoRemoveElementThenFalse() throws Exception {
        SimpleHashSet<String> set = new SimpleHashSet<>();
        set.add("String");
        assertFalse(set.remove("TestString"));
    }
    /**
     * Пробуем получить коллекцию.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryGetConteiner() throws Exception {
        SimpleHashSet<String> set = new SimpleHashSet<>();
        set.add("String");
        set.getTable();
    }

}