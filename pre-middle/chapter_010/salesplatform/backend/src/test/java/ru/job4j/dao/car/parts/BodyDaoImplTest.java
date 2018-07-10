package ru.job4j.dao.car.parts;

import org.junit.Test;
import ru.job4j.model.car.parts.Body;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class BodyDaoImplTest {

    @Test
    public void findOne() {
        Body body = new BodyDaoImpl().findOne(1L);
        assertThat(body.getType(), is("OTHER"));
    }

    @Test
    public void findAll() {
        assertThat(new BodyDaoImpl().findAll(), hasSize(7));
    }
}