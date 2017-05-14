package cc.blynk.server.application;

import cc.blynk.server.Holder;
import cc.blynk.server.application.handlers.main.AppChannelStateHandler;
import cc.blynk.server.application.handlers.main.auth.AppLoginHandler;
import cc.blynk.server.application.handlers.main.auth.GetServerHandler;
import cc.blynk.server.application.handlers.main.auth.RegisterHandler;
import cc.blynk.server.application.handlers.sharing.auth.AppShareLoginHandler;
import cc.blynk.server.core.BaseServer;
import cc.blynk.server.core.protocol.handlers.decoders.MessageDecoder;
import cc.blynk.server.core.protocol.handlers.encoders.MessageEncoder;
import cc.blynk.server.handlers.common.UserNotLoggedHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * Class responsible for handling all Application connections and netty pipeline initialization.
 *
 * The Blynk Project.
 * Created by Dmitriy Dumanskiy.
 * Created on 2/1/2015.
 */
public class AppServer extends BaseServer {

    private final ChannelInitializer<SocketChannel> channelInitializer;

    public AppServer(Holder holder) {
        super(holder.props.getProperty("listen.address"), holder.props.getIntProperty("app.ssl.port"), holder.transportTypeHolder);

        final int appTimeoutSecs = holder.limits.APP_IDLE_TIMEOUT;
        final String[] loadBalancingIPs = holder.props.getCommaSeparatedValueAsArray("load.balancing.ips");

        final AppChannelStateHandler appChannelStateHandler = new AppChannelStateHandler(holder.sessionDao);
        final RegisterHandler registerHandler = new RegisterHandler(holder);
        final AppLoginHandler appLoginHandler = new AppLoginHandler(holder);
        final AppShareLoginHandler appShareLoginHandler = new AppShareLoginHandler(holder);
        final UserNotLoggedHandler userNotLoggedHandler = new UserNotLoggedHandler();
        final GetServerHandler getServerHandler = new GetServerHandler(holder, loadBalancingIPs);

        log.debug("app.socket.idle.timeout = {}", appTimeoutSecs);

        this.channelInitializer = new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                final ChannelPipeline pipeline = ch.pipeline();

                if (appTimeoutSecs > 0) {
                    pipeline.addLast("AReadTimeout", new ReadTimeoutHandler(appTimeoutSecs));
                }

                pipeline.addLast("ASSL", holder.sslContextHolder.sslCtx.newHandler(ch.alloc()))
                        .addLast("AChannelState", appChannelStateHandler)
                        .addLast("AMessageDecoder", new MessageDecoder(holder.stats))
                        .addLast("AMessageEncoder", new MessageEncoder(holder.stats))
                        .addLast("AGetServer", getServerHandler)
                        .addLast("ARegister", registerHandler)
                        .addLast("ALogin", appLoginHandler)
                        .addLast("AShareLogin", appShareLoginHandler)
                        .addLast("ANotLogged", userNotLoggedHandler);
            }
        };
    }

    @Override
    public ChannelInitializer<SocketChannel> getChannelInitializer() {
        return channelInitializer;
    }

    @Override
    protected String getServerName() {
        return "Application";
    }

    @Override
    public void close() {
        System.out.println("Shutting down Application SSL server...");
        super.close();
    }

}
