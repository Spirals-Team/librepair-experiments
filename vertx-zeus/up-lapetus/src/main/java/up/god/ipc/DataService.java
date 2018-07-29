package up.god.ipc;

import io.vertx.core.json.JsonObject;

public class DataService implements DataStub{

    @Override
    public JsonObject getData(String params){
        return new JsonObject().put("params", params);
    }
}
