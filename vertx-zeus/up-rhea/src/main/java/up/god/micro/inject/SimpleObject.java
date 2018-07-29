package up.god.micro.inject;

import io.vertx.core.json.JsonObject;

public class SimpleObject {

    public JsonObject getData(final JsonObject data) {
        data.put("className", getClass().getName());
        return data;
    }
}
