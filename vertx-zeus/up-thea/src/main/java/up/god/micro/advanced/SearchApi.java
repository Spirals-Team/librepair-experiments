package up.god.micro.advanced;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.BodyParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public interface SearchApi {
    @Path("tabular/search")
    @POST
    @Address("ZERO://QUEUE/SEARCH")
    String search(@BodyParam String name);
}
