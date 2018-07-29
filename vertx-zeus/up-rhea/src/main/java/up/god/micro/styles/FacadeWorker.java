package up.god.micro.styles;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class FacadeWorker {

    @Address("ZERO://FACADE/STYLE")
    public Future<JsonObject> testFacade(final Envelop envelop) {
        final String arg0 = Ux.getString(envelop);
        final String arg1 = Ux.getString1(envelop);
        return Future.succeededFuture(new JsonObject()
                .put("style", arg0)
                .put("mode", arg1));
    }
}
