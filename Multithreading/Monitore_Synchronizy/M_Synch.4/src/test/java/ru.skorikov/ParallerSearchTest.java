package ru.skorikov;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 19.01.18
 * @ version: java_kurs_standart
 */
public class ParallerSearchTest {
    /**
     * Проверим что найдем файл.
     *
     * @throws Exception исключение.
     */
    @Test
    public void whenRunThenReturnListFilePaph() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(".txt");
        list.add(".xml");
        String root = new File("/home/insaider/Coding/projects/java_kurs_standart/Multithreading/Monitore_Synchronizy/M_Synch.4").getAbsolutePath();
        String text = "Салтан";
        ParallerSearch parallerSearch = new ParallerSearch(root, text, list);
        String testStr =
        "/home/insaider/Coding/projects/java_kurs_standart/Multithreading/Monitore_Synchronizy/M_Synch.4/Skazka1.txt";

        Assert.assertThat(parallerSearch.result().get(0), is(testStr));
    }
}