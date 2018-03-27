package ru.job4j.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 12.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Engine {
    private static final Logger LOG = LogManager.getLogger(Engine.class);
    private int id;
    private String fuel;
    private int volume;
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Engine(String fuel, int volume) {
        this.fuel = fuel;
        this.volume = volume;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Engine() {

    }

    public Engine(int id) {

        this.id = id;
    }

    @Override
    public String toString() {
        return "Engine{"
                + "id="
                + id
                + ", fuel='"
                + fuel
                + '\''
                + ", volume="
                + volume
                + ", car="
                + car
                + '}';
    }
}
