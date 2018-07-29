package up.god.micro.media;

import io.vertx.core.json.JsonObject;
import io.vertx.up.annotations.EndPoint;
import io.vertx.zero.eon.Values;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@EndPoint
@Path("/api")
public class ContentActor {

    @POST
    @Path("media/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public String sayJson(final JsonObject data) {
        return data.encode();
    }

    @POST
    @Path("media/xml")
    @Consumes(MediaType.APPLICATION_XML)
    public String sayXml(final byte[] data) {
        final String xml = new String(data, Values.CHARSET);
        System.out.println(xml);
        return xml;
    }
}
