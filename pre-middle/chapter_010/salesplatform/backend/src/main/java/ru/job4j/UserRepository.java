package ru.job4j;

import org.hibernate.query.Query;
import ru.job4j.dao.AbstractDao;
import ru.job4j.model.User;

import java.util.Optional;

public class UserRepository implements AbstractDao {

    public User findByEmailPassw(final String email, final String passw) {
        return fetchTx(session -> {
            Query query = session.createQuery("from User where email=:email and passw=:passw");
            query.setParameter("email", email);
            query.setParameter("passw", passw);
            return (User) Optional.ofNullable(query.uniqueResult()).orElse(new User());
        });
    }

    public User findByEmail(final String email) {
        return fetchTx(session -> {
            Query query = session.createQuery("from User where email=:email");
            query.setParameter("email", email);
            return (User) Optional.ofNullable(query.uniqueResult()).orElse(new User());
        });
    }

    /**
     * Check matching of credentials
     *
     * @param email email
     * @param passw passw
     * @return result
     */
    public boolean isCredential(final String email, final String passw) {
        return !findByEmailPassw(email, passw).isEmpty();
    }
}
