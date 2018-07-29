package up.god.micro.tabular;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.up.aiki.Ux;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

import javax.inject.infix.MySql;

@Queue
public class DbWorker {

    @MySql
    private transient SQLClient client;

    @Address("ZERO://QUEUE/NATIVE/GET")
    public void sayDb(final Message<Envelop> message) {
        final String type = Ux.getString(message);
        this.client.queryWithParams("SELECT * FROM SYS_TABULAR WHERE S_TYPE=?",
                new JsonArray().add(type), handler -> {
                    // Success or Failure
                    if (handler.succeeded()) {
                        final ResultSet res = handler.result();
                        // Build result json array
                        for (final JsonArray item : res.getResults()) {
                            System.out.println(item);
                        }
                        message.reply(Envelop.success(res.getResults()));
                    } else {
                        // Replied with error, now only print stack
                        handler.cause().printStackTrace();
                        message.reply(Envelop.ok());
                    }
                });
    }
}
