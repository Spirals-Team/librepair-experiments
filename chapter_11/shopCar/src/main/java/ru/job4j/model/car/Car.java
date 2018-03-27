package ru.job4j.model.car;

import ru.job4j.model.Persistent;

import javax.persistence.*;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 18.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
@Entity
@Table(name = "cars")
public class Car extends Persistent {

    private static final long serialVersionUID = 785710110674611001L;

    /**
     * Пройденный путь
     */
    private int milage;
    /**
     * Год выпуска
     */
    private int year;

    public Car() {
        super();
    }
    public Car(Long id) {
        super(id);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_engine")
    private Engine engine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_brand")
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_mobel")
    private Model model;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_carcass")
    private Carcass carcass;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gearbox")
    private Gearbox gearbox;


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getMilage() {
        return milage;
    }

    public void setMilage(int milage) {
        this.milage = milage;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Carcass getCarcass() {
        return carcass;
    }

    public void setCarcass(Carcass carcass) {
        this.carcass = carcass;
    }

    public Gearbox getGearbox() {
        return gearbox;
    }

    public void setGearbox(Gearbox gearbox) {
        this.gearbox = gearbox;
    }
}

