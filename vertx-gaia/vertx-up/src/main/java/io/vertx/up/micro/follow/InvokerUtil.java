package io.vertx.up.micro.follow;

import io.vertx.up.log.Annal;
import io.vertx.zero.eon.Values;
import io.vertx.zero.exception.AsyncSignatureException;
import io.vertx.zero.exception.WorkerArgumentException;
import io.zero.epic.fn.Fn;

import java.lang.reflect.Method;

/**
 * Tool for invoker do shared works.
 */
@SuppressWarnings("unused")
class InvokerUtil {
    /**
     * Whether this method is void
     *
     * @param method checked method
     * @return checked result
     */
    static boolean isVoid(final Method method) {
        final Class<?> returnType = method.getReturnType();
        return void.class == returnType || Void.class == returnType;
    }

    /**
     * Arguments verification
     *
     * @param method checked method.
     * @param target checked class
     */
    static void verifyArgs(final Method method,
                           final Class<?> target) {

        // 1. Ensure method length
        final Class<?>[] params = method.getParameterTypes();
        final Annal logger = Annal.get(target);
        // 2. The parameters
        Fn.outUp(Values.ONE != params.length,
                logger, WorkerArgumentException.class,
                target, method);
    }

    static void verify(
            final boolean condition,
            final Class<?> returnType,
            final Class<?> paramType,
            final Class<?> target) {
        final Annal logger = Annal.get(target);
        Fn.outUp(condition, logger,
                AsyncSignatureException.class, target,
                returnType.getName(), paramType.getName());
    }
}
