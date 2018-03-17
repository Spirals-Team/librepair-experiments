package ru.job4j.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 12.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Car {
    private static final Logger LOG = LogManager.getLogger(Car.class);
    private int id;
    private String name;
    private Engine engine;
    private Transmission transmission;
    private Gearbox gearbox;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Gearbox getGearbox() {
        return gearbox;
    }

    public void setGearbox(Gearbox gearbox) {
        this.gearbox = gearbox;
    }

    public Car() {

    }

    @Override
    public String toString() {
        return "Car{"
                + "id="
                + id
                + ", engine="
                + engine
                + ", transmission="
                + transmission
                + ", gearbox="
                + gearbox
                + '}';
    }
}
