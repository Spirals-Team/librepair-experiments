package up.god.micro.request;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class OneWayActor {

    @POST
    @Path("request/one-way")
    @Address("ZERO://ONE-WAY")
    public String process(
            final JsonObject data
    ) {
        return data.encode();
    }
}
