package io.vertx.up.aiki;

import io.vertx.up.log.Annal;
import io.zero.epic.Ut;
import io.zero.epic.fn.Actuator;

import java.util.function.Supplier;

class Debug {

    static <T> T timer(final Class<?> clazz, final Supplier<T> supplier) {
        final long start = System.nanoTime();
        final T ret = supplier.get();
        final long end = System.nanoTime();
        final Annal LOGGER = Annal.get(clazz);
        LOGGER.info("[ ZERO ] Time spend and Get: {0}ns", String.valueOf(end - start));
        return ret;
    }

    static void timer(final Class<?> clazz, final Actuator actuator) {
        final long start = System.nanoTime();
        actuator.execute();
        final long end = System.nanoTime();
        final Annal LOGGER = Annal.get(clazz);
        LOGGER.info("[ ZERO ] Time spend: {0}ns", String.valueOf(end - start));
    }

    static void monitor(final Object... objects) {
        for (final Object reference : objects) {
            Debug.monitor(reference);
        }
    }

    static void monitor(final Object object) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\t\t[ ZERO Debug ] ---> Start \n");
        builder.append("\t\t[ ZERO Debug ] object = ").append(object).append("\n");
        if (null != object) {
            builder.append("\t\t[ ZERO Debug ] type = ").append(object.getClass()).append("\n");
            builder.append("\t\t[ ZERO Debug ] json = ").append(Ut.serialize(object)).append("\n");
            builder.append("\t\t[ ZERO Debug ] toString = ").append(object.toString()).append("\n");
            builder.append("\t\t[ ZERO Debug ] hashCode = ").append(object.hashCode()).append("\n");
        }
        builder.append("\t\t[ ZERO Debug ] <--- End \n");
        System.err.println(builder.toString());
    }
}
