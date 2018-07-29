package up.god.micro.advanced;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

import javax.inject.Inject;

@Queue
public class SearchWorker {

    @Inject
    private transient SearchStub searchStub;

    @Address("ZERO://QUEUE/SEARCH")
    public Future<JsonObject> search(final Envelop envelop) {
        final JsonObject data = Ux.getJson(envelop);
        System.out.println(data);
        return this.searchStub.search(Ux.getInquiry(data, "tabular"));
    }
}
