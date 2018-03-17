package ru.skorikov;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 20.12.17
 * @ version: java_kurs_standart
 */
public class Start {
    /**
     * Входной файл.
     */
    private File file;
    /**
     * Книга ордеров.
     */
    private OrderBook orderBook = new OrderBook();

    /**
     * Получить файл.
     * @return файл.
     */
    public File getFile() {
        return file;
    }

    /**
     * Задать файл.
     * @param file файл.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Получить книгу ордеров.
     * @return книга.
     */
    public OrderBook getOrderBook() {
        return orderBook;
    }

    /**
     * Запуск приложения.
     *
     * @param args массив строк.
     * @throws IOException исключения.
     */
    public static void main(String[] args) throws IOException {
        //Время начала работы.
        Date dateStart = new Date();

        Start start = new Start();
        File file = new File("/home/insaider/book.xml");
        start.setFile(file);

        //Сортировка и вывод
        start.getOrderBook().work(start.getFile());
        start.getOrderBook().printMap();

        //Время окончания работы.
        Date end = new Date();
        long timeWork = (end.getTime() - dateStart.getTime());
        System.out.printf("Time work :  %s  ms (%s sek)", timeWork, timeWork / 1000);
    }
}
