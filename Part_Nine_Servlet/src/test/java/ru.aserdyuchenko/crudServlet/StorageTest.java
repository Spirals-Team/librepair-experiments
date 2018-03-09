package ru.aserdyuchenko.crudServlet;

import org.junit.Test;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class StorageTest {
    @Test
    public void whenAddInStorageAndGetResult() throws SQLException, ClassNotFoundException {
        Storage storage = new Storage();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        storage.add("anton415", "Anton Serdyuchenko", "anton415@gmail.com", df.format(dateobj));
        System.out.println(storage.get());
    }

    @Test
    public void whenUpdateInStorageAndGetResult() throws SQLException, ClassNotFoundException {
        Storage storage = new Storage();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        storage.update("Usov S.", "stepan415@gmail.com", "29.10.2017 19:32", "stepan415");
        System.out.println(storage.get());
    }
}