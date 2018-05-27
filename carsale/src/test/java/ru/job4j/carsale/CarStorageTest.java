package ru.job4j.carsale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.models.Advert;
import ru.job4j.models.Brand;
import ru.job4j.models.Model;
import ru.job4j.models.User;

import java.sql.Timestamp;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CarStorageTest {
    private final CarStorage carStorage = CarStorage.INSTANCE;

    @Before
    public void begin() {
        this.carStorage.start();
    }

    @After
    public void end() {
        this.carStorage.finish();
    }

    @Test
    public void whereAddAdvert() {
        Advert advert = new Advert();
        advert.setName("Test");
        this.carStorage.addObject(advert);
        assertThat(advert.getId(), is(((Advert) carStorage.getList(Advert.class.getSimpleName()).get(0)).getId()));
    }

    @Test
    public void whereAddBrand() {
        Brand brand = new Brand();
        brand.setName("Test");
        this.carStorage.addObject(brand);
        assertThat(brand.getId(), is(((Brand) carStorage.getList(Brand.class.getSimpleName()).get(0)).getId()));
    }

    @Test
    public void whereAddModel() {
        Model model = new Model();
        model.setName("Test");
        this.carStorage.addObject(model);
        assertThat(model.getId(), is(((Model) carStorage.getList(Model.class.getSimpleName()).get(0)).getId()));
    }

    @Test
    public void whereAddUser() {
        User user = new User();
        user.setLogin("Test");
        this.carStorage.addObject(user);
        assertThat(user.getId(), is(((User) carStorage.getList(User.class.getSimpleName()).get(0)).getId()));
    }

    @Test
    public void whereDelAdvert() {
        Advert advert = new Advert();
        advert.setName("Test");
        this.carStorage.addObject(advert);
        this.carStorage.delObject(advert);
        assertThat(this.carStorage.getList(Advert.class.getSimpleName()).size(), is(0));
    }

    @Test
    public void whereDelBrand() {
        Brand brand = new Brand();
        brand.setName("Test");
        this.carStorage.addObject(brand);
        this.carStorage.delObject(brand);
        assertThat(this.carStorage.getList(Brand.class.getSimpleName()).size(), is(0));
    }

    @Test
    public void whereDelModel() {
        Model model = new Model();
        model.setName("Test");
        this.carStorage.addObject(model);
        this.carStorage.delObject(model);
        assertThat(this.carStorage.getList(Model.class.getSimpleName()).size(), is(0));
    }

    @Test
    public void whereDelUser() {
        User user = new User();
        user.setLogin("Test");
        this.carStorage.addObject(user);
        this.carStorage.delObject(user);
        assertThat(this.carStorage.getList(User.class.getSimpleName()).size(), is(0));
    }

    @Test
    public void whereGetActivAdvert() {
        Advert advert = new Advert();
        advert.setName("Test1");
        advert.setStatus(false);
        Advert advert2 = new Advert();
        advert2.setName("Test2");
        advert2.setStatus(true);
        this.carStorage.addObject(advert);
        this.carStorage.addObject(advert2);
        int exept = advert2.getId();
        assertThat(this.carStorage.getActivAdvert().get(0).getId(), is(exept));

    }

    @Test
    public void whereGetAdvertDay() {
        Advert advert = new Advert();
        advert.setName("Test1");
        advert.setTime(new Timestamp(System.currentTimeMillis() - 87400000));
        Advert advert2 = new Advert();
        advert2.setName("Test2");
        advert2.setTime(new Timestamp(System.currentTimeMillis()));
        this.carStorage.addObject(advert);
        this.carStorage.addObject(advert2);
        int exept = advert2.getId();
        assertThat(this.carStorage.getAdvertDay().get(0).getId(), is(exept));
    }

    @Test
    public void whereGetAdvertBrand() {
        Brand brand = new Brand();
        brand.setName("TestBrand");
        this.carStorage.addObject(brand);
        int idBrand = brand.getId();
        Advert advert = new Advert();
        advert.setName("Test1");
        advert.setIdBrand(idBrand);
        Advert advert2 = new Advert();
        advert2.setName("Test2");
        advert2.setIdBrand(idBrand);
        Advert advert3 = new Advert();
        advert3.setName("Test3");
        this.carStorage.addObject(advert);
        this.carStorage.addObject(advert2);
        this.carStorage.addObject(advert3);
        assertThat(this.carStorage.getAdvertBrand(idBrand).size(), is(2));
    }

    @Test
    public void whereGetBrandID() {
        Brand brand = new Brand();
        brand.setName("Test");
        Brand brand2 = new Brand();
        brand2.setName("Test2");
        this.carStorage.addObject(brand);
        this.carStorage.addObject(brand2);
        int id = brand2.getId();
        assertThat(this.carStorage.getBrand(id), is(brand2));
    }

    @Test
    public void whereGetBrandName() {
        Brand brand = new Brand();
        brand.setName("Test");
        Brand brand2 = new Brand();
        brand2.setName("Test2");
        this.carStorage.addObject(brand);
        this.carStorage.addObject(brand2);
        String name = brand2.getName();
        assertThat(this.carStorage.getBrand(name), is(brand2));
    }

    @Test
    public void whereGetUser() {
        User user = new User();
        user.setLogin("Test");
        User user2 = new User();
        user2.setLogin("Test2");
        this.carStorage.addObject(user);
        this.carStorage.addObject(user2);
        int id = user2.getId();
        assertThat(this.carStorage.getUser(id), is(user2));
    }

    @Test
    public void whereGetModel() {
        Model model = new Model();
        model.setName("Test");
        Model model2 = new Model();
        model2.setName("Test2");
        this.carStorage.addObject(model);
        this.carStorage.addObject(model2);
        int id = model2.getId();
        assertThat(this.carStorage.getModel(id), is(model2));
    }

    @Test
    public void whereGetAdvert() {
        Advert advert = new Advert();
        advert.setName("Test");
        Advert advert2 = new Advert();
        advert2.setName("Test2");
        this.carStorage.addObject(advert);
        this.carStorage.addObject(advert2);
        int id = advert2.getId();
        assertThat(this.carStorage.getAdvert(id), is(advert2));
    }

    @Test
    public void whereGetModels() {
        Brand brand = new Brand();
        brand.setName("TestBrand");
        carStorage.addObject(brand);
        int id = brand.getId();
        Model model = new Model();
        model.setName("Test");
        model.setIdBrand(id);
        Model model2 = new Model();
        model2.setName("Test2");
        model2.setIdBrand(id);
        Model model3 = new Model();
        model3.setName("Test3");
        this.carStorage.addObject(model);
        this.carStorage.addObject(model2);
        this.carStorage.addObject(model3);
        assertThat(this.carStorage.getModels(id).size(), is(2));
    }

    @Test
    public void whereGetAdvertUser() {
        User user = new User();
        user.setLogin("TestUser");
        carStorage.addObject(user);
        int id = user.getId();
        Advert advert = new Advert();
        advert.setName("Test");
        advert.setUser(user);
        Advert advert2 = new Advert();
        advert2.setName("Test2");
        advert2.setUser(user);
        Advert advert3 = new Advert();
        advert3.setName("Test3");
        this.carStorage.addObject(advert);
        this.carStorage.addObject(advert2);
        this.carStorage.addObject(advert3);
        assertThat(this.carStorage.getAdvertUser(id).size(), is(2));
    }
}