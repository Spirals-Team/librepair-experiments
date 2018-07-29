package io.vertx.up.micro.ipc.tower;

import io.vertx.core.Future;
import io.vertx.up.aiki.Ux;
import io.vertx.up.atom.Envelop;
import io.vertx.up.exception._500RpcTransitInvokeException;
import io.vertx.up.log.Annal;
import io.zero.epic.fn.JvmSupplier;

import java.lang.reflect.Method;

/**
 * Different return value to build Future<Envelop>
 */
class ReturnTransit {

    private static final Annal LOGGER = Annal.get(ReturnTransit.class);

    private static Object invoke(final JvmSupplier<Object> invokedSupplier,
                                 final Class<?> target,
                                 final Method method) {
        final Object returnValue;
        try {
            returnValue = invokedSupplier.get();
        } catch (final Throwable ex) {
            ex.printStackTrace();
            throw new _500RpcTransitInvokeException(target, method, ex);
        }
        return returnValue;
    }

    @SuppressWarnings("unchecked")
    static Future<Envelop> build(final JvmSupplier<Object> supplier,
                                 final Class<?> target,
                                 final Method method) {
        final Object returnValue = invoke(supplier, target, method);
        if (null == returnValue) {
            // Empty Future
            return Future.succeededFuture(Envelop.ok());
        }
        final Class<?> clazz = returnValue.getClass();
        final Future<Envelop> result;
        if (Future.class.isAssignableFrom(clazz)) {
            // Fix Async Server Issue
            final Class<?> tCls = clazz.getComponentType();
            if (Envelop.class == tCls) {
                // Future<Envelop>
                LOGGER.info(Info.MSG_FLOW, "Future<Envelop>", clazz);
                result = (Future<Envelop>) returnValue;
            } else {
                // Future<JsonObject> or Future<JsonArray>
                LOGGER.info(Info.MSG_FLOW, "Future<T>", clazz);
                final Future future = (Future) returnValue;
                return future.compose(item -> Future.succeededFuture(Ux.to(item)));
            }
        } else {
            if (Envelop.class == clazz) {
                // Envelop got
                LOGGER.info(Info.MSG_FLOW, "Envelop", clazz);
                result = Future.succeededFuture((Envelop) returnValue);
            } else {
                LOGGER.info(Info.MSG_FLOW, "Other Type", clazz);
                final Envelop envelop = Envelop.success(returnValue);
                result = Future.succeededFuture(envelop);
            }
        }
        return result;
    }
}
