package io.vertx.up.micro.follow;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.up.aiki.Ux;
import io.vertx.up.atom.Envelop;
import io.vertx.up.log.Annal;
import io.vertx.up.micro.ipc.client.TunnelClient;
import io.zero.epic.Ut;

import java.lang.reflect.Method;

/**
 * Envelop method(Envelop)
 */
public class SyncInvoker implements Invoker {

    private static final Annal LOGGER = Annal.get(SyncInvoker.class);

    @Override
    public void ensure(final Class<?> returnType,
                       final Class<?> paramCls) {
        // Verify
        final boolean valid =
                Envelop.class == returnType && paramCls == Envelop.class;
        InvokerUtil.verify(!valid, returnType, paramCls, this.getClass());
    }

    @Override
    public void invoke(final Object proxy,
                       final Method method,
                       final Message<Envelop> message) {
        // Invoke directly
        final Envelop envelop = message.body();
        LOGGER.info(Info.MSG_FUTURE, this.getClass(), method.getReturnType(), false);
        message.reply(Ut.invoke(proxy, method.getName(), envelop));
    }

    @Override
    public void next(final Object proxy,
                     final Method method,
                     final Message<Envelop> message,
                     final Vertx vertx) {
        final Envelop envelop = message.body();
        LOGGER.info(Info.MSG_FUTURE, this.getClass(), method.getReturnType(), true);
        final Envelop result = Ut.invoke(proxy, method.getName(), envelop);
        TunnelClient.create(this.getClass())
                .connect(vertx)
                .connect(method)
                .send(result)
                .compose(item -> Future.succeededFuture(Ux.to(item)))
                .setHandler(Ux.toHandler(message));
    }
}
