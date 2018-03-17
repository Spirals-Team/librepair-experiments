package ru.skorikov;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.NoSuchElementException;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 08.11.17
 * @ version: java_kurs_standart
 */
public class DirectoryTest {
    /**
     * Tests Exception.
     */
    @Rule
    public ExpectedException testException = ExpectedException.none();

    /**
     * Пробуем добавить элемент.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenAddNewItemThenreturnTrue() throws Exception {
        Directory<String, String> directory = new Directory<>();
        Assert.assertTrue(directory.insert("Key", "Value"));
    }

    /**
     * Пробуем добавить дубликат.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenAddDublicateThenReturnFalse() throws Exception {
        Directory<String, String> directory = new Directory<>();
        directory.insert("Key", "Value");

        Assert.assertFalse(directory.insert("Key", "Value"));
    }

    /**
     * Пробуем получить объект.
     *
     * @throws Exception исключение.
     */
    @Test
    public void ifElementExistsThenReturnValue() throws Exception {
        Directory<String, String> directory = new Directory<>();
        directory.insert("Key", "Value");

        Assert.assertEquals(directory.get("Key"), "Value");
    }

    /**
     * Пробуем получить несуществующий объект.
     *
     * @throws Exception исключение.
     */
    @Test
    public void ifElementNoExistsThenException() throws Exception {
        testException.expect(NoSuchElementException.class);
        Directory<String, String> directory = new Directory<>();
        directory.insert("Key", "Value");
        directory.get("Key_1");
    }

    /**
     * Пробуем удалить объект.
     *
     * @throws Exception исключение.
     */
    @Test
    public void ifElementExistsThenDeleteTrue() throws Exception {
        Directory<String, String> directory = new Directory<>();
        directory.insert("Key", "Value");

        Assert.assertTrue(directory.delete("Key"));
    }

    /**
     * Пробуем удалить несуществующий объект.
     *
     * @throws Exception исключение.
     */
    @Test
    public void ifElementExistsThenDeleteFalse() throws Exception {
        Directory<String, String> directory = new Directory<>();
        directory.insert("Key", "Value");

        Assert.assertFalse(directory.delete("Key_1"));
    }

    /**
     * Пробуем добавить объект с ключом null.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryAddNullKey() throws Exception {
        Directory<String, String> directory = new Directory<>();
        directory.insert(null, "Value");

        Assert.assertEquals(directory.get(null), "Value");
    }

    /**
     * Пробуем добавить объект с данными равными null.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryAddNullValue() throws Exception {
        Directory<String, String> directory = new Directory<>();
        directory.insert("Key", null);

        Assert.assertEquals(directory.get("Key"), null);
    }
    /**
     * Пробуем получить итератор next.
     *
     * @throws Exception исключение.
     */
    @Test
    public void tryGetIteratorNext() throws Exception {
        Directory<Integer, String> directory = new Directory<>();
        directory.insert(0, "Value1");

        Assert.assertEquals(((Directory.Element) directory.next()).getValue(), directory.get(0));
    }

    /**
     * Пробуем получить элемент пустой коллекции.
     *
     * @throws Exception исключение.
     */
    @Test(expected = NoSuchElementException.class)
    public void tryGetNullHasNext() throws Exception {
        Directory<Integer, String> directory = new Directory<>();

        directory.next();
    }
}