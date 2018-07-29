package up.god.micro.validation;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.EndPoint;
import io.zero.epic.Ut;
import up.god.micro.async.JavaJson;

import javax.validation.Valid;
import javax.ws.rs.BodyParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@EndPoint
@Path("/api/jsr303")
public class PojoActor {

    @Path("pojo")
    @POST
    public JsonObject sayPojo(
            @BodyParam @Valid final JavaJson json
    ) {
        return Ut.serializeJson(json);
    }
}
