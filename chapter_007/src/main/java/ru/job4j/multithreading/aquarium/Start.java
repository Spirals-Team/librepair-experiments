package ru.job4j.multithreading.aquarium;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * Клас для запуска аквариума.
 * @author Hincu Andrei (andreih1981@gmail.com)on 27.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Start {
   private List<Fish> aquarium = new ArrayList<>();

    public  List<Fish> getAquarium() {
        return aquarium;
    }

    public static void main(String[] args) throws InterruptedException {
        Start start             = new Start();
        FishFactory factory     = new FishFactory(start);
        FishCollector collector = new FishCollector(start);
        Randevu randevu         = new Randevu(start);

        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(factory);
        service.submit(collector);
        service.submit(randevu);
        //до полного высыхания осталось.
        TimeUnit.SECONDS.sleep(30);
        service.shutdownNow();

    }
}
