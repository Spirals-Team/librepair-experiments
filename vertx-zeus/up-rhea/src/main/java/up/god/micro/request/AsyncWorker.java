package up.god.micro.request;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class AsyncWorker {

    @Address("ZERO://EVENT")
    public Envelop reply(final Envelop envelop) {
        final JsonObject resource = envelop.data();
        System.out.println(resource);
        resource.put("result", "SUCCESS");
        return Envelop.success(resource);
    }
}
