package ru.skorikov;

import org.junit.Test;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 28.12.17
 * @ version: java_kurs_standart
 */
public class UserOverrideEqualsAndHashTest {
    /**
     * Получим хранилище.
     * @throws Exception исключение.
     */
    @Test
    public void getMapUser() throws Exception {
        UserOverrideEqualsAndHash user = new UserOverrideEqualsAndHash();
        user.getMap();
    }

}