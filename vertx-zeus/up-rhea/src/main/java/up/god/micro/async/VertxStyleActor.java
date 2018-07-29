package up.god.micro.async;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class VertxStyleActor {

    @POST
    @Path("request/vertx/handler")
    @Address("ZERO://ASYNC/VERTX/HANDLER")
    public JsonObject sayHandler(final JsonObject data) {
        data.put("agent", "HANDLER");
        return data;
    }

    @POST
    @Path("request/vertx/future")
    @Address("ZERO://ASYNC/VERTX/FUTURE")
    public JsonObject sayFuture(final JsonObject data) {
        data.put("agent", "FUTURE");
        return data;
    }
}
