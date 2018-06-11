/*
 * Copyright 2018 The Hekate Project
 *
 * The Hekate Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.hekate.coordinate.internal;

import io.hekate.cluster.ClusterTopology;
import io.hekate.coordinate.CoordinationFuture;
import io.hekate.coordinate.CoordinationHandler;
import io.hekate.coordinate.CoordinationProcess;
import io.hekate.core.HekateSupport;
import io.hekate.messaging.Message;
import io.hekate.messaging.MessagingChannel;
import io.hekate.util.StateGuard;
import io.hekate.util.async.AsyncUtils;
import io.hekate.util.async.Waiting;
import io.hekate.util.format.ToString;
import io.hekate.util.format.ToStringIgnore;
import java.util.concurrent.ExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DefaultCoordinationProcess implements CoordinationProcess {
    private static final Logger log = LoggerFactory.getLogger(DefaultCoordinationProcess.class);

    private static final boolean DEBUG = log.isDebugEnabled();

    private final String name;

    @ToStringIgnore
    private final CoordinationHandler handler;

    @ToStringIgnore
    private final ExecutorService async;

    @ToStringIgnore
    private final MessagingChannel<CoordinationProtocol> channel;

    @ToStringIgnore
    private final long failoverDelay;

    @ToStringIgnore
    private final StateGuard guard = new StateGuard(DefaultCoordinationProcess.class);

    @ToStringIgnore
    private final CoordinationFuture future = new CoordinationFuture();

    @ToStringIgnore
    private final HekateSupport hekate;

    @ToStringIgnore
    private DefaultCoordinationContext ctx;

    public DefaultCoordinationProcess(String name, HekateSupport hekate, CoordinationHandler handler, ExecutorService async,
        MessagingChannel<CoordinationProtocol> channel, long failoverDelay) {
        assert name != null : "Name is null.";
        assert hekate != null : "Hekate is null.";
        assert handler != null : "Protocol is null.";
        assert async != null : "Executor service is null.";
        assert channel != null : "Messaging channel is null.";

        this.name = name;
        this.hekate = hekate;
        this.handler = handler;
        this.async = async;
        this.channel = channel;
        this.failoverDelay = failoverDelay;
    }

    public void initialize() {
        guard.lockWrite();

        try {
            guard.becomeInitialized();

            async.execute(() -> {
                try {
                    if (DEBUG) {
                        log.debug("Initializing handler [process={}]", name);
                    }

                    handler.initialize();
                } catch (RuntimeException | Error e) {
                    log.error("Got an unexpected runtime error during coordination [process={}]", name, e);
                }
            });
        } finally {
            guard.unlockWrite();
        }
    }

    public Waiting terminate() {
        Waiting waiting;

        guard.lockWrite();

        try {
            if (guard.becomeTerminated()) {
                DefaultCoordinationContext localCtx = this.ctx;

                if (localCtx != null) {
                    localCtx.cancel();

                    async.execute(() -> {
                        try {
                            localCtx.postCancel();
                        } catch (RuntimeException | Error e) {
                            log.error("Got an unexpected runtime error during coordination [process={}]", name, e);
                        }

                        try {
                            if (DEBUG) {
                                log.debug("Terminating handler [process={}]", name);
                            }

                            handler.terminate();
                        } catch (RuntimeException | Error e) {
                            log.error("Got an unexpected runtime error during coordination [process={}]", name, e);
                        }
                    });
                }

                waiting = AsyncUtils.shutdown(async);

                future.cancel(false);
            } else {
                waiting = Waiting.NO_WAIT;
            }

            this.ctx = null;
        } finally {
            guard.unlockWrite();
        }

        return waiting;
    }

    public void processMessage(Message<CoordinationProtocol> msg) {
        assert msg != null : "Message is null.";

        guard.lockRead();

        try {
            DefaultCoordinationContext localCtx = this.ctx;

            if (guard.isInitialized() && localCtx != null) {
                async.execute(() -> {
                    try {
                        localCtx.processMessage(msg);
                    } catch (RuntimeException | Error e) {
                        log.error("Failed to process coordination request [message={}]", msg, e);

                        msg.reply(CoordinationProtocol.Reject.INSTANCE);
                    }
                });
            } else {
                if (DEBUG) {
                    log.debug("Rejected coordination request since process is not initialized [message={}]", msg.get());
                }

                msg.reply(CoordinationProtocol.Reject.INSTANCE);
            }
        } finally {
            guard.unlockRead();
        }
    }

    public void processTopologyChange(ClusterTopology newTopology) {
        assert newTopology != null : "New topology is null.";

        guard.lockWrite();

        try {
            if (guard.isInitialized()) {
                if (DEBUG) {
                    log.debug("Processing topology change [topology={}]", newTopology);
                }

                boolean topologyChanged = true;

                DefaultCoordinationContext oldCtx = this.ctx;

                if (oldCtx != null) {
                    if (oldCtx.topology().equals(newTopology)) {
                        topologyChanged = false;
                    } else {
                        oldCtx.cancel();

                        async.execute(() -> {
                            try {
                                oldCtx.postCancel();
                            } catch (RuntimeException | Error e) {
                                log.error("Got an unexpected runtime error during coordination [process={}]", name, e);
                            }
                        });
                    }
                }

                if (topologyChanged) {
                    DefaultCoordinationContext newCtx = new DefaultCoordinationContext(name, hekate, newTopology, channel, async, handler,
                        failoverDelay, () -> future.complete(this)
                    );

                    this.ctx = newCtx;

                    if (DEBUG) {
                        log.debug("Created new context [context={}]", newCtx);
                    }

                    async.execute(() -> {
                        try {
                            newCtx.coordinate();
                        } catch (RuntimeException | Error e) {
                            log.error("Got an unexpected runtime error during coordination [process={}]", name, e);
                        }
                    });
                } else {
                    if (DEBUG) {
                        log.debug("Topology not changed [process={}]", name);
                    }
                }
            }
        } finally {
            guard.unlockWrite();
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public CoordinationFuture future() {
        return future.fork();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends CoordinationHandler> T handler() {
        return (T)handler;
    }

    @Override
    public String toString() {
        return ToString.format(CoordinationProcess.class, this);
    }
}
