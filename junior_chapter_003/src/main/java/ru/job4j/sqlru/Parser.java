package ru.job4j.sqlru;

import ru.job4j.sqlru.bd.ConnectionSQL;
import ru.job4j.sqlru.bd.DataBase;
import ru.job4j.sqlru.items.Url;
import ru.job4j.sqlru.setting.LoadSetting;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Parser {
    public static void main(String[] args) {
        ArrayBlockingQueue<Url> urlQueue = new ArrayBlockingQueue<>(5000);
        LoadSetting loadSetting = new LoadSetting();
        loadSetting.load();
        ConnectionSQL connectionSQL = new ConnectionSQL(loadSetting.url, loadSetting.user, loadSetting.password);
        DataBase dataBase = new DataBase(connectionSQL.getConnection());
        ScheduledExecutorService service = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        service.scheduleWithFixedDelay(new PageScanner(urlQueue), 0, 1, TimeUnit.DAYS);
        service.scheduleWithFixedDelay(new AdvertScanner(urlQueue, dataBase, loadSetting), 0, 1, TimeUnit.DAYS);
        service.scheduleWithFixedDelay(new AdvertScanner(urlQueue, dataBase, loadSetting), 0, 1, TimeUnit.DAYS);
        service.scheduleWithFixedDelay(new AdvertScanner(urlQueue, dataBase, loadSetting), 0, 1, TimeUnit.DAYS);
        service.scheduleWithFixedDelay(new AdvertScanner(urlQueue, dataBase, loadSetting), 0, 1, TimeUnit.DAYS);
    }
}
