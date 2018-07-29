package up.god.micro.async;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class JavaStyleActor {

    @POST
    @Path("request/java")
    @Address("ZERO://ASYNC/JAVA")
    public String sayHell(final JsonObject data) {
        return data.encode();
    }
}