package io.vertx.up.plugin.rpc;

import io.grpc.ManagedChannel;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.Shareable;
import io.vertx.up.log.Annal;

class RpcHolder implements Shareable {
    private static final Annal LOGGER = Annal.get(RpcHolder.class);
    private final JsonObject config;
    private final Vertx vertx;
    private final Runnable closeRunner;
    private ManagedChannel channel;
    private int refCount = 1;

    public RpcHolder(
            final Vertx vertx,
            final JsonObject config,
            final Runnable closeRunner) {
        this.vertx = vertx;
        this.config = config;
        this.closeRunner = closeRunner;
    }

    synchronized ManagedChannel getChannel() {
        if (null == this.channel) {
            this.channel = RpcSslTool.getChannel(this.vertx, this.config);
        }
        return this.channel;
    }

    synchronized void incRefCount() {
        this.refCount++;
    }

    synchronized void close() {
        if (--this.refCount == 0) {
            if (this.channel != null) {
                this.channel.shutdownNow();
            }
            if (this.closeRunner != null) {
                this.closeRunner.run();
            }
        }
    }
}
