package ru.job4j.hibernate.models;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.hibernate.HibernateUtil;
import ru.job4j.hibernate.dao.ItemDaoImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ItemTest {

    private Item item;

    @Before
    public void setUp() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM Item ").executeUpdate();
            session.getTransaction().commit();
        }
        item = new Item().setDescription("first note")
            .setCreated(Timestamp.valueOf(LocalDateTime.now()))
            .setDone(false);
    }

    @Test
    public void createItemInDb() {
        Long id = new ItemDaoImpl().create(item);
        item.setId(id);
        List<Item> items = new ItemDaoImpl().findAll();
        assertThat(items, hasSize(1));
        assertThat(items.get(0), is(item));
        assertThat(new ItemDaoImpl().findOne(id), is(item));
    }

    @Test
    public void updateItemInDb() {
        Long id = new ItemDaoImpl().create(item);
        item.setId(id);
        item.setDescription("updated description");
        Boolean result = new ItemDaoImpl().update(item);
        Item updatedItem = new ItemDaoImpl().findOne(id);
        List<Item> items = new ItemDaoImpl().findAll();
        assertTrue(result);
        assertThat(items, hasSize(1));
        assertThat(items.get(0), is(updatedItem));
        assertThat(updatedItem.getDescription(), is("updated description"));
    }

    @Test
    public void deleteItemFromDb() {
        Long id = new ItemDaoImpl().create(item);
        item.setId(id);
        Boolean result = new ItemDaoImpl().delete(item);
        List<Item> items = new ItemDaoImpl().findAll();
        assertTrue(result);
        assertThat(items, hasSize(0));
        assertNull(new ItemDaoImpl().findOne(id));
    }

    @Test
    public void deleteItemFromDbById() {
        Long id = new ItemDaoImpl().create(item);
        item.setId(id);
        Boolean result = new ItemDaoImpl().deleteById(id);
        List<Item> items = new ItemDaoImpl().findAll();
        assertTrue(result);
        assertThat(items, hasSize(0));
        assertNull(new ItemDaoImpl().findOne(id));
    }
}