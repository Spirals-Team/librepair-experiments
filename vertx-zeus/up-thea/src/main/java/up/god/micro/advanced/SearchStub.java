package up.god.micro.advanced;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.atom.query.Inquiry;

public interface SearchStub {

    Future<JsonObject> search(final Inquiry inquiry);
}
