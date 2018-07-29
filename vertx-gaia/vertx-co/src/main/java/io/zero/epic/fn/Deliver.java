package io.zero.epic.fn;

import io.vertx.zero.exception.ZeroRunException;
import io.zero.epic.Ut;

import java.util.function.Supplier;

/**
 * Convert exception type
 */
class Deliver {
    /**
     * @param supplier
     * @param runCls
     * @param <T>
     * @return
     */
    static <T> T execRun(final Supplier<T> supplier,
                         final Class<? extends ZeroRunException> runCls,
                         final Object... args) {
        T ret = null;
        try {
            ret = supplier.get();
        } catch (final Throwable ex) {
            final Object[] argument = Ut.elementAdd(args, ex);
            final ZeroRunException error = Ut.instance(runCls, argument);
            if (null != error) {
                throw error;
            }
        }
        return ret;
    }
}
