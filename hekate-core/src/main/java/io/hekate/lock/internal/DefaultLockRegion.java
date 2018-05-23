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

package io.hekate.lock.internal;

import io.hekate.cluster.ClusterFilters;
import io.hekate.cluster.ClusterHash;
import io.hekate.cluster.ClusterNode;
import io.hekate.cluster.ClusterNodeId;
import io.hekate.cluster.ClusterTopology;
import io.hekate.cluster.ClusterView;
import io.hekate.core.internal.util.ArgAssert;
import io.hekate.failover.FailoverPolicyBuilder;
import io.hekate.lock.DistributedLock;
import io.hekate.lock.LockOwnerInfo;
import io.hekate.lock.LockRegion;
import io.hekate.lock.internal.LockProtocol.LockOwnerRequest;
import io.hekate.lock.internal.LockProtocol.LockOwnerResponse;
import io.hekate.lock.internal.LockProtocol.LockResponse;
import io.hekate.lock.internal.LockProtocol.MigrationApplyRequest;
import io.hekate.lock.internal.LockProtocol.MigrationPrepareRequest;
import io.hekate.lock.internal.LockProtocol.MigrationRequest;
import io.hekate.lock.internal.LockProtocol.MigrationResponse;
import io.hekate.lock.internal.LockProtocol.UnlockRequest;
import io.hekate.lock.internal.LockProtocol.UnlockResponse;
import io.hekate.messaging.Message;
import io.hekate.messaging.MessagingChannel;
import io.hekate.messaging.unicast.ReplyDecision;
import io.hekate.messaging.unicast.Response;
import io.hekate.messaging.unicast.ResponseCallback;
import io.hekate.metrics.local.LocalMetricsService;
import io.hekate.partition.PartitionMapper;
import io.hekate.util.format.ToString;
import io.hekate.util.format.ToStringIgnore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.hekate.messaging.unicast.ReplyDecision.COMPLETE;
import static io.hekate.messaging.unicast.ReplyDecision.REJECT;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toSet;

class DefaultLockRegion implements LockRegion {
    private enum Status {
        ACTIVE,

        MIGRATING,

        TERMINATED
    }

    static final long TIMEOUT_IMMEDIATE = -1;

    static final long TIMEOUT_UNBOUND = 0;

    private static final Logger log = LoggerFactory.getLogger(DefaultLockRegion.class);

    private static final boolean DEBUG = log.isDebugEnabled();

    private final String regionName;

    @ToStringIgnore
    private final ScheduledExecutorService scheduler;

    @ToStringIgnore
    private final ClusterNodeId localNode;

    @ToStringIgnore
    private final LockRegionMetrics metrics;

    @ToStringIgnore
    private final MessagingChannel<LockProtocol> lockChannel;

    @ToStringIgnore
    private final MessagingChannel<LockProtocol> migrationChannel;

    @ToStringIgnore
    private final AtomicLong lockIdGen = new AtomicLong();

    @ToStringIgnore
    private final AtomicLong keyIdGen = new AtomicLong();

    @ToStringIgnore
    private final ReentrantReadWriteLock.ReadLock readLock;

    @ToStringIgnore
    private final ReentrantReadWriteLock.WriteLock writeLock;

    @ToStringIgnore
    private final Map<Long, LockControllerClient> lockClients = new ConcurrentHashMap<>();

    @ToStringIgnore
    private final ConcurrentMap<String, LockControllerServer> lockServers = new ConcurrentHashMap<>();

    @ToStringIgnore
    private final Object lockServersMux = new Object();

    @ToStringIgnore
    private final Set<LockControllerClient> deferredLocks = new LinkedHashSet<>();

    @ToStringIgnore
    private final CountDownLatch initMigration = new CountDownLatch(1);

    @ToStringIgnore
    private Status status = Status.MIGRATING;

    @ToStringIgnore
    private LockMigrationKey migrationKey;

    @ToStringIgnore
    private PartitionMapper activeMapping;

    @ToStringIgnore
    // Volatile since can be accessed both in locked and unlocked contexts.
    private volatile PartitionMapper latestMapping;

    @ToStringIgnore
    private LockMigrationCallback migrationCallback;

    public DefaultLockRegion(
        String regionName,
        ClusterNodeId localNode,
        ScheduledExecutorService scheduler,
        long retryInterval,
        LocalMetricsService metricsService,
        MessagingChannel<LockProtocol> channel
    ) {
        assert regionName != null : "Region name is null.";
        assert localNode != null : "Local node is null.";
        assert scheduler != null : "Scheduler is null.";
        assert channel != null : "Messaging channel is null.";

        this.regionName = regionName;
        this.scheduler = scheduler;
        this.localNode = localNode;

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        readLock = lock.readLock();
        writeLock = lock.writeLock();

        // Configure messaging channel for locking operations.
        lockChannel = channel.withFailover(new FailoverPolicyBuilder()
            .withConstantRetryDelay(retryInterval)
            .withAlwaysReRoute()
            .withRetryUntil(failover -> !isTerminated())
        );

        // Configure messaging channel for locks migration.
        migrationChannel = channel.withAffinity(regionName)
            .filterAll(ClusterFilters.forNextInJoinOrder()) // <-- use ring-based communications.
            .withFailover(new FailoverPolicyBuilder()
                .withAlwaysReRoute()
                .withConstantRetryDelay(retryInterval)
                .withRetryUntil(failover -> !isTerminated())
            );

        // Configure metrics.
        if (metricsService == null) {
            metrics = null;
        } else {
            metrics = new LockRegionMetrics(regionName, metricsService);
        }
    }

    @Override
    public String name() {
        return regionName;
    }

    @Override
    public DistributedLock get(String name) {
        ArgAssert.notNull(name, "Lock name");

        return new DefaultDistributedLock(name, this);
    }

    @Override
    public Optional<LockOwnerInfo> ownerOf(String lockName) throws InterruptedException {
        ArgAssert.notNull(lockName, "Lock name");

        if (!awaitForInitialMigration()) {
            return Optional.empty();
        }

        CompletableFuture<Optional<LockOwnerInfo>> future = new CompletableFuture<>();

        LockOwnerRequest request = new LockOwnerRequest(regionName, lockName);

        lockChannel.withAffinity(new LockKey(regionName, lockName)).request(request, new ResponseCallback<LockProtocol>() {
            @Override
            public ReplyDecision accept(Throwable err, Response<LockProtocol> reply) {
                if (err == null) {
                    LockOwnerResponse lockReply = reply.get(LockOwnerResponse.class);

                    if (lockReply.status() == LockOwnerResponse.Status.OK) {
                        ClusterNodeId ownerId = lockReply.owner();

                        if (ownerId == null) {
                            future.complete(Optional.empty());
                        } else {
                            ClusterTopology topology = lockChannel.cluster().topology();

                            ClusterNode ownerNode = topology.get(ownerId);

                            // Check that lock owner is in the local topology.
                            // It could be removed while we were waiting for response.
                            if (ownerNode != null) {
                                DefaultLockOwnerInfo info = new DefaultLockOwnerInfo(lockReply.threadId(), ownerNode);

                                future.complete(Optional.of(info));
                            }
                        }
                    }
                }

                return future.isDone() ? COMPLETE : REJECT;
            }

            @Override
            public void onComplete(Throwable err, Response<LockProtocol> rsp) {
                // All attempts failed which means that manager is terminated.
                if (err != null) {
                    future.complete(Optional.empty());
                }
            }
        });

        try {
            return future.get();
        } catch (InterruptedException e) {
            future.cancel(false);

            throw e;
        } catch (ExecutionException e) {
            // Never happens.
            throw new AssertionError("Unexpected error while requesting for lock owner info.", e);
        }
    }

    @Override
    public ClusterView cluster() {
        return lockChannel.cluster();
    }

    public LockControllerClient lock(long timeout, DistributedLock lock) {
        return lock(timeout, lock, null);
    }

    public LockControllerClient lock(long timeout, DistributedLock lock, AsyncLockCallbackAdaptor callback) {
        assert timeout >= 0 || timeout == -1 : "Unexpected timeout value [value=" + timeout + ']';

        readLock.lock();

        try {
            long threadId = Thread.currentThread().getId();

            long lockId = lockIdGen.incrementAndGet();

            // Create lock controller.
            LockControllerClient lockClient = new LockControllerClient(
                lockId,
                localNode,
                threadId,
                lock,
                lockChannel,
                timeout,
                metrics,
                callback,
                unlocked -> { // On unlock.
                    lockClients.remove(lockId);

                    synchronized (deferredLocks) {
                        deferredLocks.remove(unlocked);
                    }
                });

            if (status == Status.TERMINATED) {
                if (DEBUG) {
                    log.debug("Rejected locking since region is in {} state [lock={}]", status, lock);
                }

                LockFuture lockFuture = lockClient.lockFuture();

                if (timeout == TIMEOUT_IMMEDIATE) {
                    lockFuture.complete(false);
                } else {
                    lockFuture.completeExceptionally(new CancellationException("Lock service terminated."));
                }

                lockClient.unlockFuture().complete(true);
            } else {
                if (activeMapping == null) {
                    if (DEBUG) {
                        log.debug("Deferred lock acquisition since initial migration is not completed yet [lock={}]", lockClient);
                    }

                    synchronized (deferredLocks) {
                        deferredLocks.add(lockClient);
                    }
                } else {
                    if (DEBUG) {
                        log.debug("Locking [lock={}]", lockClient);
                    }

                    lockClient.update(activeMapping);

                    lockClients.put(lockId, lockClient);

                    lockClient.becomeLocking();
                }
            }

            return lockClient;
        } finally {
            readLock.unlock();
        }
    }

    public LockFuture unlock(long lockId) {
        readLock.lock();

        try {
            if (status == Status.TERMINATED) {
                if (DEBUG) {
                    log.debug("Rejected unlocking since region is in {} state [region={}, lock-id={}]", regionName, status, lockId);
                }

                LockFuture future = new LockFuture(null);

                future.complete(true);

                return future;
            } else {
                if (activeMapping == null) {
                    LockControllerClient deferred = null;

                    synchronized (deferredLocks) {
                        for (Iterator<LockControllerClient> it = deferredLocks.iterator(); it.hasNext(); ) {
                            LockControllerClient client = it.next();

                            if (client.lockId() == lockId) {
                                if (DEBUG) {
                                    log.debug("Cancelling deferred lock [lock={}]", client);
                                }

                                it.remove();

                                deferred = client;

                                break;
                            }
                        }
                    }

                    if (deferred == null) {
                        throw new IllegalArgumentException("Unknown lock [id=" + lockId + ']');
                    }

                    deferred.becomeTerminated(true);

                    return deferred.unlockFuture();
                } else {
                    LockControllerClient client = lockClients.get(lockId);

                    if (client == null) {
                        throw new IllegalArgumentException("Unknown lock [id=" + lockId + ']');
                    }

                    if (DEBUG) {
                        log.debug("Unlocking [lock={}]", client);
                    }

                    client.becomeUnlocking();

                    return client.unlockFuture();
                }
            }
        } finally {
            readLock.unlock();
        }
    }

    public void processLock(Message<LockProtocol> msg) {
        readLock.lock();

        try {
            LockProtocol.LockRequest request = msg.get(LockProtocol.LockRequest.class);

            if (status == Status.MIGRATING || status == Status.TERMINATED || !request.topology().equals(activeTopology())) {
                reply(msg, new LockResponse(LockResponse.Status.RETRY, null, 0));
            } else {
                String name = request.lockName();

                LockControllerServer server = checkoutServer(name);

                boolean hasLock;

                try {
                    hasLock = server.processLock(msg);
                } finally {
                    server.checkIn();
                }

                if (!hasLock) {
                    tryUnregisterServer(name, server);
                }
            }
        } finally {
            readLock.unlock();
        }
    }

    public void processUnlock(Message<LockProtocol> msg) {
        readLock.lock();

        try {
            UnlockRequest request = msg.get(UnlockRequest.class);

            if (status == Status.MIGRATING || status == Status.TERMINATED || !request.topology().equals(activeTopology())) {
                reply(msg, new UnlockResponse(UnlockResponse.Status.RETRY));
            } else {
                String name = request.lockName();

                LockControllerServer server = lockServers.get(name);

                if (server == null) {
                    if (DEBUG) {
                        log.debug("Got unlock request for unknown lock [request={}]", request);
                    }

                    reply(msg, new UnlockResponse(UnlockResponse.Status.OK));
                } else {
                    boolean isLocked = server.processUnlock(msg);

                    if (!isLocked) {
                        tryUnregisterServer(name, server);
                    }
                }
            }
        } finally {
            readLock.unlock();
        }
    }

    public void processLockOwnerQuery(Message<LockProtocol> msg) {
        readLock.lock();

        try {
            LockOwnerRequest request = msg.get(LockOwnerRequest.class);

            if (status == Status.MIGRATING || status == Status.TERMINATED || !request.topology().equals(activeTopology())) {
                reply(msg, new LockOwnerResponse(0, null, LockOwnerResponse.Status.RETRY));
            } else {
                String name = request.lockName();

                LockControllerServer server = lockServers.get(name);

                if (server == null) {
                    reply(msg, new LockOwnerResponse(0, null, LockOwnerResponse.Status.OK));
                } else {
                    server.processOwnerQuery(msg);
                }
            }
        } finally {
            readLock.unlock();
        }
    }

    public void processMigrationPrepare(Message<LockProtocol> msg) {
        MigrationPrepareRequest request = msg.get(MigrationPrepareRequest.class);

        LockMigrationKey key = request.key();

        writeLock.lock();

        try {
            if (status == Status.TERMINATED) {
                if (DEBUG) {
                    log.debug("Rejected migration prepare request since region is in {} state [request={}]", status, request);
                }

                replyMigrationRetry(msg);
            } else if (key.isSameCoordinator(localNode)) {
                replyMigrationOk(msg);

                if (key.equals(migrationKey) && key.isSameTopology(latestMapping)) {
                    if (DEBUG) {
                        log.debug("Coordinator received migration prepare request [request={}]", request);
                    }

                    if (migrationCallback != null) {
                        migrationCallback.onPrepareReceived(request);
                    }

                    Map<ClusterNodeId, ClusterHash> topologies = request.topologies();

                    // Check if all migrating locks were gathered consistently.
                    if (request.isFirstPass()) {
                        if (DEBUG) {
                            log.debug("Finished the first round of preparation phase [region={}, key={}]", regionName, key);
                        }

                        // Need to generate new migration key.
                        // Otherwise nodes can ignore second-pass message since they've already seen the previous key.
                        migrationKey = new LockMigrationKey(localNode, latestMapping.topology().hash(), keyIdGen.incrementAndGet());

                        List<LockMigrationInfo> migration = prepareMigration(topologies, emptyList());

                        MigrationPrepareRequest prepare = new MigrationPrepareRequest(regionName, migrationKey, false, topologies,
                            migration);

                        sendToNextNode(prepare);

                        if (migrationCallback != null) {
                            migrationCallback.onAfterPrepareSent(prepare);
                        }
                    } else {
                        if (DEBUG) {
                            log.debug("Starting locks migration phase [region={}, key={}]", regionName, key);
                        }

                        // Switch to second phase (apply locks).
                        List<LockMigrationInfo> remainingLocks = applyMigration(request.locks());

                        if (latestMapping.topology().size() > 1) {
                            MigrationApplyRequest apply = new MigrationApplyRequest(regionName, key, remainingLocks);

                            sendToNextNode(apply);

                            if (migrationCallback != null) {
                                migrationCallback.onAfterApplySent(apply);
                            }
                        }
                    }
                } else {
                    if (DEBUG) {
                        log.debug("Ignored migration request on the coordinator [request={}]", request);
                    }
                }
            } else if (!key.isSameTopology(latestMapping)) {
                if (DEBUG) {
                    log.debug("Rejected migration prepare request due to cluster topology mismatch [request={}]", request);
                }

                replyMigrationRetry(msg);
            } else {
                replyMigrationOk(msg);

                if (migrationKey == null || !migrationKey.equals(key)
                    // Process only if new key is from the different coordinator or if new key is later than the local one.
                    && (!migrationKey.isSameCoordinator(key.coordinator()) || migrationKey.id() < key.id())) {
                    if (DEBUG) {
                        log.debug("Processing migration prepare request [status={}, request={}]", status, request);
                    }

                    if (migrationCallback != null) {
                        migrationCallback.onPrepareReceived(request);
                    }

                    status = Status.MIGRATING;

                    migrationKey = key;

                    MigrationPrepareRequest nextPrepare;

                    Map<ClusterNodeId, ClusterHash> receivedTop = request.topologies();

                    if (request.isFirstPass()) {
                        // First round of preparation (gather topologies).
                        Map<ClusterNodeId, ClusterHash> newTopMap = addToTopologies(receivedTop);

                        List<LockMigrationInfo> migratingLocks = emptyList();

                        nextPrepare = new MigrationPrepareRequest(regionName, key, true, newTopMap, migratingLocks);
                    } else {
                        // Second round of preparation (gather migrating locks).
                        List<LockMigrationInfo> migration = prepareMigration(receivedTop, request.locks());

                        nextPrepare = new MigrationPrepareRequest(regionName, key, false, receivedTop, migration);
                    }

                    sendToNextNode(nextPrepare);

                    if (migrationCallback != null) {
                        migrationCallback.onAfterPrepareSent(nextPrepare);
                    }
                } else {
                    if (DEBUG) {
                        log.debug("Ignored migration request [request={}]", request);
                    }
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void processMigrationApply(Message<LockProtocol> msg) {
        MigrationApplyRequest request = msg.get(MigrationApplyRequest.class);

        LockMigrationKey key = request.key();

        writeLock.lock();

        try {
            if (status == Status.TERMINATED) {
                replyMigrationRetry(msg);
            } else if (key.isSameCoordinator(localNode)) {
                replyMigrationOk(msg);
            } else {
                if (key.isSameTopology(latestMapping)) {
                    replyMigrationOk(msg);

                    if (migrationKey != null && migrationKey.equals(key)) {
                        if (migrationCallback != null) {
                            migrationCallback.onApplyReceived(request);
                        }

                        List<LockMigrationInfo> locks = applyMigration(request.locks());

                        MigrationApplyRequest apply = new MigrationApplyRequest(regionName, key, locks);

                        sendToNextNode(apply);

                        if (migrationCallback != null) {
                            migrationCallback.onAfterApplySent(apply);
                        }
                    }
                } else {
                    replyMigrationRetry(msg);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void processTopologyChange() {
        PartitionMapper newPartitions = lockChannel.partitions().snapshot();

        writeLock.lock();

        try {
            if (status != Status.TERMINATED) {
                ClusterHash newClusterHash = newPartitions.topology().hash();

                if (latestMapping == null || !latestMapping.topology().hash().equals(newClusterHash)) {
                    this.latestMapping = newPartitions;

                    if (migrationCallback != null) {
                        migrationCallback.onTopologyChange(newPartitions);
                    }

                    if (isMigrationCoordinator(newPartitions.topology())) {
                        startMigration();
                    }
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    public void terminate() {
        writeLock.lock();

        try {
            lockServers.values().forEach(LockControllerServer::dispose);

            lockServers.clear();

            lockClients.values().forEach(lock -> lock.becomeTerminated(false));

            lockClients.clear();

            deferredLocks.forEach(lock -> lock.becomeTerminated(false));

            deferredLocks.clear();

            initMigration.countDown();

            // Important to update status after lock controllers termination.
            // Need to do it in order to prevent contention between the failover conditions of the messaging channel
            // and retry logic within the lock client controller.
            status = Status.TERMINATED;

            latestMapping = null;
            migrationKey = null;
            activeMapping = null;
        } finally {
            writeLock.unlock();
        }
    }

    private boolean awaitForInitialMigration() throws InterruptedException {
        while (true) {
            boolean needToWait = false;

            readLock.lock();

            try {
                if (status == Status.TERMINATED) {
                    return false;
                } else if (activeMapping == null) {
                    needToWait = true;
                }
            } finally {
                readLock.unlock();
            }

            if (needToWait) {
                initMigration.await();

                continue;
            }

            break;
        }

        return true;
    }

    // Package level for testing purposes.
    List<ClusterNodeId> queueOf(String lockName) {
        readLock.lock();

        try {
            LockControllerServer server = lockServers.get(lockName);

            return server != null ? server.enqueuedLocks() : emptyList();
        } finally {
            readLock.unlock();
        }
    }

    // Package level for testing purposes.
    ClusterNodeId managerOf(String lockName) {
        readLock.lock();

        try {
            PartitionMapper mapping = this.latestMapping;

            if (mapping == null) {
                throw new IllegalStateException("Lock region is not initialized.");
            }

            return mapping.map(new LockKey(regionName, lockName)).primaryNode().id();
        } finally {
            readLock.unlock();
        }
    }

    // Package level for testing purposes.
    ClusterHash lastRebalancedTopology() {
        readLock.lock();

        try {
            return activeTopology();
        } finally {
            readLock.unlock();
        }
    }

    // Package level for testing purposes.
    void setMigrationCallback(LockMigrationCallback migrationCallback) {
        this.migrationCallback = migrationCallback;
    }

    private void startMigration() {
        assert writeLock.isHeldByCurrentThread() : "Write lock must be held by the thread.";

        ClusterTopology topology = latestMapping.topology();

        migrationKey = new LockMigrationKey(localNode, topology.hash(), keyIdGen.incrementAndGet());

        if (DEBUG) {
            log.debug("Starting locks migration "
                + "[region={}, status={}, topology={}, migration-key={}]", regionName, status, topology, migrationKey);
        }

        status = Status.MIGRATING;

        Map<ClusterNodeId, ClusterHash> topologies = addToTopologies(emptyMap());

        List<LockMigrationInfo> migratingLocks = emptyList();

        MigrationPrepareRequest prepare = new MigrationPrepareRequest(regionName, migrationKey, true, topologies, migratingLocks);

        sendToNextNode(prepare);

        if (migrationCallback != null) {
            migrationCallback.onAfterPrepareSent(prepare);
        }
    }

    private List<LockMigrationInfo> prepareMigration(Map<ClusterNodeId, ClusterHash> topologies, List<LockMigrationInfo> gatheredLocks) {
        assert writeLock.isHeldByCurrentThread() : "Write lock must be held by the thread.";

        int maxMigrationSize = gatheredLocks.size() + lockClients.size();

        List<LockMigrationInfo> migration = new ArrayList<>(maxMigrationSize);

        migration.addAll(gatheredLocks);

        // Collect only those locks that require migration.
        PartitionMapper mapping = this.latestMapping;

        lockClients.values().stream()
            .filter(lock -> {
                ClusterNodeId oldManager = lock.manager();

                if (oldManager == null) {
                    return true;
                }

                ClusterNodeId newManager = mapping.map(lock.key()).primaryNode().id();

                // Check if mapping changed.
                if (!newManager.equals(oldManager)) {
                    return true;
                }

                // Check if remote node has the same active topology.
                // If topologies are different we can't be sure that remote node is aware of this lock
                // and should include such lock into the migrating locks list.
                if (topologies.containsKey(newManager)) {
                    ClusterHash hash = topologies.get(newManager);

                    return !Objects.equals(hash, activeTopology());
                }

                return false;
            })
            .forEach(lock -> {
                // Update mapping and gather only those locks that are in LOCKED state.
                if (lock.updateAndCheckLocked(mapping.topology())) {
                    migration.add(new LockMigrationInfo(lock.key().name(), lock.lockId(), lock.localNode(), lock.threadId()));
                }
            });

        if (DEBUG) {
            int total = migration.size();
            int localMigrating = total - gatheredLocks.size();
            int totalLocal = lockClients.size();

            log.debug("Gathered migrating locks "
                + "[region={}, local-migrating={}, total-migrating={}, total-local={}]", regionName, localMigrating, total, totalLocal);
        }

        return migration;
    }

    private List<LockMigrationInfo> applyMigration(List<LockMigrationInfo> locks) {
        assert writeLock.isHeldByCurrentThread() : "Write lock must be held by the thread.";

        if (DEBUG) {
            log.debug("Applying locks migration [region={}, status={}, key={}]", regionName, status, migrationKey);
        }

        this.status = Status.ACTIVE;
        this.activeMapping = this.latestMapping;
        this.migrationKey = null;

        List<LockMigrationInfo> remainingLocks = new ArrayList<>(locks.size());

        // Clear locks that are not managed by the local node anymore.
        for (Iterator<Map.Entry<String, LockControllerServer>> it = lockServers.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, LockControllerServer> e = it.next();

            String name = e.getKey();

            LockKey key = new LockKey(regionName, name);

            if (!activeMapping.map(key).isPrimary(localNode)) {
                LockControllerServer server = e.getValue();

                if (DEBUG) {
                    log.debug("Disposing lock server that is not managed by the local node anymore [region={}, lock={}]", regionName, name);
                }

                server.dispose();

                it.remove();
            }
        }

        // Migrate new locks to the local node.
        locks.forEach(lock -> {
            String name = lock.name();

            LockKey key = new LockKey(regionName, name);

            if (activeMapping.map(key).isPrimary(localNode)) {
                LockControllerServer server = lockServers.get(name);

                if (server == null) {
                    server = new LockControllerServer(name, scheduler);

                    lockServers.put(name, server);

                    if (DEBUG) {
                        log.debug("Registering new lock server [key={}]", key);
                    }
                }

                server.migrateLock(lock);
            } else {
                remainingLocks.add(lock);
            }
        });

        Set<ClusterNodeId> liveNodes = activeMapping.topology().stream().map(ClusterNode::id).collect(toSet());

        // Update managed locks with the latest topology so that they could release locks of failed nodes.
        lockServers.values().forEach(lock -> lock.update(liveNodes));

        // Update mapping of locally held locks.
        lockClients.values().forEach(lock ->
            lock.update(activeMapping)
        );

        // Process deferred locks that were waiting for initial lock topology.
        deferredLocks.forEach(lock -> {
            if (DEBUG) {
                log.debug("Registering deferred lock [lock={}]", lock);
            }

            lock.update(activeMapping);

            lockClients.put(lock.lockId(), lock);

            lock.becomeLocking();
        });

        deferredLocks.clear();

        // Notify all threads that are waiting for initial migration.
        initMigration.countDown();

        return remainingLocks;
    }

    private void sendToNextNode(MigrationRequest request) {
        migrationChannel.request(request, new ResponseCallback<LockProtocol>() {
            @Override
            public ReplyDecision accept(Throwable err, Response<LockProtocol> reply) {
                if (err == null) {
                    MigrationResponse response = reply.get(MigrationResponse.class);

                    if (DEBUG) {
                        log.debug("Got {} response [request={}]", response.status(), request);
                    }

                    switch (response.status()) {
                        case OK: {
                            return COMPLETE;
                        }
                        case RETRY: {
                            return isValid(request) ? REJECT : COMPLETE;
                        }
                        default: {
                            throw new IllegalArgumentException("Unexpected status type: " + response.status());
                        }
                    }
                } else {
                    if (DEBUG) {
                        log.debug("Got migration request failure [cause={}, request={}]", err.toString(), request);
                    }

                    return isValid(request) ? REJECT : COMPLETE;
                }
            }

            @Override
            public void onComplete(Throwable err, Response<LockProtocol> rsp) {
                if (err != null && isValid(request)) {
                    log.error("Failed to submit migration request [request={}]", request, err);
                }
            }
        });
    }

    private boolean isValid(MigrationRequest request) {
        // No need to lock since field is volatile.
        PartitionMapper mapping = this.latestMapping;

        if (request.key().isSameTopology(mapping)) {
            ClusterNodeId coordinator = request.key().coordinator();

            if (mapping.topology().contains(coordinator)) {
                if (DEBUG) {
                    log.debug("Request is valid [request={}]", request);
                }

                return true;
            }
        }

        if (DEBUG) {
            log.debug("Request is invalid [request={}]", request);
        }

        return false;
    }

    private void replyMigrationRetry(Message<LockProtocol> msg) {
        reply(msg, new MigrationResponse(MigrationResponse.Status.RETRY));
    }

    private void replyMigrationOk(Message<LockProtocol> msg) {
        reply(msg, new MigrationResponse(MigrationResponse.Status.OK));
    }

    private void reply(Message<LockProtocol> msg, MigrationResponse response) {
        if (DEBUG) {
            log.debug("Sending lock migration response [response={}]", response);
        }

        msg.reply(response, err -> {
            if (DEBUG) {
                if (err == null) {
                    log.debug("Successfully sent lock migration response [response={}]", response);
                } else {
                    log.debug("Failed to send lock migration response [response={}, cause={}]", response, err.toString());
                }
            }
        });
    }

    private void reply(Message<LockProtocol> msg, LockProtocol response) {
        if (DEBUG) {
            log.debug("Sending lock response [response={}]", response);
        }

        msg.reply(response, err -> {
            if (DEBUG) {
                if (err == null) {
                    log.debug("Successfully sent lock response [response={}]", response);
                } else {
                    log.debug("Failed to send lock response [response={}, cause={}]", response, err.toString());
                }
            }
        });
    }

    private LockControllerServer checkoutServer(String name) {
        synchronized (lockServersMux) {
            LockControllerServer server = lockServers.get(name);

            if (server == null) {
                server = new LockControllerServer(name, scheduler);

                if (DEBUG) {
                    log.debug("Registered new lock server [region={}, name={}]", regionName, name);
                }

                lockServers.put(name, server);
            }

            server.checkOut();

            return server;
        }
    }

    private void tryUnregisterServer(String name, LockControllerServer server) {
        synchronized (lockServersMux) {
            if (server.isFree()) {
                if (DEBUG) {
                    log.debug("Unregistered lock server [region={}, name={}]", regionName, name);
                }

                lockServers.remove(name, server);
            }
        }
    }

    private boolean isMigrationCoordinator(ClusterTopology topology) {
        return topology.oldest().id().equals(localNode);
    }

    private Map<ClusterNodeId, ClusterHash> addToTopologies(Map<ClusterNodeId, ClusterHash> oldTopologies) {
        Map<ClusterNodeId, ClusterHash> newTopologies = new HashMap<>(oldTopologies);

        newTopologies.put(localNode, activeTopology());

        return newTopologies;
    }

    private ClusterHash activeTopology() {
        PartitionMapper mapping = this.activeMapping;

        return mapping != null ? mapping.topology().hash() : null;
    }

    private boolean isTerminated() {
        readLock.lock();

        try {
            return status == Status.TERMINATED;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public String toString() {
        return ToString.format(LockRegion.class, this);
    }
}
