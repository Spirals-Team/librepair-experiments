package ru.job4j.carsale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.models.Advert;
import ru.job4j.models.Brand;
import ru.job4j.models.Model;
import ru.job4j.models.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DefaultValueTest {
    private final CarStorage carStorage = CarStorage.INSTANCE;
    DefaultValue defaultValue = new DefaultValue();

    @Before
    public void begin() {
        this.carStorage.start();
    }

    @After
    public void end() {
        this.carStorage.finish();
    }

    @Test
    public void whereAddBrand() {
        this.defaultValue.addBrand();
        assertTrue(this.carStorage.getList(Brand.class.getSimpleName()).size() > 0);

    }

    @Test
    public void whereAddModel() {
        this.defaultValue.addBrand();
        this.defaultValue.addModel();
        assertTrue(this.carStorage.getList(Model.class.getSimpleName()).size() > 0);
    }

    @Test
    public void whereAddRoot() {
        this.defaultValue.addRoot();
        assertThat(this.carStorage.getList(User.class.getSimpleName()).size(), is(1));
        assertThat(((User) this.carStorage.getList(User.class.getSimpleName()).get(0)).getLogin(), is("root"));
    }
}