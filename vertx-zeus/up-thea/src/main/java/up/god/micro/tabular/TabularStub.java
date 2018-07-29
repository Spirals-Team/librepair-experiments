package up.god.micro.tabular;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public interface TabularStub {

    Future<JsonObject> fetchOne(Long id);

    Future<JsonObject> create(JsonObject data);

    Future<JsonObject> update(Long id, JsonObject data);

    Future<JsonObject> delete(Long id);
}
