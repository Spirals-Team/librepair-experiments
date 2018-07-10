package ru.job4j.dao.car.parts;

import org.junit.Test;
import ru.job4j.model.car.parts.Category;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class CategoryDaoImplTest {

    @Test
    public void findOne() {
        Category category = new CategoryDaoImpl().findOne(1L);
        assertThat(category.getType(), is("OTHER"));
    }

    @Test
    public void findAll() {
        assertThat(new CategoryDaoImpl().findAll(), hasSize(9));
    }
}