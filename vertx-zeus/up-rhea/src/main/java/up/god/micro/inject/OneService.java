package up.god.micro.inject;

import io.vertx.core.json.JsonObject;

public class OneService implements OneStub {
    @Override
    public JsonObject getData(final JsonObject input) {
        input.put("className", getClass().getName());
        return input;
    }
}
