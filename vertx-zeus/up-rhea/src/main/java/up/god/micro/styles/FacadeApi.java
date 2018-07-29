package up.god.micro.styles;

import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@EndPoint
@Path("/api")
public interface FacadeApi {

    @GET
    @Path("/facade")
    @Address("ZERO://FACADE/STYLE")
    String sayFacade(
            @QueryParam("style") String style,
            @QueryParam("mode") String mode
    );
}
