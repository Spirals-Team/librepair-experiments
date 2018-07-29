package up.god.micro.request;

import io.vertx.core.eventbus.Message;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class OneWayWorker {

    @Address("ZERO://ONE-WAY")
    public void process(final Message<Envelop> message) {
        final String item = message.body().data();
        System.out.println(item);
    }
}
