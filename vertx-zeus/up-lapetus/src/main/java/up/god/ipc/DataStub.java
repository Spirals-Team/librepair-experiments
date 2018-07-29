package up.god.ipc;

import io.vertx.core.json.JsonObject;

public interface DataStub {

    JsonObject getData(String params);
}
