package ru.job4j.repository.car.parts;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.BaseTest;
import ru.job4j.model.car.parts.Body;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class BodyRepositoryTest extends BaseTest {

    @Autowired
    private BodyRepository repo;

    @Test
    public void findOne() {
        Body body = repo.findById(1L)
            .orElseThrow(() ->
                new IllegalStateException("Body with id 1L not found"));
        assertThat(body.getType(), is("OTHER"));
    }

    @Test
    public void findAll() {
        assertThat(repo.findAll(), hasSize(7));
    }
}