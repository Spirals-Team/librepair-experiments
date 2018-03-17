package ru.job4j.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.job4j.model.User;
import ru.job4j.service.HibernateService;

import java.util.List;
import java.util.function.Function;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 16.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class UserController extends AbstractController<User, Integer> {
    private static final Logger LOG = LogManager.getLogger(UserController.class);
    private SessionFactory sessionFactory = HibernateService.getSessionFactoty();

    private <T> T transaction(final Function<Session, T> function) {
        final Session session = this.sessionFactory.openSession();
        final Transaction trs = session.beginTransaction();
        try {
            return function.apply(session);
        } finally {
            trs.commit();
            session.close();
        }
    }
    @Override
    public List<User> getAll() {
        return transaction(session -> {
            List<User> users = session.createQuery("from User ").list();
            return users;
        });
    }

    @Override
    public User getEntityById(Integer id) {
        return transaction(session -> {
          User user =  session.get(User.class, id);
          return user;
        });
    }

    @Override
    public User update(User user) {
        return transaction(session -> {
            session.update(user);
            return user;
        });
    }

    @Override
    public boolean delete(Integer id) {
        return transaction(session -> {
            boolean found = false;
           User user = getEntityById(id);
           if (user != null) {
               session.delete(user);
               found = true;
           }
            return found;
        });
    }

    @Override
    public boolean create(User user) {
        return transaction(session -> {
            session.save(user);
            return true;
        });
    }
}
