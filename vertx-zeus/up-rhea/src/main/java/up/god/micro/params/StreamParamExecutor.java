package up.god.micro.params;

import io.vertx.core.buffer.Buffer;
import io.vertx.up.annotations.EndPoint;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.StreamParam;
import java.nio.charset.Charset;

@EndPoint
@Path("/api")
public class StreamParamExecutor {

    @Path("param/bytes")
    @POST
    public String sayBytes(@StreamParam final byte[] bytes) {
        System.out.println(bytes.length);
        return new String(bytes, Charset.defaultCharset());
    }

    @Path("param/buffer")
    @POST
    public String sayBuffer(@StreamParam final Buffer buffer) {
        System.out.println(buffer.length());
        return buffer.toString();
    }
}
