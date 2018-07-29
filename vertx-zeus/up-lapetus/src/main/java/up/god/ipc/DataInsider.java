package up.god.ipc;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Ipc;
import io.vertx.up.atom.Envelop;

import javax.inject.Inject;

public class DataInsider {

    @Inject
    private transient DataStub stub;

    @Ipc("IPC://EVENT/DATA")
    public Future<Envelop> read(final Envelop envelop) {
        final JsonObject params = envelop.data();
        return Future.succeededFuture(Envelop.success(params.put("result", "Lapetus")));
    }
}
