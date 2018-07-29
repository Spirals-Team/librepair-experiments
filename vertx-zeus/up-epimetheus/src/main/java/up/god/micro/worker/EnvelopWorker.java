package up.god.micro.worker;

import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class EnvelopWorker {

    @Address("ZERO://IPC/NODE/ENVELOP1")
    @Ipc(to = "RPC://IPC/NODE/ENVELOP1", name = "ipc-hecate")
    public Envelop execute(final Envelop envelop) {
        final String name = Ux.getString(envelop);
        return Envelop.success(new JsonObject()
                .put("name", name)
                .put("originator", "ipc-epimetheus"));
    }
}
