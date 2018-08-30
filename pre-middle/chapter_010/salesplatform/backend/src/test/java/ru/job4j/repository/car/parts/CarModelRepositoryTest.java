package ru.job4j.repository.car.parts;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.BaseTest;
import ru.job4j.model.car.parts.CarModel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class CarModelRepositoryTest extends BaseTest {

    @Autowired
    private CarModelRepository repo;

    @Test
    public void findOne() {
        CarModel model = repo.findById(1L)
            .orElseThrow(() ->
                new IllegalStateException("CarModel with id 1L not found"));
        assertThat(model.getName(), is("Other"));
        assertThat(model.getManufacture().getName(), is("Other"));
    }

    @Test
    public void findAll() {
        assertThat(repo.findAll(), hasSize(25));
    }
}