package ru.job4j.dao.car.parts;

import org.junit.Test;
import ru.job4j.model.car.parts.Manufacture;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ManufactureDaoImplTest {

    @Test
    public void findOne() {
        Manufacture manufacture = new ManufactureDaoImpl().findOne(1L);
        assertThat(manufacture.getName(), is("Other"));
    }

    @Test
    public void findAll() {
        assertThat(new ManufactureDaoImpl().findAll(), hasSize(9));
    }
}