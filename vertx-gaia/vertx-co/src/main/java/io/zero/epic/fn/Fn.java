package io.zero.epic.fn;

import io.vertx.core.Future;
import io.vertx.up.exception.WebException;
import io.vertx.up.log.Annal;
import io.vertx.zero.exception.ZeroException;
import io.vertx.zero.exception.ZeroRunException;
import io.zero.epic.fn.wait.Case;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Unique interface to call function
 */
@SuppressWarnings("all")
public class Fn {

    public static <T> Future<T> match(final Case.DefaultCase<T> defaultCase, final Case<T>... matchers) {
        return Wait.match(() -> defaultCase, matchers).second.get();
    }

    public static <T> Future<T> match(final Supplier<Case.DefaultCase<T>> defaultCase, final Case<T>... matchers) {
        return Wait.match(defaultCase, matchers).second.get();
    }

    public static <T> Case.DefaultCase<T> fork(final Supplier<Future<T>> caseLine) {
        return Case.DefaultCase.item(caseLine);
    }

    public static <T> Case.DefaultCase<T> fork(final Actuator actuator, final Supplier<Future<T>> caseLine) {
        if (null != actuator) actuator.execute();
        return Case.DefaultCase.item(caseLine);
    }

    public static <T> Case<T> branch(final Supplier<Future<T>> caseLine) {
        return Wait.branch(caseLine);
    }

    public static <T> Case<T> branch(final Actuator executor, final Supplier<Future<T>> caseLine) {
        return Wait.branch(executor, caseLine);
    }

    public static <T> Case<T> branch(final boolean condition, final Supplier<Future<T>> caseLine) {
        return Wait.branch(condition, caseLine);
    }

    public static <T> Case<T> branch(final boolean condition, final Actuator executor, final Supplier<Future<T>> caseLine) {
        return Wait.branch(condition, executor, caseLine);
    }

    public static <T> Future<T> future(final Supplier<Future<T>> caseLine) {
        return Wait.branch(caseLine).second.get();
    }

    public static <T> Future<T> future(final Actuator executor, final Supplier<Future<T>> caseLine) {
        return Wait.branch(executor, caseLine).second.get();
    }

    public static <T> Future<T> thenGeneric(final Consumer<Future<T>> consumer) {
        return Wait.then(consumer);
    }

    public static <T> Future<T> thenGeneric(final Object result, final Future<T> future, final Throwable ex) {
        return Wait.then(result, future, ex);
    }

    /**
     * ZeroException out.
     *
     * @param logger
     * @param zeroClass
     * @param args
     * @throws ZeroException
     */
    public static void outZero(
            final boolean condition,
            final Annal logger,
            final Class<? extends ZeroException> zeroClass,
            final Object... args
    ) throws ZeroException {
        if (condition) {
            Announce.outZero(logger, zeroClass, args);
        }
    }

    /**
     * ZeroException to RunZeroException
     *
     * @param actuator
     * @param logger
     */
    public static void outUp(
            final ZeroActuator actuator,
            final Annal logger
    ) {
        Announce.toRun(logger, actuator);
    }

    /**
     * ZeroRunException out.
     *
     * @param condition
     * @param logger
     * @param upClass
     * @param args
     */
    public static void outUp(
            final boolean condition,
            final Annal logger,
            final Class<? extends ZeroRunException> upClass,
            final Object... args
    ) {
        if (condition) {
            Announce.outUp(logger, upClass, args);
        }
    }

    /**
     * WebException out
     *
     * @param condition
     * @param logger
     * @param webClass
     * @param args
     */
    public static void outWeb(
            final boolean condition,
            final Annal logger,
            final Class<? extends WebException> webClass,
            final Object... args
    ) {
        if (condition) {
            Announce.outWeb(logger, webClass, args);
        }
    }

    /**
     * Defined jvm function to simply Exception function
     *
     * @param actuator
     * @param logger
     */
    public static void safeJvm(
            final JvmActuator actuator,
            final Annal logger
    ) {
        Defend.jvmVoid(actuator, logger);
    }

    public static void safeJvm(
            final JvmActuator actuator
    ) {
        Defend.jvmVoid(actuator, null);
    }

    /**
     * @param supplier
     * @param logger
     * @param <T>
     * @return
     */
    public static <T> T safeJvm(
            final JvmSupplier<T> supplier,
            final Annal logger
    ) {
        return Defend.jvmReturn(supplier, logger);
    }

    public static <T> T safeJvm(
            final JvmSupplier<T> supplier
    ) {
        return Defend.jvmReturn(supplier, null);
    }

    /**
     * @param supplier
     * @param logger
     * @param <T>
     * @return
     */
    public static <T> T safeZero(
            final ZeroSupplier<T> supplier,
            final Annal logger
    ) {
        return Defend.zeroReturn(supplier, logger);
    }

    /**
     * @param actuator
     * @param logger
     */
    public static void safeZero(
            final ZeroActuator actuator,
            final Annal logger
    ) {
        Defend.zeroVoid(actuator, logger);
    }

    /**
     * @param actuator
     */
    public static void safeNull(
            final Actuator actuator,
            final Object... input
    ) {
        Zero.exec(actuator, input);
    }

    /**
     * @param consumer
     * @param input
     * @param <T>
     */
    public static <T> void safeNull(
            final Consumer<T> consumer,
            final T input
    ) {
        Zero.exec(consumer, input);
    }


    public static void safeSemi(
            final boolean condition,
            final Annal logger,
            final Actuator tSupplier,
            final Actuator fSupplier
    ) {
        Semi.exec(condition, logger, tSupplier, fSupplier);
    }

    /**
     * @param condition
     * @param logger
     * @param tSupplier
     */
    public static void safeSemi(
            final boolean condition,
            final Annal logger,
            final Actuator tSupplier
    ) {
        Semi.exec(condition, logger, tSupplier, null);
    }

    /**
     * @param condition
     * @param logger
     * @param tSupplier
     */
    public static void safeSemi(
            final boolean condition,
            final Actuator tSupplier
    ) {
        Semi.exec(condition, null, tSupplier, null);
    }

    /**
     * @param supplier
     * @param runCls
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T transRun(
            final Supplier<T> supplier,
            final Class<? extends ZeroRunException> runCls,
            final Object... args
    ) {
        return Deliver.execRun(supplier, runCls, args);
    }

    /**
     * @param actuator
     * @param logger
     */
    public static void shuntRun(
            final Actuator actuator,
            final Annal logger
    ) {
        Announce.shuntRun(actuator, logger);
    }

    /**
     * @param actuator
     * @param input
     */
    public static void shuntZero(
            final ZeroActuator actuator,
            final Object... input
    ) throws ZeroException {
        Zero.execZero(actuator, input);
    }

    /**
     * @param supplier
     * @param input
     * @param <T>
     * @return
     */
    public static <T> T getJvm(
            final JvmSupplier<T> supplier,
            final Object... input
    ) {
        return Zero.getJvm(null, supplier, input);
    }

    /**
     * @param supplier
     * @param input
     * @param <T>
     * @return
     */
    public static <T> T getNull(
            final Supplier<T> supplier,
            final Object... input
    ) {
        return Zero.get(null, supplier, input);
    }

    /**
     * @param condition
     * @param logger
     * @param tSupplier
     * @param fSupplier
     * @param <T>
     * @return
     */
    public static <T> T getSemi(
            final boolean condition,
            final Annal logger,
            final Supplier<T> tSupplier,
            final Supplier<T> fSupplier
    ) {
        return Defend.zeroReturn(() -> Semi.execZero(condition,
                tSupplier::get, fSupplier::get), logger);
    }

    public static <T> T getSemi(
            final boolean condition,
            final Annal logger,
            final Supplier<T> tSupplier
    ) {
        return Defend.zeroReturn(() -> Semi.execZero(condition,
                tSupplier::get, null), logger);
    }

    /**
     * @param condition
     * @param tSupplier
     * @param fSupplier
     * @param <T>
     * @return
     * @throws ZeroException
     */
    public static <T> T getSemi(
            final boolean condition,
            final ZeroSupplier<T> tSupplier,
            final ZeroSupplier<T> fSupplier
    ) throws ZeroException {
        return Semi.execZero(condition, tSupplier, fSupplier);
    }

    /**
     * @param defaultValue
     * @param supplier
     * @param input
     * @param <T>
     * @return
     */
    public static <T> T getNull(
            final T defaultValue,
            final Supplier<T> supplier,
            final Object... input
    ) {
        return Zero.get(defaultValue, supplier, input);
    }

    /**
     * @param defaultValue
     * @param supplier
     * @param <T>
     * @return
     */
    public static <T> T getNull(
            final T defaultValue,
            final Supplier<T> supplier
    ) {
        return Semi.execReturn(supplier, defaultValue);
    }

    /**
     * @param defaultValue
     * @param supplier
     * @param input
     * @param <T>
     * @return
     */
    public static <T> T getJvm(
            final T defaultValue,
            final JvmSupplier<T> supplier,
            final Object... input
    ) {
        return Zero.getJvm(defaultValue, supplier, input);
    }

    /**
     * Pool
     *
     * @param pool
     * @param key
     * @param poolFn
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> V pool(
            final ConcurrentMap<K, V> pool,
            final K key,
            final Supplier<V> poolFn) {
        return Pond.exec(pool, key, poolFn);
    }

    /**
     * Thread pool
     *
     * @param pool
     * @param poolFn
     * @param <V>
     * @return
     */
    public static <V> V poolThread(
            final ConcurrentMap<String, V> pool,
            final Supplier<V> poolFn) {
        final String threadName = Thread.currentThread().getName();
        return Pond.exec(pool, threadName, poolFn);
    }

    /**
     * @param object
     * @param keyFn
     * @param valueFn
     * @param <K>
     * @param <V>
     * @param <E>
     * @return
     */
    public static <K, V, E> ConcurrentMap<K, List<V>> packet(
            final Collection<E> object,
            final Function<E, K> keyFn,
            final Function<E, V> valueFn
    ) {
        return Pond.group(object, keyFn, valueFn);
    }

    /**
     * @param object
     * @param keyFn
     * @param valueFn
     * @param <K>
     * @param <V>
     * @param <E>
     * @return
     */
    public static <K, V, E> ConcurrentMap<K, V> zipper(
            final E[] object,
            final Function<E, K> keyFn,
            final Function<E, V> valueFn) {
        return Pond.zipper(Arrays.asList(object), keyFn, valueFn);
    }

    /**
     * @param object
     * @param keyFn
     * @param valueFn
     * @param <K>
     * @param <V>
     * @param <E>
     * @return
     */
    public static <K, V, E> ConcurrentMap<K, V> zipper(
            final Collection<E> object,
            final Function<E, K> keyFn,
            final Function<E, V> valueFn
    ) {
        return Pond.zipper(object, keyFn, valueFn);
    }

    /**
     * @param reference
     * @param tranFn
     * @param supplier
     * @param <T>
     * @param <F>
     * @return
     */
    public static <T, F> T nullFlow(final F reference,
                                    final Function<F, T> tranFn,
                                    final Supplier<T> supplier) {
        return Zero.nullFlow(reference, tranFn, supplier);
    }

    /**
     * Spec function
     *
     * @return
     */
    public static <T> T nil() {
        return null;
    }
}
