package ru.job4j.model;

import ru.job4j.model.car.parts.*;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class represent dictionary data of UI controls.
 */
public class Metadata {

    private List<CarModel> carmodels;
    private List<Manufacture> manufactures;
    private List<Category> categories;
    private List<Body> bodies;
    private List<Fuel> fuels;
    private List<Transmission> transmissions;

    public Metadata() {
    }

    public Metadata(final List<CarModel> carmodels, final List<Manufacture> manufactures,
                    final List<Category> categories, final List<Body> bodies, final List<Fuel> fuels,
                    final List<Transmission> transmissions) {
        this.carmodels = carmodels;
        this.manufactures = manufactures;
        this.categories = categories;
        this.bodies = bodies;
        this.fuels = fuels;
        this.transmissions = transmissions;
    }

    public List<CarModel> getCarmodels() {
        return carmodels;
    }

    public Metadata setCarmodels(final List<CarModel> carmodels) {
        this.carmodels = carmodels;
        return this;
    }

    public List<Manufacture> getManufactures() {
        return manufactures;
    }

    public Metadata setManufactures(final List<Manufacture> manufactures) {
        this.manufactures = manufactures;
        return this;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Metadata setCategories(final List<Category> categories) {
        this.categories = categories;
        return this;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public Metadata setBodies(final List<Body> bodies) {
        this.bodies = bodies;
        return this;
    }

    public List<Fuel> getFuels() {
        return fuels;
    }

    public Metadata setFuels(final List<Fuel> fuels) {
        this.fuels = fuels;
        return this;
    }

    public List<Transmission> getTransmissions() {
        return transmissions;
    }

    public Metadata setTransmissions(final List<Transmission> transmissions) {
        this.transmissions = transmissions;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Metadata that = (Metadata) o;
        return Objects.equals(this.carmodels, that.carmodels)
            && Objects.equals(this.manufactures, that.manufactures)
            && Objects.equals(this.categories, that.categories)
            && Objects.equals(this.bodies, that.bodies)
            && Objects.equals(this.fuels, that.fuels)
            && Objects.equals(this.transmissions, that.transmissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            carmodels, manufactures, categories, bodies, fuels, transmissions);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("carmodels = " + carmodels)
            .add("manufactures = " + manufactures)
            .add("categories = " + categories)
            .add("bodies = " + bodies)
            .add("fuels = " + fuels)
            .add("transmissions = " + transmissions)
            .toString();
    }
}
