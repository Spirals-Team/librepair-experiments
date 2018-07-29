package io.vertx.up.micro.follow;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.up.aiki.Ux;
import io.vertx.up.atom.Envelop;
import io.vertx.up.log.Annal;
import io.vertx.up.micro.ipc.client.TunnelClient;
import io.vertx.zero.eon.Values;
import io.zero.epic.Ut;

import java.lang.reflect.Method;

/**
 * Future<T> method(I)
 */
public class AsyncInvoker implements Invoker {

    private static final Annal LOGGER = Annal.get(AsyncInvoker.class);

    @Override
    public void ensure(final Class<?> returnType, final Class<?> paramCls) {
        // Verify
        final boolean valid =
                (void.class != returnType && Void.class != returnType);
        InvokerUtil.verify(!valid, returnType, paramCls, this.getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(final Object proxy,
                       final Method method,
                       final Message<Envelop> message) {
        final Envelop envelop = message.body();
        // Get type of parameter first element
        final Class<?> argType = method.getParameterTypes()[Values.IDX];
        // Deserialization from message bus.
        final Class<?> returnType = method.getReturnType();
        LOGGER.info(Info.MSG_FUTURE, this.getClass(), returnType, false);
        // Get T
        final Class<?> tCls = returnType.getComponentType();
        if (Envelop.class == tCls) {
            // Input type is Envelop, input directly
            final Future<Envelop> result = Ut.invoke(proxy, method.getName(), envelop);
            result.setHandler(item -> message.reply(item.result()));
        } else {
            final Object reference = envelop.data();
            final Object arguments = Ut.deserialize(Ut.toString(reference), argType);
            final Future tResult = Ut.invoke(proxy, method.getName(), arguments);
            tResult.setHandler(Ux.toHandler(message));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void next(final Object proxy,
                     final Method method,
                     final Message<Envelop> message,
                     final Vertx vertx) {
        final Envelop envelop = message.body();
        // Get type of parameter first element
        final Class<?> argType = method.getParameterTypes()[Values.IDX];
        // Deserialization from message bus.
        final Class<?> returnType = method.getReturnType();
        LOGGER.info(Info.MSG_FUTURE, this.getClass(), returnType, true);
        // Get T
        final Class<?> tCls = returnType.getComponentType();
        if (Envelop.class == tCls) {
            // Input type is Envelop, input directly
            final Future<Envelop> result = Ut.invoke(proxy, method.getName(), envelop);
            result.compose(item -> TunnelClient.create(this.getClass())
                    .connect(vertx)
                    .connect(method)
                    .send(item))
                    .setHandler(Ux.toHandler(message));
        } else {
            final Object reference = envelop.data();
            final Object arguments = Ut.deserialize(Ut.toString(reference), argType);
            final Future future = Ut.invoke(proxy, method.getName(), arguments);
            future.compose(item -> TunnelClient.create(this.getClass())
                    .connect(vertx)
                    .connect(method)
                    .send(Ux.to(item)))
                    .compose(item -> Future.succeededFuture(Ux.to(item)))
                    .setHandler(Ux.toHandler(message));
        }
    }
}
