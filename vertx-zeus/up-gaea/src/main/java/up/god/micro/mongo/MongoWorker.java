package up.god.micro.mongo;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

import javax.inject.infix.Mongo;

@Queue
public class MongoWorker {

    @Mongo
    private transient MongoClient client;

    @Address("ZERO://QUEUE/NATIVE/MONGO")
    public void sayMongo(final Message<Envelop> message) {
        final JsonObject data = Ux.getJson(message);
        this.client.insert("DB_TEST", data, res -> {
            if (res.succeeded()) {
                System.out.println(res.result());
                message.reply(Ux.to(data));
            } else {
                res.cause().printStackTrace();
                message.reply(Envelop.ok());
            }
        });
    }
}
