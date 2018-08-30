package ru.job4j.repository.car.parts;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.BaseTest;
import ru.job4j.model.car.parts.Transmission;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class TransmissionRepositoryTest extends BaseTest {

    @Autowired
    private TransmissionRepository repo;

    @Test
    public void findOne() {
        Transmission transsmission = repo.findById(1L)
            .orElseThrow(() -> new IllegalStateException("CarModel not found"));
        assertThat(transsmission.getType(), is("OTHER"));
    }

    @Test
    public void findAll() {
        assertThat(repo.findAll(), hasSize(4));
    }
}