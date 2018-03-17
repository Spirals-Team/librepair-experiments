package ru.job4j.multithreading.aquarium;

import java.util.concurrent.TimeUnit;

/**
 * Роддом рыбок.
 * @author Hincu Andrei (andreih1981@gmail.com)on 27.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class FishFactory implements Runnable {
    public FishFactory(Start start) {
        this.start = start;
    }

    private Start start;

    @Override
    public void run() {
        //создаем двух рыб противоположенного пола.
        Fish fishM = new Fish("M");
        Fish fishF = new Fish("F");
        synchronized (start.getAquarium()) {
            start.getAquarium().add(fishF);
            start.getAquarium().add(fishM);
        }
        new Thread(fishF).start();
        new Thread(fishM).start();
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("Аквариум высох.");
                break;
            }
            //рождение новой рыбки.
            Fish f = new Fish();
            synchronized (start.getAquarium()) {
                start.getAquarium().add(f);
                new Thread(f).start();
            }
        }

    }
}
