package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 26.02.18
 * @ version: java_kurs_standart
 */
public class MyNotBlockClassTest {

    /**
     * Пробуем добавить данные.
     */
    @Test
    public void tryAddData() {
        MyNotBlockClass myNotBlockClass = new MyNotBlockClass();
        myNotBlockClass.add(new Model("name", "data"));

        Model model1 = myNotBlockClass.getModel(0);

        Assert.assertEquals(model1.getData(), "data");
    }

    /**
     * Пробуем обновть данные.
     *
     */
    @Test
    public void whenUpdateDataThenUpdateModel() {
        MyNotBlockClass myNotBlockClass = new MyNotBlockClass();
        Model model = new Model("name", "data");
        myNotBlockClass.add(model);

        model.setName("name2");
        model.setData("data2");
        myNotBlockClass.update(0, model);
        Assert.assertEquals(model.getName(), "name2");
        Assert.assertEquals(model.getData(), "data2");
    }

    /**
     * Пробуем двумя пользователями обновить данные.
     */
    @Test
    public void tryGetException() {

        MyNotBlockClass myNotBlockClass = new MyNotBlockClass();
        Model model = new Model("name", "data");
        myNotBlockClass.add(model);

        Model user1 = myNotBlockClass.getModel(0);
        Model user2 = myNotBlockClass.getModel(0);

        user1.setName("nameUser1");
        myNotBlockClass.update(0, user1);
        user2.setName("nameUser2");
        myNotBlockClass.update(0, user2);
    }

    /**
     * Пробуем удалить данные.
     */
    @Test(expected = NoSuchElementException.class)
    public void whenDeleteModelThenDelete() {
        MyNotBlockClass myNotBlockClass = new MyNotBlockClass();
        Model model = new Model("name", "data");
        myNotBlockClass.add(model);

        myNotBlockClass.delete(model);

        myNotBlockClass.getModel(0);
    }

    /**
     * Пробуем добавить одинаковые модели.
     */
    @Test(expected = NoSuchElementException.class)
    public void whenAddTwhoModelThenAddOne() {
        MyNotBlockClass myNotBlockClass = new MyNotBlockClass();
        Model model = new Model("name", "data");
        myNotBlockClass.add(model);
        myNotBlockClass.add(model);

        myNotBlockClass.getModel(1);
    }
}