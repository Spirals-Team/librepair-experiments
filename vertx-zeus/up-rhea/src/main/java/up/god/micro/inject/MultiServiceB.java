package up.god.micro.inject;

import io.vertx.core.json.JsonObject;

import javax.inject.Named;

@Named("ServiceB")
public class MultiServiceB implements MultiStub {
    @Override
    public JsonObject getData(final JsonObject input) {
        input.put("className", getClass().getName());
        return input;
    }
}
