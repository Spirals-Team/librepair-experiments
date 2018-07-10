package ru.job4j.model.car.parts;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class represent CarModel.
 */
public class CarModel {

    private Long id;
    private String name;
    private Manufacture manufacture;

    public CarModel() {
    }

    public CarModel(final String name, final Manufacture manufacture) {
        this.name = name;
        this.manufacture = manufacture;
    }

    public CarModel(final Long id, final String name,
                    final Manufacture manufacture) {
        this.id = id;
        this.name = name;
        this.manufacture = manufacture;
    }

    public Long getId() {
        return id;
    }

    public CarModel setId(final Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CarModel setName(final String name) {
        this.name = name;
        return this;
    }

    public Manufacture getManufacture() {
        return manufacture;
    }

    public CarModel setManufacture(final Manufacture manufacture) {
        this.manufacture = manufacture;
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
        CarModel that = (CarModel) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.name, that.name)
            && Objects.equals(this.manufacture, that.manufacture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, manufacture);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("name = " + name)
            .add("manufacture = " + manufacture)
            .toString();
    }
}
