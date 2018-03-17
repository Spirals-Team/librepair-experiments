package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 09.10.17
 * @ version: java_kurs_standart
 */
public class SimpleArrayTest {
    /**
     * Проверим возможность добавить строки.
     *
     * @throws Exception исключение.
     */
    @Test
    public void isAddStringToSimleArray() throws Exception {
        SimpleArray<String> simpleArray = new SimpleArray<>(10);

        simpleArray.add("String1");
        simpleArray.add("String2");
        String testStr = "String2";

        Assert.assertEquals(simpleArray.get(1), testStr);
    }

    /**
     * Проверим возможность добавить числа.
     *
     * @throws Exception исключение.
     */
    @Test
    public void isAddIntegerToSimleArray() throws Exception {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(10);

        simpleArray.add(25);
        simpleArray.add(32);
        int testInteger = 25;

        Assert.assertThat(simpleArray.get(0), is(testInteger));
    }

    /**
     * Пробуем получить элемент.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenGetElementThenReturnElement() throws Exception {
        SimpleArray<String> simpleArray = new SimpleArray<>(5);

        simpleArray.add("Str_1");
        simpleArray.add("Str_2");
        simpleArray.add("Str_3");

        String testStr = "Str_2";

        Assert.assertThat(simpleArray.get(1), is(testStr));
    }

    /**
     * Пробуем обновить элемент.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenUpdateElementThenReturnUpdete() throws Exception {
        SimpleArray<String> simpleArray = new SimpleArray<>(5);

        simpleArray.add("String1");
        simpleArray.add("String2");
        simpleArray.update(1, "TestString");

        Assert.assertThat(simpleArray.get(1), is("TestString"));
    }

    /**
     * Пробуем удалить элемент.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenDeleteElementThenDelete() throws Exception {
        SimpleArray<String> simpleArray = new SimpleArray<>(5);

        simpleArray.add("String1");
        simpleArray.add("String2");
        simpleArray.add("String3");

        simpleArray.delete("String2");

        Assert.assertEquals(simpleArray.get(1), "String3");
    }
    /**
     * Пробуем добавить елемент сверх массива.
     *
     * @throws Exception исключение.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void tryAddExceccElement() throws Exception {
        SimpleArray<String> simpleArray = new SimpleArray<>(0);
        simpleArray.add("String1");
    }
    /**
     * Пробуем получить несуществующий элемент.
     *
     * @throws Exception исключение.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void tryGetExceccElement() throws Exception {
        SimpleArray<String> simpleArray = new SimpleArray<>(1);

        simpleArray.add("String1");
        simpleArray.get(1);
    }
    /**
     * Пробуем обновить елемент сверх массива.
     *
     * @throws Exception исключение.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void tryUpdateExceccElement() throws Exception {
        SimpleArray<String> simpleArray = new SimpleArray<>(1);

        simpleArray.update(2, "TestString");
    }

}