package io.vertx.up.atom.query;

import io.vertx.core.json.JsonObject;
import io.vertx.up.exception._400QueryKeyTypeException;
import io.vertx.up.log.Annal;
import io.zero.epic.fn.Fn;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Advanced Criteria Interface for query
 */
public interface Inquiry {
    // Criteria Keys
    String KEY_PAGER = "pager";
    String KEY_SORTER = "sorter";
    String KEY_CRITERIA = "criteria";
    String KEY_PROJECTION = "projection";

    /**
     * Create Inquiry
     *
     * @param data
     * @return
     */
    static Inquiry create(final JsonObject data) {
        return new IrInquiry(data);
    }

    /**
     * Query key checking for search operation
     *
     * @param checkJson
     * @param key
     * @param type
     * @param predicate
     * @param target
     */
    static void ensureType(final JsonObject checkJson,
                           final String key, final Class<?> type,
                           final Predicate<Object> predicate,
                           final Class<?> target) {
        Fn.safeNull(() -> {
            final Annal logger = Annal.get(target);
            Fn.safeNull(() -> Fn.safeSemi(checkJson.containsKey(key), logger, () -> {
                // Throw type exception
                final Object check = checkJson.getValue(key);
                Fn.outWeb(!predicate.test(check), logger,
                        _400QueryKeyTypeException.class, target,
                        key, type, check.getClass());
            }), checkJson);
        }, target);
    }

    /**
     * @param field
     * @param value
     */
    void setInquiry(String field, Object value);

    /**
     * Get projection
     *
     * @return Projection to do filter
     */
    Set<String> getProjection();

    /**
     * Get pager
     *
     * @return Pager for pagination
     */
    Pager getPager();

    /**
     * Get Sorter
     *
     * @return Sorter for order by
     */
    Sorter getSorter();

    /**
     * Get criteria
     *
     * @return
     */
    Criteria getCriteria();

    /**
     * To JsonObject
     *
     * @return
     */
    JsonObject toJson();

    interface Instant {
        String DAY = "day";
        String DATE = "date";
        String TIME = "time";
        String DATETIME = "datetime";
    }

    interface Op {
        String LT = "<";
        String LE = "<=";
        String GT = ">";
        String GE = ">=";
        String EQ = "=";
        String NEQ = "<>";
        String NOT_NULL = "!n";
        String NULL = "n";
        String TRUE = "t";
        String FALSE = "f";
        String IN = "i";
        String NOT_IN = "!i";
        String START = "s";
        String END = "e";
        String CONTAIN = "c";

        Set<String> VALUES = new HashSet<String>() {
            {
                this.add(LT);
                this.add(LE);
                this.add(GT);
                this.add(GE);
                this.add(EQ);
                this.add(NEQ);
                this.add(NOT_NULL);
                this.add(NULL);
                this.add(TRUE);
                this.add(FALSE);
                this.add(IN);
                this.add(NOT_IN);
                this.add(START);
                this.add(END);
                this.add(CONTAIN);
            }
        };
    }
}
