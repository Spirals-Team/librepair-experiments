package ru.job4j.jdbc;

import java.util.TimerTask;
/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class JobParser extends TimerTask {

    @Override
    public void run() {
        new Parser();
    }
}
