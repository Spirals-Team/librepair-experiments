package ru.skorikov;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 08.01.18
 * @ version: java_kurs_standart
 */
public class CountTest {

    /**
     * Класс описывает нить со счетчиком.
     */
    private class ThreadCount extends Thread {
        /**
         * Поле - класс Count.
         *
         */
        private final Count count;

        /**
         * Конструктор.
         *
         * @param count count.
         */
        private ThreadCount(final Count count) {
            this.count = count;
        }

        @Override
        public void run() {
            this.count.increment();
        }
    }

    /**
     * Тест приложения.
     *
     * @throws InterruptedException exception.
     */
    @Test
    public void whenExecute2ThreadThen2() throws InterruptedException {
        //Создаем счетчик.
        final Count count = new Count();
        //Создаем нити.
        Thread first = new ThreadCount(count);
        Thread second = new ThreadCount(count);
        //Запускаем нити.
        first.start();
        second.start();
        //Заставляем главную нить дождаться выполнения наших нитей.
        first.join();
        second.join();
        //Проверяем результат.
        assertThat(count.get(), is(2));

    }
}
