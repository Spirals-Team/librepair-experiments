package io.vertx.up.micro.follow;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.up.aiki.Ux;
import io.vertx.up.atom.Envelop;
import io.vertx.up.log.Annal;
import io.vertx.up.micro.ipc.client.TunnelClient;
import io.vertx.up.web.ZeroSerializer;
import io.vertx.zero.eon.Values;
import io.zero.epic.Ut;

import java.lang.reflect.Method;

public class DynamicInvoker implements Invoker {

    private static final Annal LOGGER = Annal.get(DynamicInvoker.class);

    @Override
    public void ensure(final Class<?> returnType, final Class<?> paramCls) {
        // Verify
        final boolean valid =
                (void.class != returnType && Void.class != returnType);
        InvokerUtil.verify(!valid, returnType, paramCls, this.getClass());
    }

    @Override
    public void invoke(final Object proxy,
                       final Method method,
                       final Message<Envelop> message) {
        final Envelop envelop = message.body();
        LOGGER.info(Info.MSG_FUTURE, this.getClass(), method.getReturnType(), false);
        final Envelop returnValue = this.executeMethod(proxy, method, envelop);
        message.reply(returnValue);
    }

    private Envelop executeMethod(final Object proxy,
                                  final Method method,
                                  final Envelop envelop) {
        // Get type of parameter first element.
        final Class<?> argType = method.getParameterTypes()[Values.IDX];
        // Deserialization from message bus.
        final Object returnValue;
        if (Envelop.class == argType) {
            // Input type is Envelop, input directly
            returnValue = Ut.invoke(proxy, method.getName(), envelop);
        } else {
            final Object reference = envelop.data();
            // Non Direct
            final Object arguments = ZeroSerializer.getValue(argType, Ut.toString(reference));
            returnValue = Ut.invoke(proxy, method.getName(), arguments);
        }
        // Return type must not be Envelop because top layer has been finished checking.
        return Envelop.success(returnValue);
    }

    @Override
    public void next(final Object proxy,
                     final Method method,
                     final Message<Envelop> message,
                     final Vertx vertx) {
        final Envelop envelop = message.body();
        LOGGER.info(Info.MSG_FUTURE, this.getClass(), method.getReturnType(), true);
        final Envelop returnValue = this.executeMethod(proxy, method, envelop);
        TunnelClient.create(this.getClass())
                .connect(vertx)
                .connect(method)
                .send(returnValue)
                .compose(item -> Future.succeededFuture(Ux.to(item)))
                .setHandler(Ux.toHandler(message));
    }
}
