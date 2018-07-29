package up.god.micro.jwt;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.BodyParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/api")
@EndPoint
public interface LoginActor {

    @POST
    @Path("/login")
    @Address("ZERO://QUEUE/LOGIN")
    JsonObject login(@BodyParam final JsonObject data);

    @POST
    @Path("/secure/jwt")
    @Address("ZERO://QUEUE/JWT")
    JsonObject secure(@BodyParam final JsonObject data);
}
