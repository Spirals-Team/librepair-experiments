package up.god.micro.fetch;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

import javax.inject.Inject;

@Queue
public class FetchWorker {

    @Inject
    private transient FetchStub stub;

    @Address("ZERO://QUEUE/BY/NAME")
    public Future<JsonObject> byName(final Envelop envelop) {
        final String name = Ux.getString(envelop);
        return this.stub.fetchByName(name);
    }
}
