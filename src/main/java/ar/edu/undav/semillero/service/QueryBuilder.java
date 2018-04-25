package ar.edu.undav.semillero.service;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Alejandro Gomez
 */
public class QueryBuilder<T> {

    private final StringBuilder buffer;
    private final Map<String, Object> params;
    private final List<Sort> sorts;
    private String select;
    private String mainAlias;
    private boolean whereAppended;
    private int varCounter;

    private QueryBuilder() {
        buffer = new StringBuilder();
        params = new HashMap<>();
        sorts = new ArrayList<>();
    }

    public QueryBuilder<T> select(String select) {
        this.select = select;
        return this;
    }

    public QueryBuilder<T> from(Class<? extends Persistable> entityClass, String alias) {
        mainAlias = alias;
        buffer.append(" from ").append(entityClass.getName()).append(" ").append(alias);
        return this;
    }

    public QueryBuilder<T> eq(String lhs, Serializable value) {
        addCondition(lhs, "=", value);
        return this;
    }

    public QueryBuilder<T> neq(String lhs, Serializable value) {
        addCondition(lhs, "!=", value);
        return this;
    }

    public QueryBuilder<T> gte(String lhs, Serializable value) {
        addCondition(lhs, ">=", value);
        return this;
    }

    public QueryBuilder<T> lte(String lhs, Serializable value) {
        addCondition(lhs, "<=", value);
        return this;
    }

    public QueryBuilder<T> lt(String lhs, Serializable value) {
        addCondition(lhs, "<", value);
        return this;
    }

    public QueryBuilder<T> ilike(String lhs, String value) {
        addCondition("lower(" + lhs + ")", "like", Optional.ofNullable(value).map(v -> '%' + v.toLowerCase() + '%').orElse(null));
        return this;
    }

    public QueryBuilder<T> condition(boolean flag, String condition) {
        if (flag) {
            appendConnector();
            buffer.append(condition);
        }
        return this;
    }

    public QueryBuilder<T> notNull(boolean flag, String lhs) {
        if (flag) {
            appendConnector();
            buffer.append(lhs).append(" != null");
        }
        return this;
    }

    private void addCondition(String lhs, String operator, Object value) {
        if (value != null) {
            String varName = "var" + (varCounter++);
            appendConnector();
            buffer.append(lhs).append(" ").append(operator).append(" :").append(varName);
            params.put(varName, value);
        }
    }

    private void appendConnector() {
        if (whereAppended) {
            buffer.append(" and ");
        } else {
            buffer.append(" where ");
            whereAppended = true;
        }
    }

    public QueryBuilder<T> orderAscBy(String path) {
        return orderBy(path, true);
    }

    public QueryBuilder<T> orderBy(String path, boolean ascending) {
        sorts.add(new Sort(path, ascending));
        return this;
    }

    private String getOrderByHQL() {
        if (sorts.isEmpty()) {
            return "";
        } else {
            return sorts.stream().map(Sort::toHqlString).collect(Collectors.joining(", ", " order by ", ""));
        }
    }

    protected String getHQL() {
        return "select " + select + buffer + getOrderByHQL();
    }

    protected String getCountHQL() {
        return "select count(" + mainAlias + ")" + buffer.toString();
    }

    protected Map<String, Object> getParams() {
        return params;
    }

    public static <T> QueryBuilder<T> of(Class<T> clazz) {
        return new QueryBuilder<>();
    }

    private static class Sort {

        private final String path;
        private final boolean ascending;

        private Sort(String path, boolean ascending) {
            this.path = path;
            this.ascending = ascending;
        }

        String toHqlString() {
            return path + (ascending ? " asc" : " desc");
        }
    }
}
