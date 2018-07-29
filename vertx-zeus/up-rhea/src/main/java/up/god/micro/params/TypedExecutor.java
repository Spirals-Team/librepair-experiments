package up.god.micro.params;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public class TypedExecutor {

    @POST
    @Path("param/typed/json")
    public JsonObject sendJson(
            final JsonObject json) {
        System.out.println(json);
        return json;
    }

    @POST
    @Path("param/typed/jarray")
    public JsonArray sendArray(
            final JsonArray json) {
        System.out.println(json);
        return json;
    }

    @POST
    @Path("param/typed/request")
    public String sendObj(
            final HttpServerRequest request,
            final HttpServerResponse response
    ) {
        System.out.println(request);
        System.out.println(response);
        return "Hello, request/response.";
    }
}
