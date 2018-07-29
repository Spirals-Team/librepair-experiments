package up.god.micro.media;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@EndPoint
@Path("/api")
public class AcceptActor {

    @POST
    @Path("media/accept")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject sayJson(final JsonObject data) {
        return data;
    }
}
