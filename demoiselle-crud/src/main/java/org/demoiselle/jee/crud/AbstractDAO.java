/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee.crud;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MultivaluedMap;

import org.demoiselle.jee.core.api.crud.Crud;
import org.demoiselle.jee.core.api.crud.Result;
import org.demoiselle.jee.crud.exception.DemoiselleCrudException;
import org.demoiselle.jee.crud.pagination.PaginationHelperConfig;
import org.demoiselle.jee.crud.pagination.ResultSet;
import org.demoiselle.jee.crud.sort.CrudSort;

//TODO CLF revisar
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public abstract class AbstractDAO<T, I> implements Crud<T, I> {

    @Inject
    private PaginationHelperConfig paginationConfig;

    @Inject
    private DemoiselleRequestContext drc;

    private final Class<T> entityClass;

    protected abstract EntityManager getEntityManager();

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public T persist(T entity) {
        try {
            getEntityManager().persist(entity);
            return entity;
        } catch (Exception e) {
            throw new DemoiselleCrudException("Não foi possível salvar", e);
        }
    }

    @Override
    public T mergeHalf(I id, T entity) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ");
            sb.append(entity.getClass().getCanonicalName());
            sb.append(" SET ");
            for (Method method : entityClass.getDeclaredMethods()) {
                Object obj = method.invoke(entityClass);
                if (obj != null) {
                    sb.append(method.getName()).append(" = ").append(obj);
                }
            }
            sb.append(" WHERE ")
                    .append(CrudUtilHelper.getMethodAnnotatedWithID(entityClass))
                    .append(" = ")
                    .append(id);
            System.err.println(sb.toString());
//            //getEntityManager().createQuery(sb.toString()).executeUpdate();
            return entity;
        } catch (Exception e) {
            // TODO: CLF Severe? Pode cair aqui somente por ter violação de Unique
            throw new DemoiselleCrudException("Não foi possível salvar", e);
        }
    }

    @Override
    public T mergeFull(T entity) {
        try {
            getEntityManager().merge(entity);
            return entity;
        } catch (Exception e) {
            // TODO: CLF Severe? Pode cair aqui somente por ter violação de Unique
            throw new DemoiselleCrudException("Não foi possível salvar", e);
        }
    }

    @Override
    public void remove(I id) {
        try {
            getEntityManager().remove(getEntityManager().find(entityClass, id));
        } catch (Exception e) {
            throw new DemoiselleCrudException("Não foi possível excluir", e);
        }
    }

    @Override
    public T find(I id) {
        try {
            return getEntityManager().find(entityClass, id);
        } catch (Exception e) {
            throw new DemoiselleCrudException("Não foi possível consultar", e);
        }

    }

    @Override
    public Result find() {

        try {

            Result result = new ResultSet();

            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);

            configureCriteriaQuery(criteriaBuilder, criteriaQuery);

            TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);

            if (drc.isPaginationEnabled()) {
                Integer firstResult = drc.getOffset() == null ? 0 : drc.getOffset();
                Integer maxResults = getMaxResult();
                Long count = count();

                if (firstResult < count) {
                    query.setFirstResult(firstResult);
                    query.setMaxResults(maxResults);
                }

                drc.setCount(count);
            }

            result.setContent(query.getResultList());
            if (result.getContent() != null && !result.getContent().isEmpty()
                    && drc.isPaginationEnabled()
                    && result.getContent().size() <= drc.getCount() && drc.getCount() < getMaxResult()) {
                drc.setLimit(drc.getCount().intValue());
            }

            drc.setEntityClass(entityClass);

            return result;

        } catch (Exception e) {
            logger.severe(e.getMessage());
            throw new DemoiselleCrudException("Não foi possível consultar", e);
        }
    }

    private void configureCriteriaQuery(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery) {
        Root<T> from = criteriaQuery.from(entityClass);
        if (drc.getFilters() != null) {
            criteriaQuery.select(from).where(buildPredicates(criteriaBuilder, criteriaQuery, from));
        }

        configureOrder(criteriaBuilder, criteriaQuery, from);
    }

    private void configureOrder(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery, Root<T> root) {

        if (!drc.getSorts().isEmpty()) {
            List<Order> orders = new ArrayList<>();

            drc.getSorts().stream().forEachOrdered(sortModel -> {

                if (sortModel.getType().equals(CrudSort.ASC)) {
                    orders.add(criteriaBuilder.asc(root.get(sortModel.getField())));
                } else {
                    orders.add(criteriaBuilder.desc(root.get(sortModel.getField())));
                }
            });

            criteriaQuery.orderBy(orders);
        }

    }

    private Predicate[] buildPredicates(CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<T> root) {
        List<Predicate> predicates = new LinkedList<>();

        if (drc.getFilters() != null) {
            drc.getFilters().getChildren().stream().forEach(child -> {

                List<Predicate> predicateAndKeys = new LinkedList<>();
                List<Predicate> predicateSameKey = new LinkedList<>();

                // Many parameters for the same key, generate OR clause
                if (!child.getChildren().isEmpty()) {

                    Join<?, ?> join = root.join(child.getKey());
                    child.getChildren().stream().forEach(values -> {

                        predicateSameKey.clear();

                        if (!child.getChildren().isEmpty()) {

                            values.getValue().stream().forEach(value -> {
                                if ("null".equals(value) || value == null) {
                                    predicateSameKey.add(criteriaBuilder.isNull(join.get(values.getKey())));
                                } else {
                                    if (values.getValue().isEmpty()) {
                                        predicateSameKey.add(criteriaBuilder.isEmpty(join.get(values.getKey())));
                                    } else {
                                        predicateSameKey.add(criteriaBuilder.equal(join.get(values.getKey()), value));
                                    }
                                }
                            });

                            predicates.add(criteriaBuilder.or(predicateSameKey.toArray(new Predicate[]{})));
                        }
                    });
                } else {
                    child.getValue().stream().forEach(value -> {
                        if ("null".equals(value) || value == null) {
                            predicateAndKeys.add(criteriaBuilder.isNull(root.get(child.getKey())));
                        } else {
                            if (child.getValue().isEmpty()) {
                                predicateAndKeys.add(criteriaBuilder.isEmpty(root.get(child.getKey())));
                            } else {
                                predicateAndKeys.add(criteriaBuilder.equal(root.get(child.getKey()), value));
                            }
                        }
                    });

                    predicates.add(criteriaBuilder.and(predicateAndKeys.toArray(new Predicate[]{})));
                }
            });
        }

        return predicates.toArray(new Predicate[]{});
    }

    private Integer getMaxResult() {
        if (drc.getLimit() == null || drc.getOffset() == null) {
            return paginationConfig.getDefaultPagination();
        }

        return (drc.getLimit() - drc.getOffset()) + 1;
    }

    public Long count() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> countCriteria = criteriaBuilder.createQuery(Long.class);
        Root<T> entityRoot = countCriteria.from(entityClass);
        countCriteria.select(criteriaBuilder.count(entityRoot));

        if (drc.getFilters() != null) {
            countCriteria.where(buildPredicates(criteriaBuilder, countCriteria, entityRoot));
        }

        return getEntityManager().createQuery(countCriteria).getSingleResult();
    }

    protected Predicate[] extractPredicates(MultivaluedMap<String, String> queryParameters,
            CriteriaBuilder criteriaBuilder, Root<T> root) {
        return new Predicate[]{};
    }
}
