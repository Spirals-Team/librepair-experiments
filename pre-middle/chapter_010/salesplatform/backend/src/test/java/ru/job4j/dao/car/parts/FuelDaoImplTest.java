package ru.job4j.dao.car.parts;

import org.junit.Test;
import ru.job4j.model.car.parts.Fuel;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FuelDaoImplTest {

    @Test
    public void findOne() {
        Fuel fuel = new FuelDaoImpl().findOne(1L);
        assertThat(fuel.getType(), is("OTHER"));
    }

    @Test
    public void findAll() {
        assertThat(new FuelDaoImpl().findAll(), hasSize(6));
    }
}