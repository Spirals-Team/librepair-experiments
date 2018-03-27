package  ru.job4j.multithreading.jmm;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 07.11.17;
 * @version $Id$
 * @since 0.1
 */
public class Counter {
    private long count = 0L;
    public void increment() {
        count++;
    }

    public long getCount() {
        return count;
    }
}
