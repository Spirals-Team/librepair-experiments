/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.oak.plugins.document;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.jackrabbit.oak.plugins.document.Document.ID;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.jackrabbit.oak.commons.StringUtils;
import org.apache.jackrabbit.oak.plugins.document.util.Utils;
import org.apache.jackrabbit.oak.stats.Clock;
import org.apache.jackrabbit.oak.util.OakVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Information about a cluster node.
 */
public class ClusterNodeInfo {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterNodeInfo.class);

    /**
     * The prefix for random (non-reusable) keys.
     */
    private static final String RANDOM_PREFIX = "random:";

    /**
     * The machine id.
     */
    private static final String MACHINE_ID_KEY = "machine";

    /**
     * The Oak version.
     */
    private static final String OAK_VERSION_KEY = "oakVersion";

    /**
     * The unique instance id within this machine (the current working directory
     * if not set).
     */
    private static final String INSTANCE_ID_KEY = "instance";

    /**
     * The end of the lease.
     */
    public static final String LEASE_END_KEY = "leaseEnd";

    /**
     * The state of the cluster. On proper shutdown the state should be cleared.
     *
     * @see org.apache.jackrabbit.oak.plugins.document.ClusterNodeInfo.ClusterNodeState
     */
    public static final String STATE = "state";

    public static enum ClusterNodeState {
        NONE,
        /**
         * Indicates that cluster node is currently active
         */
        ACTIVE;

        static ClusterNodeState fromString(String state) {
            if (state == null) {
                return NONE;
            }
            return valueOf(state);
        }
    }

    /**
     * Flag to indicate whether the _lastRev recovery is in progress.
     *
     * @see RecoverLockState
     */
    public static final String REV_RECOVERY_LOCK = "recoveryLock";

    public static enum RecoverLockState {
        NONE,
        /**
         * _lastRev recovery in progress
         */
        ACQUIRED;

        static RecoverLockState fromString(String state) {
            if (state == null) {
                return NONE;
            }
            return valueOf(state);
        }
    }

    /**
     * Flag indicating which cluster node is running the recovery.
     */
    public static final String REV_RECOVERY_BY = "recoveryBy";

    /**
     * Additional info, such as the process id, for support.
     */
    private static final String INFO_KEY = "info";

    /**
     * The read/write mode key. Specifies the read/write preference to be used with
     * DocumentStore
     */
    private static final String READ_WRITE_MODE_KEY = "readWriteMode";

    /**
     * The unique machine id (the MAC address if available).
     */
    private static final String MACHINE_ID = getMachineId();

    /**
     * The process id (if available).
     */
    private static final long PROCESS_ID = getProcessId();

    /**
     * The current working directory.
     */
    private static final String WORKING_DIR = System.getProperty("user.dir", "");

    /**
     * <b>Only Used For Testing</b>
     */
    private static Clock clock = Clock.SIMPLE;


    public static final int DEFAULT_LEASE_DURATION_MILLIS = 1000 * 60;

    /**
     * The Oak version.
     */
    private static final String OAK_VERSION = OakVersion.getVersion();

    /**
     * The number of milliseconds for a lease (1 minute by default, and
     * initially).
     */
    private long leaseTime = DEFAULT_LEASE_DURATION_MILLIS;

    /**
     * The assigned cluster id.
     */
    private final int id;

    /**
     * The machine id.
     */
    private final String machineId;

    /**
     * The instance id.
     */
    private final String instanceId;

    /**
     * The document store that is used to renew the lease.
     */
    private final DocumentStore store;

    /**
     * The time (in milliseconds UTC) where this instance was started.
     */
    private final long startTime;

    /**
     * A unique id.
     */
    private final String uuid = UUID.randomUUID().toString();

    /**
     * The time (in milliseconds UTC) where the lease of this instance ends.
     */
    private long leaseEndTime;

    /**
     * The read/write mode.
     */
    private String readWriteMode;

    /**
     * The state of the cluter node.
     */
    private ClusterNodeState state;

    /**
     * The revLock value of the cluster;
     */
    private RecoverLockState revRecoveryLock;

    /**
     * In memory flag indicating that this ClusterNode is entry is new and is being added to
     * DocumentStore for the first time
     *
     * If false then it indicates that a previous entry for current node existed and that is being
     * reused
     */
    private boolean newEntry;

    private ClusterNodeInfo(int id, DocumentStore store, String machineId, String instanceId, ClusterNodeState state,
            RecoverLockState revRecoveryLock, Long leaseEnd, boolean newEntry) {
        this.id = id;
        this.startTime = getCurrentTime();
        if (leaseEnd == null) {
            this.leaseEndTime = startTime;
        } else {
            this.leaseEndTime = leaseEnd;
        }
        this.store = store;
        this.machineId = machineId;
        this.instanceId = instanceId;
        this.state = state;
        this.revRecoveryLock = revRecoveryLock;
        this.newEntry = newEntry;
    }

    public int getId() {
        return id;
    }

    /**
     * Create a cluster node info instance for the store, with the
     *
     * @param store the document store (for the lease)
     * @param configuredClusterId the configured cluster id (or 0 for dynamic assignment)
     * @return the cluster node info
     */
    public static ClusterNodeInfo getInstance(DocumentStore store, int configuredClusterId) {
        return getInstance(store, MACHINE_ID, WORKING_DIR, configuredClusterId, false);
    }

    /**
     * Create a cluster node info instance for the store.
     *
     * @param store the document store (for the lease)
     * @param machineId the machine id (null for MAC address)
     * @param instanceId the instance id (null for current working directory)
     * @return the cluster node info
     */
    public static ClusterNodeInfo getInstance(DocumentStore store, String machineId,
            String instanceId) {
        return getInstance(store, machineId, instanceId, 0, true);
    }

    /**
     * Create a cluster node info instance for the store.
     *
     * @param store the document store (for the lease)
     * @param machineId the machine id (null for MAC address)
     * @param instanceId the instance id (null for current working directory)
     * @param configuredClusterId the configured cluster id (or 0 for dynamic assignment)
     * @param updateLease whether to update the lease
     * @return the cluster node info
     */
    public static ClusterNodeInfo getInstance(DocumentStore store, String machineId,
            String instanceId, int configuredClusterId, boolean updateLease) {

        // defaults for machineId and instanceID
        if (machineId == null) {
            machineId = MACHINE_ID;
        }
        if (instanceId == null) {
            instanceId = WORKING_DIR;
        }

        int retries = 10;
        for (int i = 0; i < retries; i++) {
            ClusterNodeInfo clusterNode = createInstance(store, machineId, instanceId, configuredClusterId, i == 0);
            String key = String.valueOf(clusterNode.id);
            UpdateOp update = new UpdateOp(key, true);
            update.set(ID, key);
            update.set(MACHINE_ID_KEY, clusterNode.machineId);
            update.set(INSTANCE_ID_KEY, clusterNode.instanceId);
            if (updateLease) {
                update.set(LEASE_END_KEY, getCurrentTime() + clusterNode.leaseTime);
            } else {
                update.set(LEASE_END_KEY, clusterNode.leaseEndTime);
            }
            update.set(INFO_KEY, clusterNode.toString());
            update.set(STATE, clusterNode.state.name());
            update.set(REV_RECOVERY_LOCK, clusterNode.revRecoveryLock.name());
            update.set(OAK_VERSION_KEY, OAK_VERSION);

            final boolean success;
            if (clusterNode.newEntry) {
                // For new entry do a create. This ensures that if two nodes
                // create entry with same id then only one would succeed
                success = store.create(Collection.CLUSTER_NODES, Collections.singletonList(update));
            } else {
                // No expiration of earlier cluster info, so update
                store.createOrUpdate(Collection.CLUSTER_NODES, update);
                success = true;
            }

            if (success) {
                return clusterNode;
            }
        }
        throw new DocumentStoreException("Could not get cluster node info (retried " + retries + " times)");
    }

    private static ClusterNodeInfo createInstance(DocumentStore store, String machineId,
            String instanceId, int configuredClusterId, boolean waitForLease) {

        long now = getCurrentTime();
        int clusterNodeId = 0;
        int maxId = 0;
        ClusterNodeState state = ClusterNodeState.NONE;
        Long prevLeaseEnd = null;
        boolean newEntry = false;

        ClusterNodeInfoDocument alreadyExistingConfigured = null;
        String reuseFailureReason = "";
        List<ClusterNodeInfoDocument> list = ClusterNodeInfoDocument.all(store);

        for (ClusterNodeInfoDocument doc : list) {

            String key = doc.getId();

            int id;
            try {
                id = doc.getClusterId();
            } catch (Exception e) {
                LOG.debug("Skipping cluster node info document {} because ID is invalid", key);
                continue;
            }

            maxId = Math.max(maxId, id);

            // if a cluster id was configured: check that and abort if it does
            // not match
            if (configuredClusterId != 0) {
                if (configuredClusterId != id) {
                    continue;
                } else {
                    alreadyExistingConfigured = doc;
                }
            }

            Long leaseEnd = (Long) doc.get(LEASE_END_KEY);
            String mId = "" + doc.get(MACHINE_ID_KEY);
            String iId = "" + doc.get(INSTANCE_ID_KEY);

            if (leaseEnd != null && leaseEnd > now) {
                // wait if (a) instructed to, and (b) also the remaining time
                // time is not much bigger than the lease interval (in which
                // case something is very very wrong anyway)
                if (waitForLease && (leaseEnd - now) < (DEFAULT_LEASE_DURATION_MILLIS + 5000) && mId.equals(machineId)
                        && iId.equals(instanceId)) {
                    boolean worthRetrying = waitForLeaseExpiry(store, doc, leaseEnd.longValue(), machineId, instanceId);
                    if (worthRetrying) {
                        return createInstance(store, machineId, instanceId, configuredClusterId, false);
                    }
                }

                reuseFailureReason = "leaseEnd " + leaseEnd + " > " + now + " - " + (leaseEnd - now) + "ms in the future";
                continue;
            }

            // remove entries with "random:" keys if not in use (no lease at all) 
            if (mId.startsWith(RANDOM_PREFIX) && leaseEnd == null) {
                store.remove(Collection.CLUSTER_NODES, key);
                LOG.debug("Cleaned up cluster node info for clusterNodeId {} [machineId: {}, leaseEnd: {}]", id, mId,
                        leaseEnd == null ? "n/a" : Utils.timestampToString(leaseEnd));
                if (alreadyExistingConfigured == doc) {
                    // we removed it, so we can't re-use it after all
                    alreadyExistingConfigured = null;
                }
                continue;
            }

            if (!mId.equals(machineId) || !iId.equals(instanceId)) {
                // a different machine or instance
                reuseFailureReason = "machineId/instanceId do not match: " + mId + "/" + iId + " != " + machineId + "/" + instanceId;
                continue;
            }

            // a cluster node which matches current machine identity but
            // not being used
            if (clusterNodeId == 0 || id < clusterNodeId) {
                // if there are multiple, use the smallest value
                clusterNodeId = id;
                state = ClusterNodeState.fromString((String) doc.get(STATE));
                prevLeaseEnd = leaseEnd;
            }
        }

        // No usable existing entry with matching signature found so
        // create a new entry
        if (clusterNodeId == 0) {
            newEntry = true;
            if (configuredClusterId != 0) {
                if (alreadyExistingConfigured != null) {
                    throw new DocumentStoreException(
                            "Configured cluster node id " + configuredClusterId + " already in use: " + reuseFailureReason);
                }
                clusterNodeId = configuredClusterId;
            } else {
                clusterNodeId = maxId + 1;
            }
        }

        // Do not expire entries and stick on the earlier state, and leaseEnd so,
        // that _lastRev recovery if needed is done.
        return new ClusterNodeInfo(clusterNodeId, store, machineId, instanceId, state,
                RecoverLockState.NONE, prevLeaseEnd, newEntry);
    }

    private static boolean waitForLeaseExpiry(DocumentStore store, ClusterNodeInfoDocument cdoc, long leaseEnd, String machineId,
            String instanceId) {
        String key = cdoc.getId();
        LOG.info("Found an existing possibly active cluster node info (" + key + ") for this instance: " + machineId + "/"
                + instanceId + ", will try use it.");

        // wait until lease expiry plus 2s
        long waitUntil = leaseEnd + 2000;

        while (getCurrentTime() < waitUntil) {
            LOG.info("Waiting for cluster node " + key + "'s lease to expire: " + (waitUntil - getCurrentTime()) / 1000 + "s left");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // ignored
            }

            try {
                // check state of cluster node info
                ClusterNodeInfoDocument reread = store.find(Collection.CLUSTER_NODES, key);
                if (reread == null) {
                    LOG.info("Cluster node info " + key + ": gone; continueing.");
                    return true;
                } else {
                    Long newLeaseEnd = (Long) reread.get(LEASE_END_KEY);
                    if (newLeaseEnd == null) {
                        LOG.info("Cluster node " + key + ": lease end information missing, aborting.");
                        return false;
                    } else {
                        if (newLeaseEnd.longValue() != leaseEnd) {
                            LOG.info("Cluster node " + key + " seems to be still active (lease end changed from " + leaseEnd
                                    + " to " + newLeaseEnd + ", will not try to use it.");
                            return false;
                        }
                    }
                }
            } catch (DocumentStoreException ex) {
                LOG.info("Error reading cluster node info for key " + key, ex);
                return false;
            }
        }
        return true;
    }

    /**
     * Renew the cluster id lease. This method needs to be called once in a while,
     * to ensure the same cluster id is not re-used by a different instance.
     * The lease is only renewed when half of the lease time passed. That is,
     * with a lease time of 60 seconds, the lease is renewed every 30 seconds.
     *
     * @return {@code true} if the lease was renewed; {@code false} otherwise.
     */
    public boolean renewLease() {
        long now = getCurrentTime();
        if (LOG.isTraceEnabled()) {
            LOG.trace("renewLease - leaseEndTime: " + leaseEndTime + ", leaseTime: " + leaseTime);
        }
        if (now + leaseTime / 2 < leaseEndTime) {
            return false;
        }
        UpdateOp update = new UpdateOp("" + id, false);
        leaseEndTime = now + leaseTime;
        update.set(LEASE_END_KEY, leaseEndTime);
        update.set(STATE, ClusterNodeState.ACTIVE.name());

        if (LOG.isDebugEnabled()) {
            LOG.debug("Renewing lease for cluster id " + id + " with UpdateOp " + update);
        }
        ClusterNodeInfoDocument doc = store.createOrUpdate(Collection.CLUSTER_NODES, update);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Lease renewal for cluster id " + id + " resulted in: " + doc);
        }

        String mode = (String) doc.get(READ_WRITE_MODE_KEY);
        if (mode != null && !mode.equals(readWriteMode)) {
            readWriteMode = mode;
            store.setReadWriteMode(mode);
        }
        return true;
    }

    public void setLeaseTime(long leaseTime) {
        this.leaseTime = leaseTime;
    }

    public long getLeaseTime() {
        return leaseTime;
    }

    public void dispose() {
        UpdateOp update = new UpdateOp("" + id, true);
        update.set(LEASE_END_KEY, null);
        update.set(STATE, null);
        update.set(REV_RECOVERY_LOCK, RecoverLockState.NONE.name());
        store.createOrUpdate(Collection.CLUSTER_NODES, update);
    }

    @Override
    public String toString() {
        return "id: " + id + ",\n" +
                "startTime: " + startTime + ",\n" +
                "machineId: " + machineId + ",\n" +
                "instanceId: " + instanceId + ",\n" +
                "pid: " + PROCESS_ID + ",\n" +
                "uuid: " + uuid + ",\n" +
                "readWriteMode: " + readWriteMode + ",\n" +
                "state: " + state + ",\n" +
                "revLock: " + revRecoveryLock + ",\n" +
                "oakVersion: " + OAK_VERSION;
    }

    /**
     * Specify a custom clock to be used for determining current time.
     *
     * <b>Only Used For Testing</b>
     */
    static void setClock(Clock c) {
        checkNotNull(c);
        clock = c;
    }

    /**
     * Resets the clock to the default
     */
    static void resetClockToDefault() {
        clock = Clock.SIMPLE;
    }

    private static long getProcessId() {
        try {
            String name = ManagementFactory.getRuntimeMXBean().getName();
            return Long.parseLong(name.substring(0, name.indexOf('@')));
        } catch (Exception e) {
            LOG.warn("Could not get process id", e);
            return 0;
        }
    }

    /*
     * Allow external override of hardware address. The special value "(none)"
     * indicates that a situation where no hardware address is available is to
     * be simulated.
     */
    private static String getHWAFromSystemProperty() {
        String pname = ClusterNodeInfo.class.getName() + ".HWADDRESS";
        String hwa = System.getProperty(pname, "");
        if (!"".equals(hwa)) {
            LOG.debug("obtaining hardware address from system variable " + pname + ": " + hwa);
        }
        return hwa;
    }

    /**
     * Calculate the unique machine id. This usually is the lowest MAC address
     * if available. As an alternative, a randomly generated UUID is used.
     *
     * @return the unique id
     */
    private static String getMachineId() {
        Exception exception = null;
        try {
            ArrayList<String> macAddresses = new ArrayList<String>();
            ArrayList<String> likelyVirtualMacAddresses = new ArrayList<String>();
            ArrayList<String> otherAddresses = new ArrayList<String>();
            String hwaFromSysProp = getHWAFromSystemProperty();
            if ("".equals(hwaFromSysProp)) {
                Enumeration<NetworkInterface> e = NetworkInterface
                        .getNetworkInterfaces();
                while (e.hasMoreElements()) {
                    NetworkInterface ni = e.nextElement();
                    try {
                        byte[] hwa = ni.getHardwareAddress();
                        // empty addresses have been seen on loopback devices
                        if (hwa != null && hwa.length != 0) {
                            String str = StringUtils.convertBytesToHex(hwa);
                            if (hwa.length == 6) {
                                // likely a MAC address
                                String displayName = ni.getDisplayName().toLowerCase(Locale.ENGLISH);
                                // de-prioritize addresses that are likely to be virtual (see OAK-3885)
                                boolean looksVirtual = displayName.indexOf("virtual") >= 0 || displayName.indexOf("vpn") >= 0;
                                if (!looksVirtual) {
                                    macAddresses.add(str);
                                } else {
                                    likelyVirtualMacAddresses.add(str);
                                }
                            } else {
                                otherAddresses.add(str);
                            }
                        }
                    } catch (Exception e2) {
                        exception = e2;
                    }
                }
            }
            else {
                if (!"(none)".equals(hwaFromSysProp)) {
                    if (hwaFromSysProp.length() == 12) {
                        // assume 12 hex digits are a mac address
                        macAddresses.add(hwaFromSysProp);
                    } else {
                        otherAddresses.add(hwaFromSysProp);
                    }
                }
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("getMachineId(): discovered addresses: {} {} {}", macAddresses, likelyVirtualMacAddresses,
                        otherAddresses);
            }

            if (macAddresses.size() > 0) {
                // use the lowest MAC value, such that if the order changes,
                // the same one is used
                Collections.sort(macAddresses);
                return "mac:" + macAddresses.get(0);
            } else if (likelyVirtualMacAddresses.size() > 0) {
                Collections.sort(likelyVirtualMacAddresses);
                return "mac:" + likelyVirtualMacAddresses.get(0);
            } else if (otherAddresses.size() > 0) {
                // try the lowest "other" address
                Collections.sort(otherAddresses);
                return "hwa:" + otherAddresses.get(0);
            }
        } catch (Exception e) {
            exception = e;
        }
        if (exception != null) {
            LOG.warn("Error getting the machine id; using a UUID", exception);
        }
        return RANDOM_PREFIX + UUID.randomUUID().toString();
    }

    private static long getCurrentTime() {
        return clock.getTime();
    }
}
