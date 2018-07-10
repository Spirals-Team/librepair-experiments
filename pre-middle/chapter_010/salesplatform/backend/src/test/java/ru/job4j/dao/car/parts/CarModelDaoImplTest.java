package ru.job4j.dao.car.parts;

import org.junit.Test;
import ru.job4j.model.car.parts.CarModel;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CarModelDaoImplTest {

    @Test
    public void findOne() {
        CarModel carModel = new CarModelDaoImpl().findOne(1L);
        assertThat(carModel.getName(), is("Other"));
        assertThat(carModel.getManufacture().getName(), is("Other"));
    }

    @Test
    public void findAll() {
        assertThat(new CarModelDaoImpl().findAll(), hasSize(25));
    }
}