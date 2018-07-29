package up.god.micro.jwt;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;
import io.vertx.up.secure.Security;

import javax.inject.Inject;

@Queue
public class LoginWorker {

    @Inject
    private transient Security security;

    @Address("ZERO://QUEUE/LOGIN")
    public Future<JsonObject> login(final Envelop envelop) {
        final JsonObject data = Ux.getJson(envelop);
        return Ux.Mongo.findOne("DB_USER", data)
                .compose(item -> this.security.store(item));
    }


    @Address("ZERO://QUEUE/JWT")
    public Future<JsonObject> secure(final Envelop envelop) {
        return Future.succeededFuture(new JsonObject());
    }
}
