package ru.job4j.bankomat;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 10.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class AvtomatTest {
    /**
     * апарат по выдаче сдачи.
     */
    private Avtomat a;

    /**
     * инициализвция.
     */
    @Before
    public void setUp() {
        a = new Avtomat();
    }

    /**
     * Проверяем сдачу.
     */
    @Test
    public void changes() {
        int[] change = a.changes(50, 35);
        assertThat(change, is(new int[]{10, 5}));
    }

}