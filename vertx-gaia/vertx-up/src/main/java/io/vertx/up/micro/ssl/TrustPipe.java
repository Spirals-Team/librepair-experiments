package io.vertx.up.micro.ssl;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.ClientOptionsBase;
import io.vertx.up.eon.em.CertType;
import io.vertx.up.micro.ssl.client.JksTrust;
import io.vertx.up.micro.ssl.client.PemTrust;
import io.vertx.up.micro.ssl.client.PfxTrust;
import io.zero.epic.Ut;

public interface TrustPipe<I> {

    static TrustPipe<JsonObject> get(final CertType type) {
        // 1. OpenSSL
        TrustPipe<JsonObject> pipe = null;
        switch (type) {
            case PKCS12:
                pipe = Ut.singleton(PfxTrust.class);
                break;
            case JKS:
                pipe = Ut.singleton(JksTrust.class);
                break;
            case PEM:
                pipe = Ut.singleton(PemTrust.class);
                break;
        }
        return pipe;
    }

    Handler<ClientOptionsBase> parse(I options);
}
