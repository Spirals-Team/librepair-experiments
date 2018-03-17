package ru.job4j.sql;


import ru.job4j.sql.database.DB;
import ru.job4j.sql.items.Advert;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Главный класс с методом майн.
 * @author Hincu Andrei (andreih1981@gmail.com)on 22.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Sql {
    private DB db;
    private ArrayBlockingQueue<Advert> queue;
    public Sql(DB db) {
        this.db = db;
        this.queue = new ArrayBlockingQueue<Advert>(5000);
    }

    /**
     * Главный метод программы раз в сутки запускает потоки сканирования и обработки объявлений.
     * @param args нет.
     */
    public static void main(String[] args) {
        String settingsFile = "settings.properties";
        DB db =   new DB(settingsFile);
        Sql sql = new Sql(db);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        service.scheduleWithFixedDelay(new PageParser(sql.queue, sql.db), 0, 1, TimeUnit.DAYS);
        service.scheduleWithFixedDelay(new AdvertScanner(sql.queue, sql.db), 0, 1, TimeUnit.DAYS);
        service.scheduleWithFixedDelay(new AdvertScanner(sql.queue, sql.db), 0, 1, TimeUnit.DAYS);
        service.scheduleWithFixedDelay(new AdvertScanner(sql.queue, sql.db), 0, 1, TimeUnit.DAYS);
    }
}
