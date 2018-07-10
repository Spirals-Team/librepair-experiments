package ru.job4j.model.car;

import ru.job4j.model.car.parts.*;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Class Car carModel.
 */
public class Car {

    private Long id;
    private Body body;
    private Category category;
    private Fuel fuel;
    private CarModel carModel;
    private Transmission transmission;
    private Double engineSize;
    private Long mileage;
    private Integer year;

    public Car() {
    }

    public Car(final Long id) {
        this.id = id;
    }

    public Car(final Body body, final Category category, final Fuel fuel,
               final CarModel carModel, final Transmission transmission,
               final Double engineSize, final Long mileage, final Integer year) {
        this.body = body;
        this.category = category;
        this.fuel = fuel;
        this.carModel = carModel;
        this.transmission = transmission;
        this.engineSize = engineSize;
        this.mileage = mileage;
        this.year = year;
    }

    public Car(final Long id, final Body body, final Category category,
               final Fuel fuel, final CarModel carModel, final Transmission transmission,
               final Double engineSize, final Long mileage, final Integer year) {
        this.id = id;
        this.body = body;
        this.category = category;
        this.fuel = fuel;
        this.carModel = carModel;
        this.transmission = transmission;
        this.engineSize = engineSize;
        this.mileage = mileage;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public Car setId(final Long id) {
        this.id = id;
        return this;
    }

    public Body getBody() {
        return body;
    }

    public Car setBody(final Body body) {
        this.body = body;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Car setCategory(final Category category) {
        this.category = category;
        return this;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public Car setFuel(final Fuel fuel) {
        this.fuel = fuel;
        return this;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public Car setCarModel(final CarModel carModel) {
        this.carModel = carModel;
        return this;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public Car setTransmission(final Transmission transmission) {
        this.transmission = transmission;
        return this;
    }

    public Double getEngineSize() {
        return engineSize;
    }

    public Car setEngineSize(final Double engineSize) {
        this.engineSize = engineSize;
        return this;
    }

    public Long getMileage() {
        return mileage;
    }

    public Car setMileage(final Long mileage) {
        this.mileage = mileage;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public Car setYear(final Integer year) {
        this.year = year;
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
        Car that = (Car) o;
        return Objects.equals(this.id, that.id)
            && Objects.equals(this.body, that.body)
            && Objects.equals(this.category, that.category)
            && Objects.equals(this.fuel, that.fuel)
            && Objects.equals(this.carModel, that.carModel)
            && Objects.equals(this.transmission, that.transmission)
            && Objects.equals(this.engineSize, that.engineSize)
            && Objects.equals(this.mileage, that.mileage)
            && Objects.equals(this.year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, category, fuel, carModel, transmission,
            engineSize, mileage, year);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
            .add("id = " + id)
            .add("body = " + body)
            .add("category = " + category)
            .add("fuel = " + fuel)
            .add("carModel = " + carModel)
            .add("transmission = " + transmission)
            .add("engineSize = " + engineSize)
            .add("mileage = " + mileage)
            .add("year = " + year)
            .toString();
    }
}
