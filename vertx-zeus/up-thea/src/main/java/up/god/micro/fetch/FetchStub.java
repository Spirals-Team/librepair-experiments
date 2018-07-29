package up.god.micro.fetch;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public interface FetchStub {

    Future<JsonObject> fetchByName(String name);

    Future<JsonArray> fetchByTypes(Object... types);

    Future<JsonArray> fetchByFilters(JsonObject filters);
}
