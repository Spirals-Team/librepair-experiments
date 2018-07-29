package up.god.micro.validation;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Codex;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.BodyParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api/jsr303")
public class BodyActor {

    @POST
    @Path("/advanced")
    public JsonObject testCodex(
            @BodyParam @Codex final JsonObject user
    ) {
        return user;
    }
}
