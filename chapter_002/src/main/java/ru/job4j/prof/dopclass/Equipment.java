package ru.job4j.prof.dopclass;

/**
 *материнский класс для всего оборудования
 */
public class Equipment {
    private String model;
    private int garantia;
    private int lifetime;
    private Rabsost rabsost;

    Equipment(String model, int garantia, int lifetime, Rabsost rabsost) {
        this.model = model;
        this.garantia = garantia;
        this.lifetime = lifetime;
        this.rabsost = rabsost;
    }

    public Equipment() {
    }

    public String getModel() {
        return this.model;
    }

    public int getGarantia() {
        return garantia;
    }

    public int getLifetime() {
        return lifetime;
    }

    public Rabsost getRabsost() {
        return this.rabsost;
    }

    public void setRabsost(Rabsost rabsost) {
            this.rabsost = rabsost;
    }
}
