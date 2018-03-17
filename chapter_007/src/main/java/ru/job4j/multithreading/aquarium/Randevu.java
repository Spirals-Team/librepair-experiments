package ru.job4j.multithreading.aquarium;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Класс описывает случайные встречи рыбок одинаковых полов.
 * @author Hincu Andrei (andreih1981@gmail.com)on 27.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Randevu implements Runnable {
    private Start start;

    public Randevu(Start start) {
        this.start = start;
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("Время работы аквариума вышло.");
                break;
            }
            synchronized (start.getAquarium()) {
                List<Fish> f = start.getAquarium();
                int one = (int) (Math.random() * f.size());
                int two = (int) (Math.random() * f.size());
                if (one != two) {
                    if (f.get(one).getSex().equals(f.get(two).getSex())) {
                        System.out.println(String.format("Встретились рыбки %s и %s", f.get(one).getName(), f.get(two).getName()));
                    }
                }
            }
        }
    }
}
