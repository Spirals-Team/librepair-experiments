package ru.job4j.dao.car;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dao.car.parts.*;
import ru.job4j.model.car.Car;
import ru.job4j.model.car.parts.CarModel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CarDaoImplTest {

    @Before
    public void setUp() {
    }

    @Test
    public void findOne() {
        Car car = new CarDaoImpl().findOne(1L);
        assertThat(car.getBody().getType(), is("HATCHBACK"));
    }

    @Test
    public void findAll() {
        assertThat(new CarDaoImpl().findAll().size(), greaterThanOrEqualTo(1));
    }

    @Test
    public void create() {
        Car car = new Car().setBody(new BodyDaoImpl().findOne(2L))
            .setCategory(new CategoryDaoImpl().findOne(2L))
            .setFuel(new FuelDaoImpl().findOne(2L))
            .setCarModel(new CarModelDaoImpl().findOne(2L))
            .setTransmission(new TransmissionDaoImpl().findOne(2L))
            .setEngineSize(4.0)
            .setMileage(30000L)
            .setYear(2008);
        Long id = new CarDaoImpl().create(car);
        car.setId(id);
        Car savedCar = new CarDaoImpl().findOne(id);
        assertThat(savedCar, is(car));
    }

    @Test
    public void update() {
        Car car = new CarDaoImpl().findOne(2L);
        CarModel oldModel = car.getCarModel();
        car.setCarModel(new CarModelDaoImpl().findOne(5L));
        new CarDaoImpl().update(car);
        Car updatedCar = new CarDaoImpl().findOne(1L);
        assertThat(oldModel, not(updatedCar.getCarModel()));
    }

    public void updateAll() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void deleteById() {
    }
}