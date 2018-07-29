package up.god.micro.fetch;

import io.vertx.core.json.JsonArray;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.*;

@EndPoint
@Path("/api")
public interface FetchListApi {

    @Path("tabular/list/by/{type}")
    @GET
    @Address("ZERO://QUEUE/LIST/BY")
    String fetchByType(@PathParam("type") String type);

    @Path("tabular/list/by")
    @POST
    @Address("ZERO://QUEUE/LIST/BY/TYPES")
    JsonArray fetchByTypes(@BodyParam JsonArray types);

    @Path("tabular/list/multi")
    @GET
    @Address("ZERO://QUEUE/LIST/BY/MULTI")
    String fetchByMulti(@QueryParam("type") String type,
                        @QueryParam("code") String code);
}
