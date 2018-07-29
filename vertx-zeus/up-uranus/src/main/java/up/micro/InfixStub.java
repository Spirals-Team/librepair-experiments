package up.micro;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public interface InfixStub {

    Future<JsonObject> login(final String type);
}
