package up.god.micro.rpc;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;
import io.vertx.up.plugin.rpc.RpcClient;

import javax.inject.infix.Rpc;

@Queue
public class SingleWorker {

    @Rpc
    private transient RpcClient client;

    @Address("ZERO://RPC/FIRST")
    public void sayHello(final Message<Envelop> message) {
        final String name = Ux.getString(message);
        final JsonObject params = new JsonObject().put("name", name);
        this.client.connect("ipc-coeus", "RPC://SAY/HELLO", params, res -> {
            if (res.succeeded()) {
                message.reply(Envelop.success(res.result()));
            } else {
                res.cause().printStackTrace();
                message.reply(Envelop.failure(res.cause().getMessage()));
            }
        });
    }
}
