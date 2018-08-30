package ru.job4j.repository.car.parts;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.BaseTest;
import ru.job4j.model.car.parts.Fuel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class FuelRepositoryTest extends BaseTest {

    @Autowired
    private FuelRepository repo;

    @Test
    public void findOne() {
        Fuel fuel = repo.findById(1L)
            .orElseThrow(() -> new IllegalStateException("Fuel not found"));
        assertThat(fuel.getType(), is("OTHER"));
    }

    @Test
    public void findAll() {
        assertThat(repo.findAll(), hasSize(6));
    }
}