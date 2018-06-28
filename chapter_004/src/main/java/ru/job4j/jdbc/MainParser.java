package ru.job4j.jdbc;

import java.util.Timer;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class MainParser {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new JobParser(), 0, 20_000);
//        timer.schedule(new JobParser(), 0,24*60*60);
    }
}
