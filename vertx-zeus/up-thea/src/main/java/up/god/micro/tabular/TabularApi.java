package up.god.micro.tabular;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Codex;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.*;

@EndPoint
@Path("/api")
public interface TabularApi {

    @Path("tabular")
    @POST
    @Address("ZERO://QUEUE/TABULAR/CREATE")
    JsonObject create(@BodyParam @Codex JsonObject data);

    @Path("tabular/{id}")
    @PUT
    @Address("ZERO://QUEUE/TABULAR/UPDATE")
    JsonObject update(@PathParam("id") Long id, @BodyParam @Codex JsonObject data);


    @Path("tabular/{id}")
    @DELETE
    @Address("ZERO://QUEUE/TABULAR/DELETE")
    JsonObject delete(@PathParam("id") Long id);
}
