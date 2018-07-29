package io.vertx.up.aiki;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.jwt.JWT;
import io.vertx.ext.jwt.JWTOptions;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.up.atom.Envelop;
import io.vertx.up.atom.query.Inquiry;
import io.vertx.up.atom.query.Pager;
import io.vertx.up.atom.query.Sorter;
import io.vertx.up.exception.WebException;
import io.zero.epic.Ut;
import io.zero.epic.container.RxHod;
import io.zero.epic.fn.Actuator;
import io.zero.epic.fn.Fn;
import io.zero.epic.fn.wait.Log;

import java.util.List;
import java.util.UUID;
import java.util.function.*;

/**
 * Here Ux is a util interface of uniform to call different tools.
 * It just like helper for business usage.
 */
@SuppressWarnings("unchecked")
public final class Ux {

    // ---------------------- Fast Logging ----------------------------
    public static Log log(final Class<?> clazz) {
        return Log.create(null == clazz ? Ux.class : clazz);
    }

    // ---------------------- Debug/Timer --------------------------
    public static void debug(final Object... objects) {
        Debug.monitor(objects);
    }

    public static <T> Future<T> debug(final T item) {
        Fn.safeNull(() -> Debug.monitor(item), item);
        return Future.succeededFuture(item);
    }

    public static void timer(final Class<?> clazz, final Actuator actuator) {
        Debug.timer(clazz, actuator);
    }

    public static <T> T timer(final Class<?> clazz, final Supplier<T> supplier) {
        return Debug.timer(clazz, supplier);
    }

    // ---------------------- Pager/Sort --------------------------
    // Business method
    // page, size -> JsonObject
    public static JsonObject toPagerJson(final int page, final int size) {
        return Pagination.toPager(page, size).toJson();
    }

    // page, size -> Pager
    public static Pager toPager(final int page, final int size) {
        return Pagination.toPager(page, size);
    }

    // JsonObject -> Pager
    public static Pager toPager(final JsonObject data) {
        return Pagination.toPager(data);
    }

    // field, asc -> Sorter
    public static Sorter toSorter(final String field, final boolean asc) {
        return Pagination.toSorter(field, asc);
    }

    // field, mode -> Sorter
    public static Sorter toSorter(final String field, final int mode) {
        return Pagination.toSorter(field, mode);
    }

    // ---------------------- JsonObject Returned --------------------------
    // T -> JsonObject
    public static <T> JsonObject toJson(final T entity) {
        return To.toJson(entity, "");
    }

    // T -> JsonObject ( with Pojo )
    public static <T> JsonObject toJson(final T entity, final String pojo) {
        return To.toJson(entity, pojo);
    }

    public static JsonObject toFilters(final String[] columns, final Supplier<Object>... supplier) {
        return To.toFilters(columns, supplier);
    }

    public static <T> T fromJson(final JsonObject data, final Class<T> clazz) {
        return From.fromJson(data, clazz, "");
    }

    public static <T> T fromJson(final JsonObject data, final Class<T> clazz, final String pojo) {
        return From.fromJson(data, clazz, pojo);
    }

    public static JsonObject fromJson(final JsonObject data, final String pojo) {
        return From.fromJson(data, pojo);
    }

    // T -> JsonObject ( with convert )
    public static <T> JsonObject toJsonFun(final T entity, final Function<JsonObject, JsonObject> convert) {
        return To.toJson(entity, convert);
    }

    // JsonArray -> JsonObject ( with field grouped )
    public static <T> JsonObject toJsonByGroup(final JsonArray array, final String field) {
        return Calculator.groupBy(array, field);
    }

    // JsonArray -> Future<JsonObject>
    public static Future<JsonObject> toJsonFutureByGroup(final JsonArray array, final String field) {
        return Future.succeededFuture(Calculator.groupBy(array, field));
    }

    // Special Merge
    public static void appendJson(final JsonObject target, final JsonObject source) {
        Calculator.appendJson(target, source);
    }

    // ---------------------- Web Error Returned --------------------------
    // -> WebException direct
    public static WebException toError(final Class<? extends WebException> clazz, final Object... args) {
        return To.toError(clazz, args);
    }

    // -> WebException transfer
    public static WebException toError(final Class<?> clazz, final Throwable error) {
        return To.toError(clazz, error);
    }

    // ---------------------- JsonArray Returned --------------------------
    // -> List<T> -> JsonArray
    public static <T> JsonArray toArray(final List<T> list) {
        return To.toArray(list, "");
    }

    // -> List<T> -> JsonArray ( with Pojo )
    public static <T> JsonArray toArray(final List<T> list, final String pojo) {
        return To.toArray(list, pojo);
    }

    // -> List<T> -> JsonArray ( convert )
    public static <T> JsonArray toArrayFun(final List<T> list, final Function<JsonObject, JsonObject> convert) {
        return To.toArray(list, convert);
    }

    public static BiConsumer<JsonArray, Object> fnCollectJArray() {
        return Functions.fnCollectJArray();
    }

    // ---------------------- Envelop Returned --------------------------
    public static <T> Future<T> toFuture(final T entity) {
        return To.toFuture(entity);
    }

    public static <T, R> Future<R> toFuture(final T entity, final Supplier<R> defaultFun, final Function<T, R> actualFun) {
        return null == entity ? Future.succeededFuture(defaultFun.get()) : Future.succeededFuture(actualFun.apply(entity));
    }

    // ---------------------- If/Else Future ----------------------------
    public static <T> Future<T> toFuture(final Supplier<Future<T>> caseLine) {
        return Fn.future(caseLine);
    }

    public static <T> Future<T> toFuture(final Actuator executor, final Supplier<Future<T>> caseLine) {
        return Fn.future(executor, caseLine);
    }

    // ---------------------- Envelop Returned --------------------------
    // -> List<T> -> Envelop
    public static <T> Envelop to(final List<T> list) {
        return Envelop.success(Ux.toArray(list));
    }

    // -> Class<?> -> Envelop ( With failure )
    public static Envelop to(final Class<? extends WebException> clazz, final Object... args) {
        return To.toEnvelop(clazz, args);
    }

    // -> T -> Envelop
    public static <T> Envelop to(final T entity) {
        return To.toEnvelop(entity);
    }

    // -> T -> Envelop ( If entity is null, return Envelop.failure(error) )
    public static <T> Envelop to(final T entity, final WebException error) {
        return To.toEnvelop(entity, error);
    }

    // -> JsonArray -> Envelop ( To JsonObject, Result length must be 1 )
    public static Envelop toOne(final JsonArray array) {
        return Envelop.success(To.toUnique(array, ""));
    }

    // -> List<T> -> JsonObject ( Result length must be 1 )
    public static <T> JsonObject toUnique(final List<T> list) {
        return To.toUnique(Ux.toArray(list), "");
    }

    public static <T> JsonObject toUnique(final List<T> list, final String pojo) {
        return To.toUnique(Ux.toArray(list), pojo);
    }

    // -> JsonArray -> JsonObject ( Result length must be 1 )
    public static <T> JsonObject toUnique(final JsonArray array) {
        return To.toUnique(array, "");
    }

    // ---------------------- User Data -------------------------------------

    // -> Message<Envelop> -> String ( Security )
    public static String getUserID(final Message<Envelop> message, final String field) {
        return In.requestUser(message, field);
    }

    public static String getUserID(final Envelop envelop, final String field) {
        return In.requestUser(envelop, field);
    }

    // -> Message<Envelop> -> UUID ( Security )
    public static UUID getUserUUID(final Message<Envelop> message, final String field) {
        return UUID.fromString(getUserID(message, field));
    }

    public static UUID getUserUUID(final Envelop envelop, final String field) {
        return UUID.fromString(getUserID(envelop, field));
    }

    public static String getToken(final String token, final String field) {
        return In.requestTokenData(token, field);
    }

    // -> Message<Envelop> -> Session ( Key )
    public static Object getSession(final Message<Envelop> message, final String field) {
        return In.requestSession(message, field);
    }

    public static Object getSession(final Envelop envelop, final String field) {
        return In.requestSession(envelop, field);
    }

    // -> JsonArray ( JsonObject ) + JsonArray ( String ) -> add 'serial'

    public static JsonArray fillSerial(final JsonArray items, final JsonArray serials) {
        return In.assignValue(items, serials, "serial", false);
    }

    public static JsonArray assignSerial(final JsonArray items, final JsonArray serials) {
        return In.assignValue(items, serials, "serial", true);
    }

    public static JsonArray fillField(final JsonArray items, final JsonArray targets, final String field) {
        return In.assignValue(items, targets, field, false);
    }

    public static JsonArray assignField(final JsonArray items, final JsonArray targets, final String field) {
        return In.assignValue(items, targets, field, true);
    }

    public static void assignAuditor(final Object reference, final boolean isUpdate) {
        In.assignAuditor(reference, isUpdate);
    }

    // ---------------------- Function Generator --------------------------------------
    public static Future<JsonObject> fnRpc(final JsonArray array) {
        return UxRpc.fnRpc(array);
    }

    public static Future<JsonArray> fnRpc(final JsonObject data) {
        return UxRpc.fnRpc(data);
    }

    // ---------------------- Web Flow --------------------------------------
    public static <T> Handler<AsyncResult<T>> toHandler(final Message<Envelop> message) {
        return Web.toHandler(message);
    }

    public static <T> Handler<AsyncResult<Boolean>> toHandler(final Message<Envelop> message, final JsonObject data) {
        return Web.toHandler(message, data);
    }

    // ---------------------- Future --------------------------
    public static <T> Future<JsonObject> thenRpc(final String name, final String address, final JsonObject params) {
        return UxRpc.thenRpc(name, address, params);
    }

    public static <T> Future<JsonObject> thenRpc(final String name, final String address, final String field, final Object value) {
        return UxRpc.thenRpc(name, address, new JsonObject().put(field, value));
    }

    // ---------------------- New future ----------------------
    public static <T> Future<Envelop> then(final T entity) {
        return Future.succeededFuture(to(entity));
    }

    public static <T> Future<JsonArray> thenJsonMore(final List<T> list) {
        return Future.succeededFuture(To.toArray(list, ""));
    }

    public static <T> Future<JsonArray> thenJsonMore(final List<T> list, final String pojo) {
        return Future.succeededFuture(To.toArray(list, pojo));
    }

    public static <T> Future<JsonObject> thenJsonOne(final List<T> list, final String pojo) {
        return Future.succeededFuture(To.toUnique(new JsonArray(list), pojo));
    }

    public static <T> Future<JsonObject> thenJsonOne(final T entity, final String pojo) {
        return Future.succeededFuture(To.toJson(entity, pojo));
    }

    public static <T> Future<JsonObject> thenUpsert(final T entity,
                                                    final Supplier<Future<JsonObject>> supplier) {
        return Async.toUpsertFuture(entity, "", supplier, null);
    }

    public static <T> Future<JsonObject> thenUpsert(final T entity,
                                                    final Supplier<Future<JsonObject>> supplier,
                                                    final Function<JsonObject, JsonObject> updateFun) {
        return Async.toUpsertFuture(entity, "", supplier, updateFun);
    }

    public static <T> Future<JsonObject> thenUpsert(final T entity, final String pojo,
                                                    final Supplier<Future<JsonObject>> supplier) {
        return Async.toUpsertFuture(entity, pojo, supplier, null);
    }

    public static <T> Future<JsonObject> thenUpsert(final T entity, final String pojo,
                                                    final Supplier<Future<JsonObject>> supplier,
                                                    final Function<JsonObject, JsonObject> updateFun) {
        return Async.toUpsertFuture(entity, pojo, supplier, updateFun);
    }

    public static <T> Future<Envelop> thenMore(final List<T> list, final String pojo) {
        return Future.succeededFuture(Envelop.success(To.toArray(list, pojo)));
    }

    public static <T> Future<Envelop> thenOne(final T entity, final String pojo) {
        return Future.succeededFuture(Envelop.success(To.toJson(entity, pojo)));
    }

    // -> Consumer<Future<T>> -> Future<T>
    public static <T> Future<T> thenGeneric(final Consumer<Future<T>> consumer) {
        return Fn.thenGeneric(consumer);
    }

    public static <T> Future<T> thenGeneric(final Object result, final Future<T> future, final Throwable ex) {
        return Fn.thenGeneric(result, future, ex);
    }

    // ---------------------- Future --------------------------

    /**
     * Parallel generate
     * Source ->
     * source1 -> Future<1>
     * source2 -> Future<2>
     * For each element merge 1,2 -> 3
     *
     * @param source      List<F>
     * @param generateFun F -> Future<S>
     * @param mergeFun    Each element: (F,S) -> T
     * @param <F>         first
     * @param <S>         second
     * @param <T>         third
     * @return List<T>
     */
    public static <F, S, T> Future<List<T>> thenParallel(final Future<List<F>> source, final Function<F, Future<S>> generateFun, final BiFunction<F, S, T> mergeFun) {
        return Fluctuate.thenParallel(source, generateFun, mergeFun);
    }

    public static Future<JsonArray> thenParallelArray(final Future<JsonArray> source, final Function<JsonObject, Future<JsonObject>> generateFun, final BinaryOperator<JsonObject> operatorFun) {
        return Fluctuate.thenParallelArray(source, generateFun, operatorFun);
    }

    public static Future<JsonObject> thenParallelArray(final Future<JsonArray>... sources) {
        return Fluctuate.thenParallelArray(sources);
    }

    public static Future<JsonObject> thenParallelJson(final Future<JsonObject>... sources) {
        return Fluctuate.thenParallelJson(sources);
    }

    public static Future<JsonObject> thenParallelJson(final Future<JsonObject> source, final Function<JsonObject, List<Future>> generateFun, final BiConsumer<JsonObject, JsonObject>... operatorFun) {
        return Fluctuate.thenParallelJson(source, generateFun, operatorFun);
    }

    public static Future<JsonObject> thenParallelJson(final JsonObject source, final Function<JsonObject, List<Future>> generateFun, final BiConsumer<JsonObject, JsonObject>... operatorFun) {
        return Fluctuate.thenParallelJson(Future.succeededFuture(source), generateFun, operatorFun);
    }

    /**
     * Scatter generate
     * Source ->
     * source1 -> Future<List<1>>
     * source2 -> Future<List<2>>
     * Fore each element mergage 1, List<2> -> 3
     *
     * @param source      JsonArray
     * @param generateFun JsonObject -> Future<JsonArray>
     * @param mergeFun    Each element: JsonObject + JsonArray -> JsonObject
     * @return JsonArray
     */
    public static Future<JsonArray> thenScatterJson(final Future<JsonArray> source, final Function<JsonObject, Future<JsonArray>> generateFun, final BiFunction<JsonObject, JsonArray, JsonObject> mergeFun) {
        return Fluctuate.thenScatterJson(source, generateFun, mergeFun);
    }

    /**
     * Merge multi Future<> to single one, one for all module.
     * source ->
     * supplier1
     * supplier2
     * supplier3
     * .....
     * All suppliers will be executed after source, then return the final Future.
     *
     * @param mergeFun  How to merge source result and all supplier's results into final result:
     * @param source    Single Future --> F
     * @param suppliers Multi Futures --> List<S>
     * @param <F>       Type of source element
     * @param <S>       Type of supplier's list element
     * @param <T>       Type of return
     * @return Future<T> for final result.
     */
    public static <F, S, T> Future<T> thenComposite(final Future<F> source, final BiFunction<F, List<S>, T> mergeFun, final Supplier<Future<S>>... suppliers) {
        return Fluctuate.thenComposite(source, mergeFun, suppliers);
    }

    public static Future<JsonArray> thenComposite(final List<Future<JsonObject>> futures) {
        return Fluctuate.thenComposite(futures);
    }

    // -> Merge multi Future<> to single one, one for all module.
    public static <F, S, T> Future<T> thenComposite(final Future<F> source, final BiFunction<F, List<S>, T> mergeFun, final Function<F, Future<S>>... functions) {
        return Fluctuate.thenComposite(source, mergeFun, functions);
    }

    // -> IfElse true -> Future<T>, false -> Future<F>
    public static <T, F, R> Future<R> thenOtherwise(final Future<Boolean> condition, final Supplier<Future<T>> trueFuture, final Function<T, R> trueFun, final Supplier<Future<F>> falseFuture, final Function<F, R> falseFun) {
        return Fluctuate.thenOtherwise(condition, trueFuture, trueFun, falseFuture, falseFun);
    }

    // -> IfOr true -> Future<T>, false -> Future<R>
    public static <T, R> Future<R> thenError(final Future<Boolean> condition, final Supplier<Future<T>> trueFuture, final Function<T, R> trueFun, final Class<? extends WebException> clazz, final Object... args) {
        return Fluctuate.thenOtherwise(condition, trueFuture, trueFun, clazz, args);
    }

    // -> IfOr true -> Future<JsonObject>, false -> Future<JsonObject>
    public static Future<JsonObject> thenError(final Future<Boolean> condition, final Supplier<Future<JsonObject>> trueFuture, final Class<? extends WebException> clazz, final Object... args) {
        return Fluctuate.thenOtherwise(condition, trueFuture, item -> item, clazz, args);
    }

    // -> To error directly
    public static <T> Future<T> thenError(final Class<? extends WebException> clazz, final Object... args) {
        return Fluctuate.thenError(clazz, args);
    }

    // -> If only true -> Future<T>
    public static <T, R> Future<R> thenTrue(final Future<Boolean> condition, final Supplier<Future<T>> trueFuture, final Function<T, R> trueFun) {
        return Fluctuate.thenOtherwise(condition, trueFuture, trueFun, null);
    }

    // ---------------------- Request Data Extract --------------------------
    // -> Message<Envelop> -> T ( Interface mode )
    public static JsonArray getArray(final Message<Envelop> message, final int index) {
        return In.request(message, index, JsonArray.class);
    }

    public static JsonArray getArray(final Envelop envelop, final int index) {
        return In.request(envelop, index, JsonArray.class);
    }

    // -> Message<Envelop> -> T ( Interface mode )
    public static JsonArray getArray(final Message<Envelop> message) {
        return In.request(message, 0, JsonArray.class);
    }

    public static JsonArray getArray(final Envelop envelop) {
        return In.request(envelop, 0, JsonArray.class);
    }

    // -> Message<Envelop> -> T ( Interface mode )
    public static JsonArray getArray1(final Message<Envelop> message) {
        return In.request(message, 1, JsonArray.class);
    }

    public static JsonArray getArray1(final Envelop envelop) {
        return In.request(envelop, 1, JsonArray.class);
    }

    // -> Message<Envelop> -> T ( Interface mode )
    public static JsonArray getArray2(final Message<Envelop> message) {
        return In.request(message, 2, JsonArray.class);
    }

    public static JsonArray getArray2(final Envelop envelop) {
        return In.request(envelop, 2, JsonArray.class);
    }

    // -> Message<Envelop> -> T ( Interface mode )
    public static JsonArray getArray3(final Message<Envelop> message) {
        return In.request(message, 3, JsonArray.class);
    }

    public static JsonArray getArray3(final Envelop envelop) {
        return In.request(envelop, 3, JsonArray.class);
    }

    // -> Message<Envelop> -> String ( Interface mode )
    public static String getString(final Message<Envelop> message, final int index) {
        return In.request(message, index, String.class);
    }

    public static String getString(final Envelop envelop, final int index) {
        return In.request(envelop, index, String.class);
    }

    // -> Message<Envelop> -> String ( Interface mode )
    public static String getString(final Message<Envelop> message) {
        return In.request(message, 0, String.class);
    }

    public static String getString(final Envelop envelop) {
        return In.request(envelop, 0, String.class);
    }

    // -> Message<Envelop> -> String ( Interface mode )
    public static String getString1(final Message<Envelop> message) {
        return In.request(message, 1, String.class);
    }

    public static String getString1(final Envelop envelop) {
        return In.request(envelop, 1, String.class);
    }

    // -> Message<Envelop> -> String ( Interface mode )
    public static String getString2(final Message<Envelop> message) {
        return In.request(message, 2, String.class);
    }

    public static String getString2(final Envelop envelop) {
        return In.request(envelop, 2, String.class);
    }

    // -> Message<Envelop> -> String ( Interface mode )
    public static String getString3(final Message<Envelop> message) {
        return In.request(message, 3, String.class);
    }

    public static String getString3(final Envelop envelop) {
        return In.request(envelop, 3, String.class);
    }

    // -> Message<Envelop> -> JsonObject ( Interface mode )
    public static JsonObject getJson(final Message<Envelop> message, final int index) {
        return In.request(message, index, JsonObject.class);
    }

    public static JsonObject getJson(final Envelop envelop, final int index) {
        return In.request(envelop, index, JsonObject.class);
    }

    // -> Message<Envelop> -> JsonObject ( Interface mode )
    public static JsonObject getJson(final Message<Envelop> message) {
        return In.request(message, 0, JsonObject.class);
    }

    public static <T> T fromEnvelop(final Envelop envelop, final Class<T> clazz, final String pojo) {
        return From.fromJson(getJson(envelop), clazz, pojo);
    }

    public static JsonObject fromEnvelop(final Envelop envelop, final String pojo) {
        return From.fromJson(Ux.getJson(envelop), pojo);
    }

    public static JsonObject getJson(final Envelop envelop) {
        return In.request(envelop, 0, JsonObject.class);
    }

    public static Inquiry getInquiry(final JsonObject envelop) {
        return Query.getInquiry(envelop, "");
    }

    public static Inquiry getInquiry(final JsonObject envelop, final String pojo) {
        return Query.getInquiry(envelop, pojo);
    }

    // -> Message<Envelop> -> JsonObject ( Interface mode )
    public static JsonObject getJson1(final Message<Envelop> message) {
        return In.request(message, 1, JsonObject.class);
    }

    public static <T> T fromEnvelop1(final Envelop envelop, final Class<T> clazz, final String pojo) {
        return From.fromJson(getJson1(envelop), clazz, pojo);
    }

    public static JsonObject fromEnvelop1(final Envelop envelop, final String pojo) {
        return From.fromJson(Ux.getJson1(envelop), pojo);
    }

    public static JsonObject getJson1(final Envelop envelop) {
        return In.request(envelop, 1, JsonObject.class);
    }

    // -> Message<Envelop> -> JsonObject ( Interface mode )
    public static JsonObject getJson2(final Message<Envelop> message) {
        return In.request(message, 2, JsonObject.class);
    }

    public static JsonObject getJson2(final Envelop envelop) {
        return In.request(envelop, 2, JsonObject.class);
    }

    public static JsonObject fromEnvelop2(final Envelop envelop, final String pojo) {
        return From.fromJson(Ux.getJson2(envelop), pojo);
    }

    public static <T> T fromEnvelop2(final Envelop envelop, final Class<T> clazz, final String pojo) {
        return From.fromJson(getJson2(envelop), clazz, pojo);
    }

    // -> Message<Envelop> -> JsonObject ( Interface mode )
    public static JsonObject getJson3(final Message<Envelop> message) {
        return In.request(message, 3, JsonObject.class);
    }

    public static JsonObject getJson3(final Envelop envelop) {
        return In.request(envelop, 3, JsonObject.class);
    }

    public static JsonObject fromEnvelop3(final Envelop envelop, final String pojo) {
        return From.fromJson(Ux.getJson3(envelop), pojo);
    }

    public static <T> T fromEnvelop3(final Envelop envelop, final Class<T> clazz, final String pojo) {
        return From.fromJson(getJson3(envelop), clazz, pojo);
    }

    // -> Message<Envelop> -> Integer ( Interface mode )
    public static Integer getInteger(final Message<Envelop> message, final int index) {
        return In.request(message, index, Integer.class);
    }

    public static Integer getInteger(final Envelop envelop, final int index) {
        return In.request(envelop, index, Integer.class);
    }

    public static Integer getInteger(final Message<Envelop> message) {
        return In.request(message, 0, Integer.class);
    }

    public static Integer getInteger(final Envelop envelop) {
        return In.request(envelop, 0, Integer.class);
    }

    // -> Message<Envelop> -> Integer ( Interface mode )
    public static Integer getInteger1(final Message<Envelop> message) {
        return In.request(message, 1, Integer.class);
    }

    public static Integer getInteger1(final Envelop envelop) {
        return In.request(envelop, 1, Integer.class);
    }

    // -> Message<Envelop> -> Integer ( Interface mode )
    public static Integer getInteger2(final Message<Envelop> message) {
        return In.request(message, 2, Integer.class);
    }

    public static Integer getInteger2(final Envelop envelop) {
        return In.request(envelop, 2, Integer.class);
    }

    // -> Message<Envelop> -> Integer ( Interface mode )
    public static Integer getInteger3(final Message<Envelop> message) {
        return In.request(message, 3, Integer.class);
    }

    public static Integer getInteger3(final Envelop envelop) {
        return In.request(envelop, 3, Integer.class);
    }

    // -> Message<Envelop> -> Long ( Interface mode )
    public static Long getLong(final Message<Envelop> message, final int index) {
        return In.request(message, index, Long.class);
    }

    public static Long getLong(final Envelop envelop, final int index) {
        return In.request(envelop, index, Long.class);
    }

    // -> Message<Envelop> -> Long ( Interface mode )
    public static Long getLong(final Message<Envelop> message) {
        return In.request(message, 0, Long.class);
    }

    public static Long getLong(final Envelop envelop) {
        return In.request(envelop, 0, Long.class);
    }

    // -> Message<Envelop> -> Long ( Interface mode )
    public static Long getLong1(final Message<Envelop> message) {
        return In.request(message, 1, Long.class);
    }

    public static Long getLong1(final Envelop envelop) {
        return In.request(envelop, 1, Long.class);
    }

    // -> Message<Envelop> -> Long ( Interface mode )
    public static Long getLong2(final Message<Envelop> message) {
        return In.request(message, 2, Long.class);
    }

    public static Long getLong2(final Envelop envelop) {
        return In.request(envelop, 2, Long.class);
    }

    // -> Message<Envelop> -> Long ( Interface mode )
    public static Long getLong3(final Message<Envelop> message) {
        return In.request(message, 3, Long.class);
    }

    public static Long getLong3(final Envelop envelop) {
        return In.request(envelop, 3, Long.class);
    }

    // -> Message<Envelop> -> T ( Interface mode )
    public static <T> T getT(final Message<Envelop> message, final int index, final Class<T> clazz) {
        return In.request(message, index, clazz);
    }

    public static <T> T getT(final Envelop envelop, final int index, final Class<T> clazz) {
        return In.request(envelop, index, clazz);
    }

    // -> Message<Envelop> -> T ( Interface mode )
    public static <T> T getT(final Message<Envelop> message, final Class<T> clazz) {
        return In.request(message, 0, clazz);
    }

    public static <T> T getT(final Envelop envelop, final Class<T> clazz) {
        return In.request(envelop, 0, clazz);
    }

    // -> Message<Envelop> -> T ( Interface mode )
    public static <T> T getT1(final Message<Envelop> message, final Class<T> clazz) {
        return In.request(message, 1, clazz);
    }

    public static <T> T getT1(final Envelop envelop, final Class<T> clazz) {
        return In.request(envelop, 1, clazz);
    }

    // -> Message<Envelop> -> T ( Interface mode )
    public static <T> T getT2(final Message<Envelop> message, final Class<T> clazz) {
        return In.request(message, 2, clazz);
    }

    public static <T> T getT2(final Envelop envelop, final Class<T> clazz) {
        return In.request(envelop, 2, clazz);
    }

    // -> Message<Envelop> -> T ( Interface mode )
    public static <T> T getT3(final Message<Envelop> message, final Class<T> clazz) {
        return In.request(message, 3, clazz);
    }

    public static <T> T getT3(final Envelop envelop, final Class<T> clazz) {
        return In.request(envelop, 3, clazz);
    }

    // ---------------------- Agent mode usage --------------------------
    // -> Message<Envelop> -> JsonObject ( Agent mode )
    public static JsonObject getBody(final Message<Envelop> message) {
        return In.request(message, JsonObject.class);
    }

    // -> Envelop -> JsonObject ( Agent mode )
    public static JsonObject getBody(final Envelop envelop) {
        return In.request(envelop, JsonObject.class);
    }

    // -> Message<Envelop> -> T ( Agent mode )
    public static <T> T getBodyT(final Message<Envelop> message, final Class<T> clazz) {
        return In.request(message, clazz);
    }

    // -> Envelop -> T ( Agent mode )
    public static <T> T getBodyT(final Envelop envelop, final Class<T> clazz) {
        return In.request(envelop, clazz);
    }

    // ---------------------- Request Data Ending --------------------------
    public static <E, T> Future<E> rxContainer(final RxHod container, final E entity) {
        return Functions.fnSupplier(container, entity, null);
    }

    public static <E, T> Future<E> rxContainer(final RxHod container, final E entity, final Supplier<T> supplier) {
        return Functions.fnSupplier(container, entity, supplier);
    }

    public static <E, T> Future<E> rxContainer(final RxHod container, final E entity, final Consumer<T> consumer) {
        return Functions.fnConsumer(container, entity, consumer);
    }

    public static <E, T> Future<E> rxContainer(final RxHod container, final E entity, final Function<T, E> function) {
        return Functions.fnConsumer(container, entity, item -> function.apply((T) item));
    }

    public static <E, T> Future<E> rxContainer(final RxHod container, final E entity, final T target) {
        return Functions.fnSupplier(container, entity, () -> target);
    }

    // -> Jooq
    public static class Jooq {

        public static UxJooq on(final Class<?> clazz) {
            return Fn.pool(Cache.JOOQ, clazz, () -> new UxJooq(clazz));
        }
    }

    public static class Pool {

        public static UxPool on(final String name) {
            return Fn.pool(Cache.POOL, name, () -> new UxPool(name));
        }

        public static UxPool on() {
            return new UxPool();
        }
    }

    // -> Jwt
    public static class Jwt {

        public static String token(final JsonObject claims) {
            return UxJwt.generate(claims, new JWTOptions());
        }

        public static String token(final JsonObject claims, final Function<String, Buffer> funcBuffer) {
            return UxJwt.generate(claims, new JWTOptions(), funcBuffer);
        }

        public static JsonObject extract(final JsonObject vertxToken) {
            return UxJwt.extract(vertxToken.getString("jwt"));
        }

        public static JsonObject extract(final String token) {
            return UxJwt.extract(token);
        }

        public static JsonObject extract(final String token, final JsonObject config) {
            return UxJwt.extract(token, config);
        }

        public static JWT create(final JWTAuthOptions config) {
            return UxJwt.create(new JWTAuthOptions(config), Ut::ioBuffer);
        }

        public static JWT create(final JsonObject config) {
            return UxJwt.create(new JWTAuthOptions(config), Ut::ioBuffer);
        }

        public static JWT create(final JWTAuthOptions config, final Function<String, Buffer> funcBuffer) {
            return UxJwt.create(config, funcBuffer);
        }

        public static JWT create(final JsonObject config, final Function<String, Buffer> funcBuffer) {
            return UxJwt.create(new JWTAuthOptions(config), funcBuffer);
        }
    }

    // -> Mongo
    public static class Mongo {

        public static JsonObject termIn(final JsonObject filter, final String field, final JsonArray values) {
            return UxMongo.termIn(filter, field, values);
        }

        public static JsonObject termLike(final JsonObject filter, final String field, final String value) {
            return UxMongo.termLike(filter, field, value);
        }

        public static Future<Boolean> missing(final String collection, final JsonObject filter) {
            return UxMongo.missing(collection, filter);
        }

        public static Future<Boolean> existing(final String collection, final JsonObject filter) {
            return UxMongo.existing(collection, filter);
        }

        public static Future<JsonObject> insert(final String collection, final JsonObject data) {
            return UxMongo.insert(collection, data);
        }

        public static Future<JsonObject> findOne(final String collection, final JsonObject filter) {
            return UxMongo.findOne(collection, filter);
        }

        public static Future<JsonObject> findOne(final String collection, final JsonObject filter,
                                                 final String joinedCollection, final String joinedKey, final JsonObject additional,
                                                 final BinaryOperator<JsonObject> operatorFun) {
            return UxMongo.findOne(collection, filter, joinedCollection, joinedKey, additional, operatorFun);
        }

        public static Future<JsonObject> findOneAndReplace(final String collection, final JsonObject filter,
                                                           final String field, final Object value) {
            return UxMongo.findOneAndReplace(collection, filter, new JsonObject().put(field, value));
        }

        public static Future<JsonObject> findOneAndReplace(final String collection, final JsonObject filter,
                                                           final JsonObject data) {
            return UxMongo.findOneAndReplace(collection, filter, data);
        }

        public static Future<Long> removeDocument(final String collection, final JsonObject filter) {
            return UxMongo.removeDocument(collection, filter);
        }

        public static Future<JsonArray> findWithOptions(final String collection, final JsonObject filter,
                                                        final FindOptions options) {
            return UxMongo.findWithOptions(collection, filter, options);
        }

        public static Future<JsonArray> findWithOptions(final String collection, final JsonObject filter, final FindOptions options,
                                                        // Secondary Query
                                                        final String joinedCollection, final String joinedKey, final JsonObject additional,
                                                        final BinaryOperator<JsonObject> operatorFun) {
            return UxMongo.findWithOptions(collection, filter, options,
                    joinedCollection, joinedKey, additional, operatorFun);
        }

        public static Future<JsonArray> find(final String collection, final JsonObject filter) {
            return UxMongo.findWithOptions(collection, filter, new FindOptions());
        }
    }
}
