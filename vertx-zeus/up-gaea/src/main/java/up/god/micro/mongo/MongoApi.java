package up.god.micro.mongo;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.BodyParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public interface MongoApi {

    @Path("native/test")
    @POST
    @Address("ZERO://QUEUE/NATIVE/MONGO")
    JsonObject sayMongo(@BodyParam JsonObject params);
}
