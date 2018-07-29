package up.micro;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.tp.plugin.ali.sms.SmsClient;
import io.vertx.tp.plugin.qiy.QiyClient;
import io.vertx.up.annotations.Plugin;
import io.vertx.up.plugin.shared.SharedClient;

public class InfixService implements InfixStub {

    @Plugin
    private SharedClient<String, Object> sharedClient;
    @Plugin
    private SmsClient smsClient;
    @Plugin
    private QiyClient qiyClient;

    @Override
    public Future<JsonObject> login(final String code) {
        System.out.println(this.sharedClient);
        System.out.println(this.smsClient);
        System.out.println(this.qiyClient);
        return Future.succeededFuture();
    }
}
