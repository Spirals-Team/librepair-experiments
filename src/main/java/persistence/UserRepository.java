package persistence;

import model.User;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mariano on 23/04/18.
 */
public class UserRepository extends HibernateGenericDAO<User> implements GenericRepository<User> {

    public static Logger log = Logger.getLogger(UserRepository.class);
    private static final long serialVersionUID = -4036535812105672110L;


    @Override
    public void save(User entity) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void update(User entity) {

    }

    @Override
    public User findById(Serializable id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }


    @Override
    protected Class<User> getDomainClass() {
        return User.class;
    }

}
