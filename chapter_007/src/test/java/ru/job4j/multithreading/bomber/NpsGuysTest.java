package  ru.job4j.multithreading.bomber;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.multithreading.bomber.console.RandomOutputForTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 21.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class NpsGuysTest {
    private NpsGuys npsGuys;
    private Start start;

    @Before
    public void start() {
        start = new Start(new RandomOutputForTest());
        npsGuys = new NpsGuys(start, Names.Bomber);
    }


    @Test
    public void move() throws Exception {
        Thread t = new Thread(npsGuys);
        t.start();
        Thread.sleep(10);
        t.interrupt();
    }


    /**
     * Тест границ поля.
     */
    @Test
    public void checkBorders() {
       boolean result = npsGuys.checkBorders(21, 5);
       assertFalse(result);
       result = npsGuys.checkBorders(5, 5);
       assertTrue(result);
    }

}