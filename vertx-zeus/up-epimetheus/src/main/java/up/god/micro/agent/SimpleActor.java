package up.god.micro.agent;

import io.vertx.core.json.JsonObject;

public class SimpleActor implements SimpleApi {
    @Override
    public JsonObject saySimple(final String name) {
        return new JsonObject()
                .put("name", name)
                .put("originator", "ipc-epimetheus");
    }
}
