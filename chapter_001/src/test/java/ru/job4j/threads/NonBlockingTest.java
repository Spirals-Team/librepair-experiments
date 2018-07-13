package ru.job4j.threads;

import org.junit.Test;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Класс тестирует поведение неблокирующегося кеша
 */
public class NonBlockingTest {
    NonBlocking cc = new NonBlocking();

    @Test
    /**
     * Тест основных функция неблокирующегося кеша
     */
    public void testCache() {
        cc.add(cc. new Model(3654,"NameA"));
        cc.add(cc. new Model(5588,"NameB"));
        cc.add(cc. new Model(8743,"NameC"));
        cc.add(cc. new Model(1276,"NameD"));
        assertThat(cc.findById(8743).getName(), is("NameC"));
        cc.delete(cc.findById(5588));
        assertThat(cc.findById(5588), is(nullValue()));
        cc.update(cc. new Model(1276,"NameE"));
        assertThat(cc.findById(1276).getName(), is("NameE"));
    }

    /**
     * Тест возникновения ошибки при несоответствии версий
     */
    @Test (expected = OptimisticException.class)
    public void testException() {
        cc.add(cc. new Model(3654,"NameA"));
        cc.add(cc. new Model(5588,"NameB"));
        cc.add(cc. new Model(8743,"NameC"));
        cc.add(cc. new Model(1276,"NameD"));
        cc.update(cc. new Model(1276,"NameE"));
        cc.update(cc. new Model(1276,"NameF"));
    }
}