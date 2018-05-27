package ru.job4j.todolist;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.models.Item;

import java.util.List;
import java.util.function.Function;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public enum EnumSingleton {
    INSTANCE;

    private SessionFactory factory;
    private Session session;

    public void start() {
        factory = new Configuration().configure().buildSessionFactory();
    }

    public void finish() {
        if (factory != null) {
            factory.close();
        }
    }

    private <T> T tx(final Function<Session, T> command) {
        session = factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            return command.apply(session);
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            tx.commit();
            if (session != null) {
                session.close();
            }
        }
    }

    public void addOrUpadateItem(final Item item) {
        this.tx(
                session -> {
                    session.saveOrUpdate(item);
                    return null;
                }
        );
    }

    public List<Item> getList() {
        return this.tx(session -> session.createQuery("FROM Item ORDER BY id ASC").list());
    }

    public Item getItem(final String id) {
        return this.tx(
                session -> {
                    List<Item> list = session.createQuery(String.format("FROM Item WHERE id = '%s'", id)).list();
                    Item item = null;
                    if (!list.isEmpty()) {
                        item = list.get(0);
                    }
                    return item;
                }
        );
    }
}
