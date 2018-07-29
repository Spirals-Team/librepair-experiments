package up.god.micro.inject;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api")
public class SimpleActor {

    @Path("inject/simple")
    @GET
    @Address("ZERO://INJECT/SIMPLE")
    public JsonObject sayInject(
            @QueryParam("username") final String username
    ) {
        return new JsonObject()
                .put("age", 33)
                .put("username", username);
    }
}
