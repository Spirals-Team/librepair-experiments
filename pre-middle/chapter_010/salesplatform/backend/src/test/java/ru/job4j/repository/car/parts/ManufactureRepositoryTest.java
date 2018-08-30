package ru.job4j.repository.car.parts;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.BaseTest;
import ru.job4j.model.car.parts.Manufacture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ManufactureRepositoryTest extends BaseTest {

    @Autowired
    private ManufactureRepository repo;

    @Test
    public void findOne() {
        Manufacture manufacture = repo.findById(1L)
            .orElseThrow(() -> new IllegalStateException("CarModel not found"));
        assertThat(manufacture.getName(), is("Other"));
    }

    @Test
    public void findAll() {
        assertThat(repo.findAll(), hasSize(9));
    }

}