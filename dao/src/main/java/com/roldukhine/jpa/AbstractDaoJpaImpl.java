package com.roldukhine.jpa;

import com.roldukhine.api.CrudDao;
import com.roldukhine.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Slf4j
@Repository
public abstract class AbstractDaoJpaImpl<T extends BaseEntity> implements CrudDao<T> {

    protected final Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public AbstractDaoJpaImpl() {
        logger.trace("construct AbstractDaoJpaImpl");
        ParameterizedType genericSuperclass = (ParameterizedType) (getClass().getGenericSuperclass());
        logger.debug("genericSuperclass = {}", genericSuperclass);
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public void insert(T entity) {
        logger.trace("insert {}", entity);
        entityManager.persist(entity);
        entityManager.flush();
    }

    @Override
    public void update(T entity) {
        logger.trace("update {}", entity);
        entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        logger.trace("delete {}", entity);
        entityManager.remove(entity);
    }

    @Override
    public T get(long id) {
        logger.trace("get with input id {}", id);
        @SuppressWarnings("unchecked") T entity = entityManager.find(entityClass, id);
        return entity;
    }

    @Override
    public List<T> getAll() {
        logger.trace("getAll");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        logger.debug("getCriteriaBuilder {}", criteriaBuilder);
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        logger.debug("criteriaQuery {} ", criteriaQuery);
        Root<T> from = criteriaQuery.from(entityClass);
        CriteriaQuery<T> select = criteriaQuery.select(from);
        logger.debug("CriteriaQuery select {} ", select);
        return entityManager.createQuery(select).getResultList();
    }
}
