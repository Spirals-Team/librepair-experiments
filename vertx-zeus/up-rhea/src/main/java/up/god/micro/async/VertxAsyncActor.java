package up.god.micro.async;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class VertxAsyncActor {
    @POST
    @Path("request/vertx/futureT")
    @Address("ZERO://ASYNC/VERTX/FUTURE_T")
    public JsonObject sayFutureT(final JsonObject data) {
        data.put("agent", "T");
        return data;
    }
}
