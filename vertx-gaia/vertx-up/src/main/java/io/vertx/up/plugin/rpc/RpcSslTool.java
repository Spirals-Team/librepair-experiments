package io.vertx.up.plugin.rpc;

import io.grpc.ManagedChannel;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.grpc.VertxChannelBuilder;
import io.vertx.up.atom.flux.IpcData;
import io.vertx.up.eon.em.CertType;
import io.vertx.up.log.Annal;
import io.vertx.up.micro.ssl.TrustPipe;
import io.vertx.zero.marshal.node.Node;
import io.vertx.zero.marshal.node.ZeroUniform;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

public class RpcSslTool {

    private static final Annal LOGGER = Annal.get(RpcSslTool.class);

    private static final Node<JsonObject> node = Ut.singleton(ZeroUniform.class);

    /**
     * @param vertx  Vert.x instance
     * @param config configuration
     * @return ManagedChannel
     */
    public static ManagedChannel getChannel(final Vertx vertx,
                                            final JsonObject config) {
        final String rpcHost = config.getString(Key.HOST);
        final Integer rpcPort = config.getInteger(Key.PORT);
        final VertxChannelBuilder builder =
                VertxChannelBuilder
                        .forAddress(vertx, rpcHost, rpcPort);
        Fn.safeSemi(null != config.getValue(Key.SSL), LOGGER,
                () -> {
                    final JsonObject sslConfig = config.getJsonObject(Key.SSL);
                    if (null != sslConfig && !sslConfig.isEmpty()) {
                        final Object type = sslConfig.getValue("type");
                        final CertType certType = null == type ?
                                CertType.PEM : Ut.toEnum(CertType.class, type.toString());
                        final TrustPipe<JsonObject> pipe = TrustPipe.get(certType);
                        // Enable SSL
                        builder.useSsl(pipe.parse(sslConfig));
                    } else {
                        builder.usePlaintext(true);
                    }
                });
        final ManagedChannel channel = builder.build();
        LOGGER.info(Info.CLIENT_RPC, rpcHost, String.valueOf(rpcPort), String.valueOf(channel.hashCode()));
        return channel;
    }

    public static ManagedChannel getChannel(final Vertx vertx,
                                            final IpcData data) {
        final String grpcHost = data.getHost();
        final Integer grpcPort = data.getPort();
        LOGGER.info(Info.CLIENT_BUILD, grpcHost, String.valueOf(grpcPort));
        final VertxChannelBuilder builder =
                VertxChannelBuilder
                        .forAddress(vertx, grpcHost, grpcPort);
        // Ssl Required
        final JsonObject config = node.read();

        Fn.safeSemi(null != config && null != config.getValue("rpc"), LOGGER,
                () -> {
                    // Extension or Uniform
                    final JsonObject rpcConfig = config.getJsonObject("rpc");
                    final String name = data.getName();
                    final JsonObject ssl = RpcHelper.getSslConfig(name, rpcConfig);
                    if (ssl.isEmpty()) {
                        // Disabled SSL
                        builder.usePlaintext(true);
                    } else {
                        final Object type = ssl.getValue("type");
                        final CertType certType = null == type ?
                                CertType.PEM : Ut.toEnum(CertType.class, type.toString());
                        final TrustPipe<JsonObject> pipe = TrustPipe.get(certType);
                        // Enabled SSL
                        builder.useSsl(pipe.parse(ssl));
                    }
                });
        return builder.build();
    }
}
