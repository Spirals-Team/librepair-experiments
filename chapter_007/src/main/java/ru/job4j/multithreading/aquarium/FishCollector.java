package ru.job4j.multithreading.aquarium;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Класс описывает сборку умерших рыб и отчет о текущем колличестве рыб в аквариуме.
 * @author Hincu Andrei (andreih1981@gmail.com)on 27.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class FishCollector implements Runnable {
    private Start start;
    private SimpleDateFormat format = new SimpleDateFormat();
    public FishCollector(Start start) {
        this.start = start;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                System.out.println("Аквариум высох.");
                break;
            }
            synchronized (start.getAquarium()) {
                start.getAquarium().removeIf(fish -> !fish.isAlive());
                System.out.println(String.format("Популяция аквариума на момент %s состовляет %d особей", format.format(new GregorianCalendar().getTime()), start.getAquarium().size()));
            }
        }
    }
}
