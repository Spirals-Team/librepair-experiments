package up.god.micro.fetch;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

import javax.inject.Inject;

@Queue
public class FetchListWorker {

    @Inject
    private transient FetchStub stub;

    @Address("ZERO://QUEUE/LIST/BY")
    public Future<JsonArray> fetchByType(final Envelop envelop) {
        final String type = Ux.getString(envelop);
        return this.stub.fetchByTypes(type);
    }

    @Address("ZERO://QUEUE/LIST/BY/TYPES")
    public Future<JsonArray> fetchByTypes(final Envelop envelop) {
        final JsonArray types = Ux.getArray(envelop);
        return this.stub.fetchByTypes(types.getList().toArray());
    }

    @Address("ZERO://QUEUE/LIST/BY/MULTI")
    public Future<JsonArray> fetchByMulti(final Envelop envelop) {
        final String type = Ux.getString(envelop);
        final String code = Ux.getString1(envelop);
        final JsonObject filters = new JsonObject();
        filters.put("S_TYPE", type).put("S_CODE", code);
        return this.stub.fetchByFilters(filters);
    }
}
