package ru.job4j.jdbc;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class EntityTest {

    @Test
    public void whetSetMillionThenGetMany() {
        Entity entity = new Entity();
        entity.setN(1_000_000);
        entity.setCon();
        entity.converting();
        Assert.assertThat(entity.getSum(), is(500_000_500_000L));
    }

    @Test
    public void whetSetTenThenGetFew() {
        Entity entity = new Entity();
        entity.setN(10);
        entity.setCon();
        entity.converting();
        Assert.assertThat(entity.getSum(), is(55L));
    }
}