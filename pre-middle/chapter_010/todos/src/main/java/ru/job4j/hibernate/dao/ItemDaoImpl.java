package ru.job4j.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.job4j.hibernate.HibernateUtil;
import ru.job4j.hibernate.models.Item;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * DAO class for Item.
 */
public class ItemDaoImpl implements ItemDao {

    @Override
    public Item findOne(final Long key) {
        return this.fetchTx(session -> session.find(Item.class, key));
    }

    @Override
    public List<Item> findAll() {
        return this.fetchTx(session ->
            session.createQuery("from Item").list());
    }

    @Override
    public Long create(final Item entity) {
        return (Long) this.fetchTx(session -> session.save(entity));
    }

    @Override
    public boolean update(final Item entity) {
        this.modifyTx(session -> session.update(entity));
        return findOne(entity.getId()).equals(entity);
    }

    public void updateAll(final List<Item> entities) {
        entities.forEach(this::update);
    }

    @Override
    public boolean delete(final Item entity) {
        this.modifyTx(session -> session.delete(entity));
        return findOne(entity.getId()) == null;
    }

    @Override
    public boolean deleteById(final Long key) {
        return delete(findOne(key));
    }

    private <T> T fetchTx(final Function<Session, T> command) {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        final Transaction tx = session.beginTransaction();
        try {
            return command.apply(session);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            tx.commit();
            session.close();
        }
    }

    private void modifyTx(final Consumer<Session> command) {
        final Session session = HibernateUtil.getSessionFactory().openSession();
        final Transaction tx = session.beginTransaction();
        try {
            command.accept(session);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            tx.commit();
            session.close();
        }
    }
}
