package persistence.repositories;


import java.io.Serializable;
import java.util.List;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public abstract class HibernateGenericDAO<T>
        extends HibernateDaoSupport implements
        GenericRepository<T>, Serializable {

    //public static Logger log = Logger.getLogger(PersonRepository.class);
    //private static final long serialVersionUID = 5058950102420892922L;

    protected Class<T> persistentClass = this.getDomainClass();

    @SuppressWarnings("unchecked")
    public int count() {
        List<Long> list = (List<Long>) this.getHibernateTemplate()
                .find("select count(*) from " + this.persistentClass.getName() + " o");
        Long count = list.get(0);
        return count.intValue();

    }

    public void delete(final T entity) {
        // EJEMPLO DE COMO USAR LOG4J CON ERROR
        try {
            this.getHibernateTemplate().delete(entity);
        } catch (RuntimeException e) {
            //log.error(e);
        }
    }

    public void deleteById(final Serializable id) {
        T obj = this.findById(id);
        this.getHibernateTemplate().delete(obj);
    }

    public List<T> findAll() {
        List<T> find = (List<T>) this.getHibernateTemplate().find("from " + this.persistentClass.getName() + " o");
        return find;

    }

    public List<T> findByExample(final T exampleObject) {
        return this.getHibernateTemplate().findByExample(exampleObject);

    }

    public T findById(final Serializable id) {
        return this.getHibernateTemplate().get(this.persistentClass, id);
    }

    protected abstract Class<T> getDomainClass();

    public void save(final T entity) {
        this.getHibernateTemplate().save(entity);
        this.getHibernateTemplate().flush();
    }

    public void update(final T entity) {
        this.getHibernateTemplate().update(entity);
    }

    public void deleteAll() {
        this.getHibernateTemplate().deleteAll(this.findAll());
    }

}