package up.god.micro.filter;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.BodyParam;
import javax.ws.rs.ContextParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/api")
@EndPoint
public class FilterAgent {

    @POST
    @Path("/jsr340/agent")
    public JsonObject filter(@BodyParam final JsonObject data,
                             @ContextParam("key") final String filtered) {
        return new JsonObject().put("filter", filtered)
                .mergeIn(data);
    }
}
