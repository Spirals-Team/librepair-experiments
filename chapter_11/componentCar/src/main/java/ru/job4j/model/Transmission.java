package ru.job4j.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 12.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class Transmission {
    private static final Logger LOG = LogManager.getLogger(Transmission.class);
    private int id;
    private String type;
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Transmission(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Transmission(int id) {

        this.id = id;
    }

    public Transmission() {

    }

    @Override
    public String toString() {
        return "Transmission{"
                + "id="
                + id
                + ", type='"
                + type
                + '\''
                + ", car="
                + car
                + '}';
    }
}
