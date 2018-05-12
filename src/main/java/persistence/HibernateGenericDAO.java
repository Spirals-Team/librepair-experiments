package persistence;


import org.apache.log4j.Logger;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mariano on 14/04/18.
 */
public abstract class HibernateGenericDAO<T>  extends HibernateDaoSupport implements GenericRepository<T>, Serializable {

    //public static Logger log = Logger.getLogger(.class);
    private static final long serialVersionUID = 5058950102420892922L;

    protected Class<T> persistentClass = this.getDomainClass();

    public T findById(Serializable id){
        Object aux = this.getHibernateTemplate().get(this.persistentClass.getName(), id);
        return (T)aux;
    }
    public int count() {
        List<Long> list = (List<Long>) this.getHibernateTemplate()
                .find("select count(*) from " + this.persistentClass.getName() + " o");
        Long count = list.get(0);
        return count.intValue();

    }

    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
        this.getHibernateTemplate().flush();
    }
    
    public void delete(T entity) {
        this.getHibernateTemplate().delete(entity);
        this.getHibernateTemplate().flush();
    }


    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
        this.getHibernateTemplate().flush();
    }


    public List<T> findAll() {
        List<T> find = (List<T>) this.getHibernateTemplate().find("from " + this.persistentClass.getName() + " o");
        return find;

    }


    protected abstract Class<T> getDomainClass();


}
