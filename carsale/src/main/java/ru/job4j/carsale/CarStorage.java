package ru.job4j.carsale;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.job4j.models.Advert;
import ru.job4j.models.Brand;
import ru.job4j.models.Model;
import ru.job4j.models.User;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public enum CarStorage {

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

    public List getList(final String table) {
        return this.tx(
                session -> {
                    List list = session.createQuery(String.format("FROM %s ORDER BY id ASC", table)).list();
                    return list;
                }
        );
    }

    public List<Advert> getActivAdvert() {
        return this.tx(
                session -> {
                    List<Advert> list = session.createQuery("FROM Advert WHERE status = true ORDER BY id ASC").list();
                    return list;
                }
        );
    }

    public List<Advert> getAdvertDay() {
        return this.tx(
                session -> {
                    Query query = session.createQuery("FROM Advert WHERE timecreated > :time ORDER BY id ASC");
                    query.setParameter("time", new Timestamp(System.currentTimeMillis() - 86400000));
                    List<Advert> list = query.list();
                    return list;
                }
        );
    }

    public List<Advert> getAdvertBrand(final int idBrand) {
        return this.tx(
                session -> {
                    Query query = session.createQuery("FROM Advert WHERE id_brand = :idBrand ORDER BY id ASC");
                    query.setParameter("idBrand", idBrand);
                    List<Advert> list = query.list();
                    return list;
                }
        );
    }

    public void addObject(final Object obj) {
        this.tx(
                session -> {
                    session.saveOrUpdate(obj);
                    return null;
                }
        );
    }

    public void delObject(final Object obj) {
        this.tx(
                session -> {
                    session.delete(obj);
                    return null;
                }
        );
    }

    public Brand getBrand(final String brandName) {
        return this.tx(
                session -> {
                    Query query = session.createQuery("FROM Brand WHERE name = :brand");
                    query.setParameter("brand", brandName);
                    List list = query.list();
                    Brand brand = null;
                    if (list.size() > 0) {
                        brand = (Brand) query.list().get(0);
                    }
                    return brand;
                }
        );
    }

    public User getUser(final int id) {
       return this.tx(
                session -> {
                    User user = session.get(User.class, id);
                    return user;
                }
        );
    }

    public Brand getBrand(final int id) {
        return this.tx(
                session -> {
                    Brand brand = session.get(Brand.class, id);
                    return brand;
                }
        );
    }

    public Model getModel(final int id) {
        return this.tx(
                session -> {
                    Model model = session.get(Model.class, id);
                    return model;
                }
        );
    }

    public Advert getAdvert(final int id) {
        return this.tx(
                session -> {
                    Advert advert = session.get(Advert.class, id);
                    return advert;
                }
        );
    }

    public List<Model> getModels(final int idBrand) {
        return this.tx(
                session -> {
                    Query query = session.createQuery("FROM Model WHERE id_brand = :idBrand");
                    query.setParameter("idBrand", idBrand);
                    List<Model> list = query.list();
                    return list;
                }
        );
    }

    public List<Advert> getAdvertUser(final int idUser) {
        return this.tx(
                session -> {
                    Query query = session.createQuery("FROM Advert WHERE id_user = :idUser");
                    query.setParameter("idUser", idUser);
                    List<Advert> list = query.list();
                    return list;
                }
        );
    }
}
