package ru.job4j.waitnotifynotifyall.examplesp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class NonBlock {


    private volatile boolean blockCustomer = true; // thread stack

    public void doSomething() throws InterruptedException {
        while (this.blockCustomer) {
            System.out.println("usefull work " + Thread.currentThread().getId());
        }

    }

    public void changeBlock(boolean enable) {

        System.out.println("enable " + Thread.currentThread().getId());

        this.blockCustomer = enable;

    }

    //volatile - только одну операцию по соути одно действие к примеру только запись
    private static volatile int count = 0;

    public  static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                /**
                 * 1 read
                 * 2 increment
                 * 3 write
                 */
                count++;
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                count++;
            }
        }.start();

        List<String> syncLList = Collections.synchronizedList(new ArrayList<>()); //медленно т..к. синхрон
        // везеде где синхронайзд в наименоваинее то методы оборочиваются в синхронайз - можно посмотреть в коде
        CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>(); // пробегание по коллекции
        // отличается создает слабую ссылку. делает кописю. если пробегаем, то мы не блокируем всю очередь а просто пр
        //пробегаем по этому клону. но если есть 2 итератлора в разных трэдах то может ситуация что может быть неспрведдливы
        //это метод сайз к примеру

        //быстрее чем полная синронизация при прохождении по карте соз сайз изэмпти - при итерации создает клоны. и карту
        //памяти разбивает на 16 секторов. 16 локов для каждого сегмоента памяти = все это понять нужно
        //целесообразность если нет многопот то медленнее. только в том случае если
//        ConcurrentHashMap


    }
}
