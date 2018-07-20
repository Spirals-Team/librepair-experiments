package org.corfudb.runtime;

import java.time.Duration;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.corfudb.runtime.CorfuRuntime.CorfuRuntimeParameters;
import org.corfudb.runtime.clients.BaseHandler;
import org.corfudb.runtime.clients.IClientRouter;
import org.corfudb.runtime.clients.LayoutClient;
import org.corfudb.runtime.clients.LayoutHandler;
import org.corfudb.runtime.clients.ManagementClient;
import org.corfudb.runtime.clients.ManagementHandler;
import org.corfudb.runtime.clients.NettyClientRouter;
import org.corfudb.runtime.exceptions.AlreadyBootstrappedException;
import org.corfudb.runtime.view.Layout;
import org.corfudb.util.CFUtils;
import org.corfudb.util.NodeLocator;
import org.corfudb.util.Sleep;

/**
 * Utility to bootstrap a cluster.
 *
 * <p>Created by zlokhandwala on 1/19/18.
 */
@Slf4j
public class BootstrapUtil {

    /**
     * Bootstraps the given layout.
     * Attempts to bootstrap each node finite number of times.
     * If the retries are exhausted, the utility throws the responsible exception.
     *
     * @param layout       Layout to bootstrap the cluster.
     * @param retries      Number of retries to bootstrap each node before giving up.
     * @param retryTimeout Duration between retries.
     */
    public static void bootstrap(@NonNull Layout layout,
                                 int retries,
                                 @NonNull Duration retryTimeout) {
        bootstrap(layout, CorfuRuntimeParameters.builder().build(), retries, retryTimeout);
    }

    /**
     * Bootstraps the given layout.
     * Attempts to bootstrap each node finite number of times.
     * If the retries are exhausted, the utility throws the responsible exception.
     *
     * @param layout                 Layout to bootstrap the cluster.
     * @param corfuRuntimeParameters CorfuRuntimeParameters can specify security parameters.
     * @param retries                Number of retries to bootstrap each node before giving up.
     * @param retryTimeout           Duration between retries.
     */
    public static void bootstrap(@NonNull Layout layout,
                                 @NonNull CorfuRuntimeParameters corfuRuntimeParameters,
                                 int retries,
                                 @NonNull Duration retryTimeout) {
        for (String server : layout.getAllServers()) {
            int retry = retries;
            while (retry-- > 0) {
                try {
                    log.info("Attempting to bootstrap node:{} with layout:{}", server, layout);
                    IClientRouter router = new NettyClientRouter(NodeLocator.parseString(server),
                            corfuRuntimeParameters);
                    router.addClient(new LayoutHandler())
                            .addClient(new ManagementHandler())
                            .addClient(new BaseHandler());

                    LayoutClient layoutClient = new LayoutClient(router, layout.getEpoch());
                    ManagementClient managementClient
                            = new ManagementClient(router, layout.getEpoch());

                    try {
                        CFUtils.getUninterruptibly(layoutClient.bootstrapLayout(layout),
                                AlreadyBootstrappedException.class);
                    } catch (AlreadyBootstrappedException abe) {
                        if (!layoutClient.getLayout().get().equals(layout)) {
                            log.error("Layout Server already bootstrapped with different layout.");
                            throw abe;
                        }
                    }

                    try {
                        CFUtils.getUninterruptibly(managementClient.bootstrapManagement(layout),
                                AlreadyBootstrappedException.class);
                    } catch (AlreadyBootstrappedException abe) {
                        // Ignore Exception if already bootstrapped.
                    }
                    router.stop();
                    break;
                } catch (Exception e) {
                    log.error("Bootstrapping node:{} failed with exception:", server, e);
                    if (retry == 0) {
                        throw new RuntimeException(e);
                    }
                    log.warn("Retrying {} times in {}ms.", retry, retryTimeout.toMillis());
                    Sleep.MILLISECONDS.sleepUninterruptibly(retryTimeout.toMillis());
                }
            }
        }
        log.info("Bootstrapping layout:{} successful.", layout);
    }
}
