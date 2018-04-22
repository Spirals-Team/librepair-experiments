package ar.edu.undav.semillero.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;
import java.util.Map;

/**
 * @author Alejandro Gomez
 */
@Component
public class QueryRunner {

    private final EntityManager entityManager;

    public QueryRunner(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> Page<T> run(Class<T> clazz, QueryBuilder<T> queryBuilder, Pageable pageable) {
        long total = count(queryBuilder);
        TypedQuery<T> query = build(clazz, queryBuilder);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<T> results = query.getResultList();
        return new PageImpl<>(results, pageable, total);
    }

    private long count(QueryBuilder queryBuilder) {
        TypedQuery<Number> countQuery = buildCount(queryBuilder);
        return countQuery.getSingleResult().longValue();
    }

    private <T> TypedQuery<T> build(Class<T> clazz, QueryBuilder<T> queryBuilder) {
        return build(clazz, queryBuilder.getHQL(), queryBuilder.getParams());
    }

    private TypedQuery<Number> buildCount(QueryBuilder queryBuilder) {
        return build(Number.class, queryBuilder.getCountHQL(), queryBuilder.getParams());
    }

    private <T> TypedQuery<T> build(Class<T> clazz, String hql, Map<String, Object> params) {
        TypedQuery<T> query = entityManager.createQuery(hql, clazz);
        params.forEach(query::setParameter);
        return query;
    }
}
