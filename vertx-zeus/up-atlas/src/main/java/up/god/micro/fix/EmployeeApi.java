package up.god.micro.fix;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.BodyParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api")
public interface EmployeeApi {

    @Path("fix/envelop")
    @POST
    @Address("ZERO://FIX/ENVELOP")
    String sayEmployee(@BodyParam JsonObject data);
}
