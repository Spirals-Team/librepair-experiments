package ru.job4j.repository.car.parts;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.BaseTest;
import ru.job4j.model.car.parts.Category;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class CategoryRepositoryTest extends BaseTest {

    @Autowired
    private CategoryRepository repo;

    @Test
    public void findOne() {
        Category category = repo.findById(1L)
            .orElseThrow(() -> new IllegalStateException("Category not found"));
        assertThat(category.getType(), is("OTHER"));
    }

    @Test
    public void findAll() {
        assertThat(repo.findAll(), hasSize(9));
    }
}