package ru.job4j.repository.car;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.BaseTest;
import ru.job4j.model.car.Car;
import ru.job4j.model.car.parts.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class CarDaoImplTest extends BaseTest {

    @Autowired
    private CarRepository repo;
    private Car car;

    @Before
    public void setUp() {
        Car newCar = new Car()
            .setBody(new Body().setId(2L))
            .setCategory(new Category().setId(2L))
            .setFuel(new Fuel().setId(2L))
            .setCarModel(new CarModel().setId(2L))
            .setTransmission(new Transmission().setId(2L))
            .setEngineSize(1.2)
            .setMileage(4000L)
            .setYear(2018);
        Long id = repo.save(newCar).getId();
        car = repo.findById(id)
            .orElseThrow(() -> new IllegalStateException("Car not found"));
    }

    @Test
    public void findOne() {
        Car c = repo.findById(car.getId())
            .orElseThrow(() -> new IllegalStateException("Car not found"));
        assertThat(c.getBody().getType(), is("HATCHBACK"));
    }

    @Test
    public void findAll() {
        List<Car> cars = repo.findAll();
        assertThat(cars.size(), greaterThanOrEqualTo(1));
        assertThat(cars, hasItem(car));
    }

//    @Test
//    public void create() {
//        Car newCar = new Car().setBody(new BodyRepository().findOne(2L))
//            .setCategory(new CategoryRepository().findOne(2L))
//            .setFuel(new FuelRepository().findOne(2L))
//            .setCarModel(new CarModelRepository().findOne(2L))
//            .setTransmission(new TransmissionRepository().findOne(2L))
//            .setEngineSize(4.0)
//            .setMileage(30000L)
//            .setYear(2008);
//        Long id = new CarRepository().create(newCar);
//        newCar.setId(id);
//        Car savedCar = new CarRepository().findOne(id);
//        assertThat(savedCar, is(newCar));
//        assertThat(savedCar.getBody().getType(), is("HATCHBACK"));
//    }
//
//    @Test
//    public void update() {
//        Car newCar = new Car().setBody(new BodyRepository().findOne(2L))
//            .setCategory(new CategoryRepository().findOne(2L))
//            .setFuel(new FuelRepository().findOne(2L))
//            .setCarModel(new CarModelRepository().findOne(2L))
//            .setTransmission(new TransmissionRepository().findOne(2L))
//            .setEngineSize(4.0)
//            .setMileage(30000L)
//            .setYear(2008);
//        newCar.setId(new CarRepository().create(newCar));
//        newCar = new CarRepository().findOne(newCar.getId());
//        new CarRepository().update(newCar.setYear(2000));
//        Car savedCar = new CarRepository().findOne(newCar.getId());
//        assertThat(savedCar, is(newCar));
//        assertThat(savedCar.getYear(), is(2000));
//    }
//
//    public void updateAll() {
//    }
//
//    @Test
//    public void delete() {
//        Car newCar = new Car().setBody(new BodyRepository().findOne(2L))
//            .setCategory(new CategoryRepository().findOne(2L))
//            .setFuel(new FuelRepository().findOne(2L))
//            .setCarModel(new CarModelRepository().findOne(2L))
//            .setTransmission(new TransmissionRepository().findOne(2L))
//            .setEngineSize(4.0)
//            .setMileage(30000L)
//            .setYear(2008);
//        newCar.setId(new CarRepository().create(newCar));
//        newCar = new CarRepository().findOne(newCar.getId());
//        new CarRepository().delete(newCar);
//        Car foundCar = new CarRepository().findOne(newCar.getId());
//        assertThat(foundCar, nullValue());
//    }
//
//    @Test
//    public void deleteById() {
//        Car newCar = new Car().setBody(new BodyRepository().findOne(2L))
//            .setCategory(new CategoryRepository().findOne(2L))
//            .setFuel(new FuelRepository().findOne(2L))
//            .setCarModel(new CarModelRepository().findOne(2L))
//            .setTransmission(new TransmissionRepository().findOne(2L))
//            .setEngineSize(4.0)
//            .setMileage(30000L)
//            .setYear(2008);
//        newCar.setId(new CarRepository().create(newCar));
//        newCar = new CarRepository().findOne(newCar.getId());
//        new CarRepository().deleteById(newCar.getId());
//        Car foundCar = new CarRepository().findOne(newCar.getId());
//        assertThat(foundCar, nullValue());
//    }
}