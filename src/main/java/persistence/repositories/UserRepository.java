package persistence.repositories;


import model.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserRepository
        extends HibernateGenericDAO<User>
        implements GenericRepository<User> {

    @Override
    protected Class<User> getDomainClass() {
        return User.class;
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<User> filterUser(final String pattern) {

        return (List<User>) this.getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public List<User> doInHibernate(final Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(User.class);
                criteria.add(Restrictions.like("surname", "%" + pattern + "%"));
                return criteria.list();
            }

        });
    }


}
