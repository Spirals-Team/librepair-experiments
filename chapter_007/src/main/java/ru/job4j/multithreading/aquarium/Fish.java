package ru.job4j.multithreading.aquarium;

import java.util.concurrent.TimeUnit;

/**
 * Класс описывает рождение и жизнь рыбки в аквариуме.
 * @author Hincu Andrei (andreih1981@gmail.com)on 27.11.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Fish implements Runnable {
    /**
     * СЧетчик колличества экземпляров классов.
     */
    private static int count = 1;
    /**
     * Номер рыбки.
     */
    private int number;
    /**
     * Имя рыбки.
     */
    private String name;
    /**
     * Время жизни рыбки.
     */
    private int lifeTime;
    /**
     * Пол рыбки.
     */
    private Sex sex;
    /**
     * Живая или мертвая.
     */
    private boolean isAlive;


    /**
     * Создаем рыбу случайного пола.
     */
    public Fish() {
        this.number = count++;
        this.name = String.format("№ %d", this.number);
        this.lifeTime = (int) (10 + Math.random() * 15);
        this.isAlive = true;
        if (((int) (Math.random() * 2)) == 1) {
            this.sex = Sex.M;
        } else {
            this.sex = Sex.F;
        }
    }

    /**
     * Создаем рыбу заданного пола.
     * @param sex М или F.
     */
    public Fish(String sex) {
        this.number = count++;
        this.name = String.format("№ %d", this.number);
        this.lifeTime = (int) (10 + Math.random() * 15);
        this.isAlive = true;
        if (sex.equalsIgnoreCase("M")) {
            this.sex = Sex.M;
        } else {
            this.sex = Sex.F;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void run() {
        System.out.println(String.format("Родилась рыбка %s", this.name));
        try {
            TimeUnit.SECONDS.sleep(lifeTime);
        } catch (InterruptedException e) {
            System.out.println("Время работы аквариума вышло.");
        }
        System.out.println(String.format("Умерла рыбка %s", this.name));
        this.isAlive = false;

    }

    public Sex getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }
}
 enum Sex {
    M,
    F
}