package ru.job4j.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.job4j.model.Item;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 13.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class DbService implements Service {
    private static final Logger LOG = LogManager.getLogger(DbService.class);
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    private static class DbS {
        private static final DbService INSTANCE = new DbService();
    }

    public static DbService getInstance() {
        return DbS.INSTANCE;
    }

    @Override
    public List<Item> getItems(String status) {
        List<Item> items = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            if ("not".equals(status)) {
                session.beginTransaction();
                items .addAll(session.createQuery("select i from Item i where i.done = false order by  created").list());
                session.getTransaction().commit();
            }
            if ("all".equals(status)) {
                session.beginTransaction();
                items .addAll(session.createQuery("from Item order by  created").list());
                session.getTransaction().commit();
            }
        }
        return items;
    }

    @Override
    public void add(String description) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Item item = new Item(description, new Timestamp(System.currentTimeMillis()), false);
            session.save(item);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(int id, boolean done) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Item item = session.load(Item.class, id);
            item.setDone(done);
            session.flush();
            session.getTransaction().commit();
        }
    }

    @Override
    public void close() {
        sessionFactory.close();
    }
}
