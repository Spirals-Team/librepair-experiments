package up.micro;

import io.vertx.core.json.JsonObject;
import io.vertx.tp.plugin.ali.sms.SmsClient;
import io.vertx.tp.plugin.qiy.QiyClient;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Codex;
import io.vertx.up.annotations.EndPoint;
import io.vertx.up.annotations.Plugin;
import io.vertx.up.plugin.shared.SharedClient;

import javax.ws.rs.BodyParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@EndPoint
public class InfixApi {

    @Plugin
    private SharedClient<String, Object> sharedClient;
    @Plugin
    private SmsClient smsClient;
    @Plugin
    private QiyClient qiyClient;

    @Path("/api/say/{type}")
    @POST
    @Address("ZERO://SHARED")
    public JsonObject say(@PathParam("type") final String type,
                          @Codex @BodyParam final JsonObject data) {
        System.out.println(Thread.currentThread().getName() + Thread.currentThread().getId());
        return new JsonObject();
    }
}
