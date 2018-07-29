package up.god.micro.request;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/api")
@EndPoint
public class AsyncActor {

    @Path("/async/event")
    @POST
    @Address("ZERO://EVENT")
    public JsonObject sendEvent(final JsonObject data) {
        return data;
    }
}
