package io.vertx.up.atom;

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpStatusCode;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.web.Session;
import io.vertx.up.exception.WebException;
import io.vertx.up.exception._500InternalServerException;
import io.vertx.up.kidd.Readible;
import io.vertx.up.log.Annal;
import io.vertx.up.web.ZeroSerializer;
import io.vertx.zero.eon.Strings;
import io.vertx.zero.eon.Values;
import io.vertx.zero.exception.IndexExceedException;
import io.zero.epic.fn.Fn;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Envelop implements Serializable {

    private static final Annal LOGGER = Annal.get(Envelop.class);

    private final HttpStatusCode status;
    private final WebException error;
    private final JsonObject data;
    private final Map<String, Object> context = new HashMap<>();
    private MultiMap headers;
    private User user;

    private Session session;

    private <T> Envelop(final T data, final HttpStatusCode status) {
        final Object serialized = ZeroSerializer.toSupport(data);
        final JsonObject bodyData = new JsonObject();
        bodyData.put(Key.DATA, serialized);
        this.data = bodyData;
        this.error = null;
        this.status = status;
    }

    private Envelop(final WebException error) {
        this.status = error.getStatus();
        this.error = error;
        this.data = error.toJson();
    }

    /**
     * Empty content success
     *
     * @return
     */
    public static Envelop ok() {
        return success(null);
    }

    public static <T> Envelop success(final T entity) {
        return new Envelop(entity, HttpStatusCode.OK);
    }

    public static Envelop failure(final String message) {
        return new Envelop(new _500InternalServerException(Envelop.class, message));
    }

    public static <T> Envelop success(final T entity, final HttpStatusCode status) {
        return new Envelop(entity, status);
    }

    /**
     * Failure response with exception
     *
     * @param error
     * @return
     */
    public static Envelop failure(final WebException error) {
        // Inner building error
        final Readible readible = Readible.get();
        readible.interpret(error);
        return new Envelop(error);
    }

    /**
     * Whether this envelop is valid.
     *
     * @return
     */
    public boolean valid() {
        return null == this.error;
    }

    /**
     * Extract data port to T
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T data() {
        if (null != this.data && this.data.containsKey(Key.DATA)) {
            final Object value = this.data.getValue(Key.DATA);
            if (null != value) {
                return (T) value;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Extract request part to t
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T data(final Class<T> clazz) {
        T reference = null;
        if (this.data.containsKey(Key.DATA)) {
            reference = this.extract(this.data.getValue(Key.DATA), clazz);
        }
        return reference;
    }

    /**
     * Extract data from context
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T context(final String key, final Class<T> clazz) {
        T reference = null;
        if (this.context.containsKey(key)) {
            reference = this.extract(this.context.get(key), clazz);
        }
        return reference;
    }

    /**
     * Extract request part to t ( Direct Mode )
     *
     * @param argIndex
     * @param <T>
     * @return
     */
    public <T> T data(final Integer argIndex, final Class<T> clazz) {
        T reference = null;
        Fn.outUp(0 > argIndex, LOGGER,
                IndexExceedException.class, this.getClass(), argIndex);
        if (this.data.containsKey(Key.DATA)) {
            final JsonObject raw = this.data.getJsonObject(Key.DATA);
            if (null != raw) {
                final String key = argIndex.toString();
                if (raw.containsKey(key)) {
                    reference = this.extract(raw.getValue(key), clazz);
                }
            }
        }
        return reference;
    }

    private JsonObject getData(final Integer argIndex) {
        JsonObject data = new JsonObject();
        final Object reference = Fn.getNull(null, () -> this.data.getValue(Key.DATA), this.data);
        if (reference instanceof JsonObject) {
            data = (JsonObject) reference;
        }
        if (null == argIndex) {
            if (data.containsKey(String.valueOf(Values.ZERO))) {
                data = data.getJsonObject(String.valueOf(Values.ZERO));
            }
        } else {
            data = data.getJsonObject(String.valueOf(argIndex));
        }
        return data;
    }

    public void setValue(final String field, final Object value) {
        this.setValue(null, field, value);
    }

    public void setValue(final Integer argIndex, final String field, final Object value) {
        final JsonObject reference = this.getData(argIndex);
        if (null != reference) {
            reference.put(field, value);
        }
    }

    /**
     * Result
     *
     * @param value
     * @param clazz
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    private <T> T extract(final Object value, final Class<T> clazz) {
        T reference = null;
        if (null != value) {
            final Object result = ZeroSerializer.getValue(clazz, value.toString());
            reference = Fn.getNull(() -> (T) result, result);
        }
        return reference;
    }

    /**
     * Convert to response
     *
     * @return
     */
    public String response() {
        final JsonObject response;
        if (null == this.error) {
            response = this.data;
        } else {
            response = this.error.toJson();
        }
        return response.encode();
    }

    public Future<Envelop> toFuture() {
        return Future.succeededFuture(this);
    }

    public HttpStatusCode status() {
        return this.status;
    }

    public User user() {
        return this.user;
    }

    public MultiMap headers() {
        return this.headers;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public void setHeaders(final MultiMap headers) {
        this.headers = headers;
    }

    // ------------------ Failure resource model ------------------

    public Session getSession() {
        return this.session;
    }

    public void setSession(final Session session) {
        this.session = session;
    }

    public Map<String, Object> context() {
        return this.context;
    }

    public void setContext(final Map<String, Object> data) {
        this.context.putAll(data);
    }

    /**
     * Read user's identifier
     *
     * @return
     */
    public String identifier(final String field) {
        return Fn.getJvm(Strings.EMPTY, () -> {
            final JsonObject credential = this.user.principal();
            return Fn.getSemi(null != credential && credential.containsKey(field),
                    () -> credential.getString(field),
                    () -> Strings.EMPTY);
        }, this.user);
    }

    @Override
    public String toString() {
        return "Envelop{" +
                "status=" + this.status +
                ", headers=" + this.headers +
                ", error=" + this.error +
                ", data=" + this.data +
                ", user=" + this.user +
                '}';
    }
}
