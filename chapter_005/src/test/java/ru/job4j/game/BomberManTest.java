package ru.job4j.game;

import org.junit.Ignore;
import org.junit.Test;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BomberManTest {
    @Ignore
    @Test
    public void whenTestBomberMan() throws InterruptedException {
        BomberMan bomberMan = new BomberMan(5, 10, Difficulty.Dif.EASY, Difficulty.Dif.HARD);
        bomberMan.start();
        Thread.sleep(10000);
        BomberMan.stop();
    }

}