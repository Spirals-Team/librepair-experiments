package up.god.micro.params;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;

@EndPoint
@Path("/api")
public class HeadParamExecutor {

    @POST
    @Path("/param/head")
    public JsonObject sayHead(
            @HeaderParam(HttpHeaders.CONTENT_TYPE) final String content,
            @HeaderParam(HttpHeaders.ACCEPT) final String accept
    ) {
        return new JsonObject()
                .put("content-type", content)
                .put("accept", accept);
    }
}
