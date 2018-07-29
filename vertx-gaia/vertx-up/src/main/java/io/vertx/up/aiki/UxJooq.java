package io.vertx.up.aiki;

import io.github.jklingsporn.vertx.jooq.future.VertxDAO;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.tp.plugin.jooq.JooqInfix;
import io.vertx.up.atom.query.Criteria;
import io.vertx.up.atom.query.Inquiry;
import io.vertx.up.atom.query.Pager;
import io.vertx.up.log.Annal;
import io.vertx.zero.eon.Values;
import io.vertx.zero.exception.JooqArgumentException;
import io.vertx.zero.exception.JooqMergeException;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("all")
public class UxJooq {

    private static final Annal LOGGER = Annal.get(UxJooq.class);
    private static final ConcurrentMap<String, BiFunction<String, Object, Condition>> OPS =
            new ConcurrentHashMap<String, BiFunction<String, Object, Condition>>() {
                {
                    this.put(Inquiry.Op.LT, (field, value) -> DSL.field(field).lt(value));
                    this.put(Inquiry.Op.GT, (field, value) -> DSL.field(field).gt(value));
                    this.put(Inquiry.Op.LE, (field, value) -> DSL.field(field).le(value));
                    this.put(Inquiry.Op.GE, (field, value) -> DSL.field(field).ge(value));
                    this.put(Inquiry.Op.EQ, (field, value) -> DSL.field(field).eq(value));
                    this.put(Inquiry.Op.NEQ, (field, value) -> DSL.field(field).ne(value));
                    this.put(Inquiry.Op.NOT_NULL, (field, value) -> DSL.field(field).isNotNull());
                    this.put(Inquiry.Op.NULL, (field, value) -> DSL.field(field).isNull());
                    this.put(Inquiry.Op.TRUE, (field, value) -> DSL.field(field).isTrue());
                    this.put(Inquiry.Op.FALSE, (field, value) -> DSL.field(field).isFalse());
                    this.put(Inquiry.Op.IN, UxJooq::opIn);
                    this.put(Inquiry.Op.NOT_IN, UxJooq::opNotIn);
                    this.put(Inquiry.Op.START, (field, value) -> DSL.field(field).startsWith(value));
                    this.put(Inquiry.Op.END, (field, value) -> DSL.field(field).endsWith(value));
                    this.put(Inquiry.Op.CONTAIN, (field, value) -> DSL.field(field).contains(value));
                }
            };
    private static final ConcurrentMap<String, BiFunction<String, Instant, Condition>> DOPS =
            new ConcurrentHashMap<String, BiFunction<String, Instant, Condition>>() {
                {
                    this.put(Inquiry.Instant.DAY, (field, value) -> {
                        // Time for locale
                        final LocalDate date = Ut.toDate(value);
                        return DSL.field(field).between(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
                    });
                    this.put(Inquiry.Instant.DATE, (field, value) -> {
                        final LocalDate date = Ut.toDate(value);
                        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        return DSL.field(field).eq(date.format(formatter));
                    });
                }
            };
    private transient final Class<?> clazz;
    private transient final VertxDAO vertxDAO;
    private transient String pojo;

    <T> UxJooq(final Class<T> clazz) {
        this.clazz = clazz;
        this.vertxDAO = (VertxDAO) JooqInfix.getDao(clazz);
    }

    // Condition ---------------------------------------------------------
    public static Condition transform(final JsonObject filters, final Operator operator) {
        Condition condition = null;
        for (final String field : filters.fieldNames()) {
            final String key = getKey(field);
            final String[] fields = field.split(",");
            final String targetField = field.split(",")[Values.IDX];
            // Date, DateTime, Time
            Object value = filters.getValue(field);

            if (3 > fields.length) {
                // Function
                final BiFunction<String, Object, Condition> fun = OPS.get(key);
                // JsonArray to List, fix vert.x and jooq connect issue.
                if (Ut.isJArray(value)) {
                    value = ((JsonArray) value).getList().toArray();
                }
                final Condition item = fun.apply(targetField.trim(), value);
                condition = opCond(condition, item, operator);
                // Function condition inject

            } else if (3 == fields.length) {
                Fn.outUp(null == value, LOGGER,
                        JooqArgumentException.class, UxJooq.class, value);
                final Instant instant = filters.getInstant(field);
                Fn.outUp(Instant.class != instant.getClass(), LOGGER,
                        JooqArgumentException.class, UxJooq.class, instant.getClass());
                final String mode = fields[Values.TWO];
                final BiFunction<String, Instant, Condition> fun = DOPS.get(mode);
                final Condition item = fun.apply(targetField.trim(), instant);
                condition = opCond(condition, item, operator);
            }
        }
        LOGGER.info(Info.JOOQ_PARSE, condition);
        return condition;
    }

    private static String getKey(final String field) {
        if (!field.contains(",")) {
            return Inquiry.Op.EQ;
        } else {
            final String opStr = field.split(",")[Values.ONE];
            return Ut.isNil(opStr) ? Inquiry.Op.EQ : opStr.trim().toLowerCase();
        }
    }

    private static Condition opIn(final String field, final Object value) {
        return opCond(value, Operator.OR, DSL.field(field)::eq);
    }

    private static Condition opNotIn(final String field,
                                     final Object value) {
        return opCond(value, Operator.OR, DSL.field(field)::ne);
    }

    private static Condition opCond(final Object value,
                                    final Operator operator,
                                    final Function<Object, Condition> condFun) {
        // Using or instead of in
        Condition condition = null;
        // Params
        final Collection values = Ut.toCollection(value);
        if (null != values) {
            for (final Object item : values) {
                final Condition itemCond = condFun.apply(item);
                condition = opCond(condition, itemCond, operator);
            }
        }
        return condition;
    }

    private static Condition opCond(final Condition left,
                                    final Condition right,
                                    final Operator operator) {
        if (null == left || null == right) {
            if (null == left && null != right) {
                return right;
            } else {
                return null;
            }
        } else {
            if (Operator.AND == operator) {
                return left.and(right);
            } else {
                return left.or(right);
            }
        }
    }

    // Bind current jooq to pojo configuration file.
    public UxJooq on(final String pojo) {
        LOGGER.info(Info.JOOQ_BIND, pojo, this.clazz);
        this.pojo = pojo;
        return this;
    }

    // CRUD - Read -----------------------------------------------------
    // Get by id
    public <T> Future<T> findByIdAsync(final Object id) {
        final CompletableFuture<T> future =
                this.vertxDAO.findByIdAsync(id);
        return Async.toFuture(future);
    }

    // Get all
    public <T> Future<List<T>> findAllAsync() {
        final CompletableFuture<List<T>> future =
                this.vertxDAO.findAllAsync();
        return Async.toFuture(future);
    }

    // CRUD - Create ---------------------------------------------------
    // Create entity
    public <T> Future<T> insertAsync(final T entity) {
        final CompletableFuture<Void> future =
                this.vertxDAO.insertAsync(entity);
        return Async.toFuture(future)
                .compose(result -> Future.succeededFuture(entity));
    }

    // Create entity
    public <T> Future<T> insertReturningPrimaryAsync(final T entity, final Consumer<Long> consumer) {
        final CompletableFuture<Long> future =
                this.vertxDAO.insertReturningPrimaryAsync(entity);
        return Async.toFuture(future).compose(id -> {
            if (null != consumer) {
                consumer.accept(id);
            }
            return Future.succeededFuture(entity);
        });
    }

    // Create entities
    public <T> Future<List<T>> insertAsync(final List<T> entities) {
        final CompletableFuture<Void> future =
                this.vertxDAO.insertAsync(entities);
        return Async.toFuture(future)
                .compose(result -> Future.succeededFuture(entities));
    }

    // CRUD - Update ----------------------------------------------------
    // Update entity
    public <T> Future<T> updateAsync(final T entity) {
        final CompletableFuture<Void> future =
                this.vertxDAO.updateAsync(entity);
        return Async.toFuture(future)
                .compose(result -> Future.succeededFuture(entity));
    }

    public <T> Future<List<T>> updateAsync(final List<T> entities) {
        final CompletableFuture<Void> future =
                this.vertxDAO.updateAsync(entities);
        return Async.toFuture(future)
                .compose(result -> Future.succeededFuture(entities));
    }

    // CRUD - Upsert ----------------------------------------------------
    private <T> T copyEntity(final T target, final T updated) {
        Fn.outUp(null == updated, LOGGER, JooqMergeException.class,
                UxJooq.class, null == target ? null : target.getClass(), Ut.serialize(target));
        final JsonObject targetJson = null == target ? new JsonObject() : Ut.serializeJson(target);
        final JsonObject sourceJson = Ut.serializeJson(updated);
        targetJson.mergeIn(sourceJson, true);
        return (T) Ut.deserialize(targetJson, updated.getClass());
    }

    public <T> Future<T> saveAsync(final Object id, final T updated) {
        return saveAsync(id, (target) -> copyEntity(target, updated));
    }

    // CRUD - Existing/Missing ------------------------------------------

    public <T> Future<T> saveAsync(final Object id, final Function<T, T> copyFun) {
        return this.<T>findByIdAsync(id).compose(old ->
                this.<T>updateAsync(copyFun.apply(old)));
    }

    public <T> Future<T> upsertReturningPrimaryAsync(final JsonObject andFilters, final T updated, final Consumer<Long> consumer) {
        return this.<T>fetchOneAndAsync(andFilters)
                .compose(item -> Fn.match(
                        // null != item, updated to existing item.
                        Fn.fork(() -> this.<T>updateAsync(copyEntity(item, updated))),
                        // null == item, insert data
                        Fn.branch(null == item, () -> this.insertReturningPrimaryAsync(updated, consumer))
                ));
    }

    public <T> Future<T> upsertAsync(final JsonObject andFilters, final T updated) {
        return this.<T>fetchOneAndAsync(andFilters)
                .compose(item -> Fn.match(
                        // null != item, updated to existing item.
                        Fn.fork(() -> this.<T>updateAsync(copyEntity(item, updated))),
                        // null == item, insert data
                        Fn.branch(null == item, () -> this.insertAsync(updated))
                ));
    }

    // CRUD - Delete ----------------------------------------------------
    public <T> Future<T> deleteAsync(final T entity) {
        final CompletableFuture<Void> future =
                this.vertxDAO.deleteAsync(Arrays.asList(entity));
        return Async.toFuture(future)
                .compose(result -> Future.succeededFuture(entity));
    }

    public <T> Future<Boolean> deleteByIdAsync(final Object id) {
        final CompletableFuture<Void> future =
                this.vertxDAO.deleteByIdAsync(id);
        return Async.toFuture(future)
                .compose(result -> Future.succeededFuture(Boolean.TRUE));
    }

    public <T> Future<Boolean> deleteByIdAsync(final Collection<Object> ids) {
        final CompletableFuture<Void> future =
                this.vertxDAO.deleteByIdAsync(ids);
        return Async.toFuture(future)
                .compose(result -> Future.succeededFuture(Boolean.TRUE));
    }

    public <T> Future<Boolean> deleteByIdAsync(final Object... ids) {
        final CompletableFuture<Void> future =
                this.vertxDAO.deleteByIdAsync(Arrays.asList(ids));
        return Async.toFuture(future)
                .compose(result -> Future.succeededFuture(Boolean.TRUE));
    }

    public <T> Future<Boolean> existsByIdAsync(final Object id) {
        final CompletableFuture<Boolean> future =
                this.vertxDAO.existsByIdAsync(id);
        return Async.toFuture(future);
    }

    public <T> Future<Boolean> existsOneAsync(final JsonObject andFilters) {
        return this.<T>fetchOneAndAsync(andFilters)
                .compose(item -> Future.succeededFuture(null != item));
    }

    // Search Operation --------------------------------------------------
    // Search ( Pager, Sort, Projection )
    // Because you want to do projection remove, it means that you could not pass List<T> in current method.

    // Fetch Operation --------------------------------------------------
    // Fetch One
    // Filter column called
    public <T> Future<T> fetchOneAsync(final String column, final Object value) {
        final CompletableFuture<T> future =
                this.vertxDAO.fetchOneAsync(DSL.field(column), value);
        return Async.toFuture(future);
    }

    // Filter transform called
    public <T> Future<T> fetchOneAndAsync(final JsonObject andFilters) {
        final Condition condition = transform(andFilters, Operator.AND);
        final CompletableFuture<T> future =
                this.vertxDAO.fetchOneAsync(condition);
        return Async.toFuture(future);
    }

    // Filter column called
    // Fetch List
    public <T> Future<List<T>> fetchAsync(final String column, final Object value) {
        final CompletableFuture<List<T>> future =
                this.vertxDAO.fetchAsync(DSL.field(column), Arrays.asList(value));
        return Async.toFuture(future);
    }

    // Filter column called
    public <T> Future<List<T>> fetchInAsync(final String column, final Object... value) {
        final JsonArray values = Ut.toJArray(Arrays.asList(value));
        return fetchInAsync(column, values);
    }

    // Filter column called
    public <T> Future<List<T>> fetchInAsync(final String column, final JsonArray values) {
        final CompletableFuture<List<T>> future =
                this.vertxDAO.fetchAsync(DSL.field(column), values.getList());
        return Async.toFuture(future);
    }

    // Filter transform called
    public <T> Future<List<T>> fetchAndAsync(final JsonObject andFilters) {
        final Condition condition = transform(andFilters, Operator.AND);
        final CompletableFuture<List<T>> future =
                this.vertxDAO.fetchAsync(condition);
        return Async.toFuture(future);
    }

    // Filter transform called
    public <T> Future<List<T>> fetchOrAsync(final JsonObject orFilters) {
        final Condition condition = transform(orFilters, Operator.OR);
        final CompletableFuture<List<T>> future =
                this.vertxDAO.fetchAsync(condition);
        return Async.toFuture(future);
    }

    public Future<JsonObject> searchOrAsync(final Inquiry inquiry) {
        return this.searchOrAsync(inquiry, "");
    }

    public Future<JsonObject> searchOrAsync(final JsonObject params, final String pojo) {
        final Inquiry inquiry = Query.getInquiry(params, pojo);
        return searchOrAsync(inquiry, pojo);
    }

    public Future<JsonObject> searchOrAsync(final Inquiry inquiry, final String pojo) {
        // Pager, Sort, Criteria, Projection
        final JsonObject result = new JsonObject();
        return searchDirect(inquiry, Operator.OR)
                .compose(array -> Ux.thenJsonMore(array.getList(), pojo))
                .compose(array -> {
                    result.put("list", array);
                    return countSearchAsync(inquiry, Operator.OR);
                })
                .compose(count -> {
                    result.put("count", count);
                    return Future.succeededFuture(result);
                });
    }

    public Future<JsonObject> searchAndAsync(final Inquiry inquiry) {
        return this.searchAndAsync(inquiry, "");
    }

    public Future<JsonObject> searchAndAsync(final JsonObject params, final String pojo) {
        final Inquiry inquiry = Query.getInquiry(params, pojo);
        return searchAndAsync(inquiry, pojo);
    }

    public Future<JsonObject> searchAndAsync(final Inquiry inquiry, final String pojo) {
        // Pager, Sort, Criteria, Projection
        final JsonObject result = new JsonObject();
        return searchDirect(inquiry, Operator.AND)
                .compose(array -> Ux.thenJsonMore(array.getList(), pojo))
                .compose(array -> {
                    result.put("list", array);
                    return countSearchAsync(inquiry, Operator.AND);
                })
                .compose(count -> {
                    result.put("count", count);
                    return Future.succeededFuture(result);
                });
    }

    public <T> Future<List<T>> searchAndListAsync(final Inquiry inquiry) {
        // Fast mode is search AND operator
        return searchAsync(inquiry, Operator.AND);
    }

    public <T> Future<List<T>> searchOrListAsync(final Inquiry inquiry) {
        // Fast mode is search AND operator
        return searchAsync(inquiry, Operator.OR);
    }

    private Future<JsonArray> searchDirect(final Inquiry inquiry, final Operator operator) {
        // Pager, Sort, Criteria, Projection
        return searchAsync(inquiry, operator)
                .compose(list -> {
                    if (null == inquiry.getProjection()) {
                        return Ux.thenJsonMore(list);
                    } else {
                        return Ux.thenJsonMore(list)
                                .compose(array -> Uarr.create(array)
                                        .remove(inquiry.getProjection().toArray(new String[]{}))
                                        .toFuture());
                    }
                });
    }

    // Filter transform called
    private <T> Future<Integer> countSearchAsync(final Inquiry inquiry, final Operator operator) {
        final Criteria criteria = inquiry.getCriteria();
        final Function<DSLContext, Integer> function
                = dslContext -> null == criteria ? dslContext.fetchCount(this.vertxDAO.getTable()) :
                dslContext.fetchCount(this.vertxDAO.getTable(), transform(criteria.toJson(), operator));
        return Async.toFuture(this.vertxDAO.executeAsync(function));
    }

    // Filter transform called
    public <T> Future<List<T>> searchAsync(final Inquiry inquiry, final Operator operator) {
        // Pager, Sort, Criteria Only, this mode do not support projection
        final Function<DSLContext, List<T>> function
                = (dslContext) -> {
            // Started steps
            final SelectWhereStep started = dslContext.selectFrom(this.vertxDAO.getTable());
            // Condition set
            SelectConditionStep conditionStep = null;
            if (null != inquiry.getCriteria()) {
                final Condition condition = transform(inquiry.getCriteria().toJson(), operator);
                conditionStep = started.where(condition);
            }
            // Sorted Enabled
            SelectSeekStepN selectStep = null;
            if (null != inquiry.getSorter()) {
                final JsonObject sorter = inquiry.getSorter().toJson();
                final List<OrderField> orders = new ArrayList<>();
                for (final String field : sorter.fieldNames()) {
                    final boolean asc = sorter.getBoolean(field);
                    orders.add(asc ? DSL.field(field).asc() : DSL.field(field).desc());
                }
                if (null == conditionStep) {
                    selectStep = started.orderBy(orders);
                } else {
                    selectStep = conditionStep.orderBy(orders);
                }
            }
            // Pager Enabled
            SelectWithTiesAfterOffsetStep pagerStep = null;
            if (null != inquiry.getPager()) {
                final Pager pager = inquiry.getPager();
                if (null == selectStep && null == conditionStep) {
                    pagerStep = started.offset(pager.getStart()).limit(pager.getEnd());
                } else if (null == selectStep) {
                    pagerStep = conditionStep.offset(pager.getStart()).limit(pager.getEnd());
                } else {
                    pagerStep = selectStep.offset(pager.getStart()).limit(pager.getEnd());
                }
            }
            // Returned one by one
            if (null != pagerStep) {
                return pagerStep.fetch(this.vertxDAO.mapper());
            }
            if (null != selectStep) {
                return selectStep.fetch(this.vertxDAO.mapper());
            }
            if (null != conditionStep) {
                return conditionStep.fetch(this.vertxDAO.mapper());
            }
            return started.fetch(this.vertxDAO.mapper());
        };
        return Async.toFuture(this.vertxDAO.executeAsync(function));
    }
}
