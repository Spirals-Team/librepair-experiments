package up.micro;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.tp.plugin.ali.sms.SmsClient;
import io.vertx.tp.plugin.qiy.QiyClient;
import io.vertx.up.annotations.Address;
import io.vertx.up.annotations.Plugin;
import io.vertx.up.annotations.Queue;
import io.vertx.up.atom.Envelop;
import io.vertx.up.plugin.shared.SharedClient;

import javax.inject.Inject;

@Queue
public class InfixWorker {

    @Plugin
    private SharedClient<String, Object> sharedClient;
    @Plugin
    private SmsClient smsClient;
    @Plugin
    private QiyClient qiyClient;
    @Inject
    private transient InfixStub stub;

    @Address("ZERO://SHARED")
    public Future<JsonObject> shared(final Envelop envelop) {

        System.out.println(this.sharedClient);
        System.out.println(this.smsClient);
        System.out.println(this.qiyClient);
        return this.stub.login("Test");
    }
}
