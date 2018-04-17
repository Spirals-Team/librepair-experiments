package ru.job4j.threads;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class CountWithTime {
    private CountChar counter;
    private Time time;

    public CountWithTime(String path, long period) {
        this.counter = new CountChar(path);
        this.time = new Time(period);
    }

    public void go() {
        while (counter.getThread().isAlive()) {
            if (!time.getThread().isAlive()) {
                counter.getThread().interrupt();
            }
        }
        System.out.println("Главные поток - ЗАВЕРШЕНИЕ");
    }
}
