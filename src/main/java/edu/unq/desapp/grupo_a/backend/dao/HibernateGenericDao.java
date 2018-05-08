package edu.unq.desapp.grupo_a.backend.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
public abstract class HibernateGenericDao<T> extends HibernateDaoSupport implements GenericDao<T>, Serializable {

	private static final long serialVersionUID = 2150025908769301782L;
	
	protected Class<T> persistentClass = this.getDomainClass();

	@Autowired
	public void init(SessionFactory factory) {
	    setSessionFactory(factory);
	}

    public int count() {
        List<Long> list = (List<Long>) this.getHibernateTemplate()
                .find("select count(*) from " + this.persistentClass.getName() + " o");
        Long count = list.get(0);
        return count.intValue();
    }

    public int count(String query) {
        List<Long> list = (List<Long>) this.getHibernateTemplate()
                .find(query);
        Long count = list.get(0);
        return count.intValue();
    }

    public void delete(final T entity) {
        this.getHibernateTemplate().delete(entity);
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

    public abstract Class<T> getDomainClass();

    public void save(final T entity) {
        this.getHibernateTemplate().saveOrUpdate(entity);
        this.getHibernateTemplate().flush();
    }

    @Transactional
    public void update(final T entity) {
        this.getHibernateTemplate().update(entity);
    }
    
	/**
	* Returns a page of items depending on the page size and the number.
    * @param pageSize the total record in one page.
    * @param pageNumber the page number starts from 0.
    */
    public List<T> findByPage(Integer pageNumber, Integer pageSize) {
       HibernateTemplate template = getHibernateTemplate();
       return (List<T>) template.execute(new HibernateCallback() {
           public List<T> doInHibernate(Session session) throws HibernateException {
               Query query = session.createQuery("from " + persistentClass.getName());
               query.setMaxResults(pageSize);
               query.setFirstResult(pageSize * pageNumber);
               return query.list();
           }
       });
    }

	/**
	* Returns a page of items depending on the page size, the page number and a query.
    * @param pageSize the total record in one page.
    * @param pageNumber the page number starts from 0.
    * @param hQuery the query to filter the entities.
    */
    public List<T> findByPage(Integer pageNumber, Integer pageSize, String hQuery) {
       HibernateTemplate template = getHibernateTemplate();
       return (List<T>) template.execute(new HibernateCallback() {
           public List<T> doInHibernate(Session session) throws HibernateException {
               Query query = session.createQuery(hQuery);
               query.setMaxResults(pageSize);
               query.setFirstResult(pageSize * pageNumber);
               return query.list();
           }
       });
    }

}