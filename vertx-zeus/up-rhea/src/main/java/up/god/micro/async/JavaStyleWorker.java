package up.god.micro.async;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;

@Queue
public class JavaStyleWorker {

    @Address("ZERO://ASYNC/JAVA")
    public Envelop async(final Envelop input) {
        final String literal = input.data();
        final JsonObject data = new JsonObject()
                .put("result", "SUCCESS")
                .put("input", literal);
        return Envelop.success(data);
    }
}