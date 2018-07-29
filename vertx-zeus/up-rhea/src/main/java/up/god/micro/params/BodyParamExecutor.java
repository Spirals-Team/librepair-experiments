package up.god.micro.params;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.BodyParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class BodyParamExecutor {

    @POST
    @Path("/param/body/json")
    public JsonObject sayJson(
            @BodyParam final JsonObject json
    ) {
        return json;
    }

    @POST
    @Path("/param/body/jarray")
    public JsonArray sayJArray(
            @BodyParam final JsonArray jarray
    ) {
        return jarray;
    }
}
