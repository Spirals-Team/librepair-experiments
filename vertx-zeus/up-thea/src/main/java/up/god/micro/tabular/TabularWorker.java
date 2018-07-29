package up.god.micro.tabular;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

import javax.inject.Inject;

@Queue
public class TabularWorker {

    @Inject
    private transient TabularStub stub;

    @Address("ZERO://QUEUE/TABULAR/ID")
    Future<JsonObject> get(final Envelop envelop) {
        final Long id = Ux.getLong(envelop);
        // First version
        return this.stub.fetchOne(id);
    }

    @Address("ZERO://QUEUE/TABULAR/CREATE")
    Future<JsonObject> create(final Envelop envelop) {
        final JsonObject data = Ux.getJson(envelop);
        return this.stub.create(data);
    }

    @Address("ZERO://QUEUE/TABULAR/UPDATE")
    Future<JsonObject> update(final Envelop envelop) {
        final Long id = Ux.getLong(envelop);
        final JsonObject data = Ux.getJson1(envelop);
        return this.stub.update(id, data);
    }

    @Address("ZERO://QUEUE/TABULAR/DELETE")
    Future<JsonObject> delete(final Envelop envelop) {
        final Long id = Ux.getLong(envelop);
        return this.stub.delete(id);
    }
}
