package ru.job4j.model.car;

import ru.job4j.model.Persistent;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 19.03.2018.
 * @version $Id$.
 * @since 0.1.
 */

@Entity
@Table(name = "engine")
public class Engine extends Persistent {
    private static final long serialVersionUID = 3182545441241279870L;

    private int volume;
    private String fuelType;
    private int power;

    public Engine() {
        super();
    }
    public Engine(Long id) {
        super(id);
    }

    @OneToMany(mappedBy = "engine", fetch = FetchType.LAZY)
    private List<Car> car = new ArrayList<>();

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public List<Car> getCar() {
        return car;
    }

    public void setCar(List<Car> car) {
        this.car = car;
    }
}
