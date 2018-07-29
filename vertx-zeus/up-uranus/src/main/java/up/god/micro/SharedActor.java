package up.god.micro;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class SharedActor {


    @Path("/shared")
    @GET
    public JsonObject sayShared() {

        return new JsonObject();
    }
}
