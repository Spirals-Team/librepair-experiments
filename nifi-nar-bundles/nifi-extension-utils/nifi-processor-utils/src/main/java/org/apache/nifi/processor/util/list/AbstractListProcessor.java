/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.nifi.processor.util.list;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.nifi.annotation.behavior.Stateful;
import org.apache.nifi.annotation.behavior.TriggerSerially;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.annotation.notification.OnPrimaryNodeStateChange;
import org.apache.nifi.annotation.notification.PrimaryNodeState;
import org.apache.nifi.components.AllowableValue;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.components.state.Scope;
import org.apache.nifi.components.state.StateManager;
import org.apache.nifi.components.state.StateMap;
import org.apache.nifi.distributed.cache.client.Deserializer;
import org.apache.nifi.distributed.cache.client.DistributedMapCacheClient;
import org.apache.nifi.distributed.cache.client.Serializer;
import org.apache.nifi.distributed.cache.client.exception.DeserializationException;
import org.apache.nifi.distributed.cache.client.exception.SerializationException;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * An Abstract Processor that is intended to simplify the coding required in order to perform Listing operations of remote resources.
 * Those remote resources may be files, "objects", "messages", or any other sort of entity that may need to be listed in such a way that
 * we identity the entity only once. Each of these objects, messages, etc. is referred to as an "entity" for the scope of this Processor.
 * </p>
 * <p>
 * This class is responsible for triggering the listing to occur, filtering the results returned such that only new (unlisted) entities
 * or entities that have been modified will be emitted from the Processor.
 * </p>
 * <p>
 * In order to make use of this abstract class, the entities listed must meet the following criteria:
 * </p>
 * <ul>
 * <li>
 * Entity must have a timestamp associated with it. This timestamp is used to determine if entities are "new" or not. Any entity that is
 * returned by the listing will be considered "new" if the timestamp is later than the latest timestamp pulled.
 * </li>
 * <li>
 * If the timestamp of an entity is before OR equal to the latest timestamp pulled, then the entity is not considered new. If the timestamp is later
 * than the last timestamp pulled, then the entity is considered new.
 * </li>
 * <li>
 * Entity must have a user-readable name that can be used for logging purposes.
 * </li>
 * </ul>
 * <p>
 * This class persists state across restarts so that even if NiFi is restarted, duplicates will not be pulled from the target system given the above criteria. This is
 * performed using the {@link StateManager}. This allows the system to be restarted and begin processing where it left off. The state that is stored is the latest timestamp
 * that has been pulled (as determined by the timestamps of the entities that are returned). See the section above for information about how this information isused in order to
 * determine new entities.
 * </p>
 * <p>
 * NOTE: This processor performs migrations of legacy state mechanisms inclusive of locally stored, file-based state and the optional utilization of the <code>Distributed Cache
 * Service</code> property to the new {@link StateManager} functionality. Upon successful migration, the associated data from one or both of the legacy mechanisms is purged.
 * </p>
 * <p>
 * For each new entity that is listed, the Processor will send a FlowFile to the 'success' relationship. The FlowFile will have no content but will have some set
 * of attributes (defined by the concrete implementation) that can be used to fetch those remote resources or interact with them in whatever way makes sense for
 * the configured dataflow.
 * </p>
 * <p>
 * Subclasses are responsible for the following:
 * </p>
 * <ul>
 * <li>
 * Perform a listing of remote resources. The subclass will implement the {@link #performListing(ProcessContext, Long)} method, which creates a listing of all
 * entities on the remote system that have timestamps later than the provided timestamp. If the entities returned have a timestamp before the provided one, those
 * entities will be filtered out. It is therefore not necessary to perform the filtering of timestamps but is provided in order to give the implementation the ability
 * to filter those resources on the server side rather than pulling back all of the information, if it makes sense to do so in the concrete implementation.
 * </li>
 * <li>
 * Creating a Map of attributes that are applicable for an entity. The attributes that are assigned to each FlowFile are exactly those returned by the
 * {@link #createAttributes(ListableEntity, ProcessContext)}.
 * </li>
 * <li>
 * Returning the configured path. Many resources can be comprised of a "path" (or a "container" or "bucket", etc.) as well as name or identifier that is unique only
 * within that path. The {@link #getPath(ProcessContext)} method is responsible for returning the path that is currently being polled for entities. If this does concept
 * does not apply for the concrete implementation, it is recommended that the concrete implementation return "." or "/" for all invocations of this method.
 * </li>
 * <li>
 * Determining when the listing must be cleared. It is sometimes necessary to clear state about which entities have already been ingested, as the result of a user
 * changing a property value. The {@link #isListingResetNecessary(PropertyDescriptor)} method is responsible for determining when the listing needs to be reset by returning
 * a boolean indicating whether or not a change in the value of the provided property should trigger the timestamp and identifier information to be cleared.
 * </li>
 * <li>
 * Provide the target system timestamp precision. By either letting user to choose the right one by adding TARGET_SYSTEM_TIMESTAMP_PRECISION to the return value of
 * getSupportedPropertyDescriptors method or, overriding getDefaultTimePrecision method in case the target system has a fixed time precision.
 * </li>
 * </ul>
 */
@TriggerSerially
@Stateful(scopes = {Scope.LOCAL, Scope.CLUSTER}, description = "After a listing of resources is performed, the latest timestamp of any of the resources is stored in the component's state. "
    + "The scope used depends on the implementation.")
public abstract class AbstractListProcessor<T extends ListableEntity> extends AbstractProcessor {

    public static final PropertyDescriptor DISTRIBUTED_CACHE_SERVICE = new PropertyDescriptor.Builder()
        .name("Distributed Cache Service")
        .description("Specifies the Controller Service that should be used to maintain state about what has been pulled from the remote server so that if a new node "
            + "begins pulling data, it won't duplicate all of the work that has been done. If not specified, the information will not be shared across the cluster. "
            + "This property does not need to be set for standalone instances of NiFi but should be configured if NiFi is run within a cluster.")
        .required(false)
        .identifiesControllerService(DistributedMapCacheClient.class)
        .build();

    public static final AllowableValue PRECISION_AUTO_DETECT = new AllowableValue("auto-detect", "Auto Detect",
    "Automatically detect time unit deterministically based on candidate entries timestamp."
            + " Please note that this option may take longer to list entities unnecessarily, if none of entries has a precise precision timestamp."
            + " E.g. even if a target system supports millis, if all entries only have timestamps without millis, such as '2017-06-16 09:06:34.000', then its precision is determined as 'seconds'.");
    public static final AllowableValue PRECISION_MILLIS = new AllowableValue("millis", "Milliseconds",
            "This option provides the minimum latency for an entry from being available to being listed if target system supports millis, if not, use other options.");
    public static final AllowableValue PRECISION_SECONDS = new AllowableValue("seconds", "Seconds","For a target system that does not have millis precision, but has in seconds.");
    public static final AllowableValue PRECISION_MINUTES = new AllowableValue("minutes", "Minutes", "For a target system that only supports precision in minutes.");

    public static final PropertyDescriptor TARGET_SYSTEM_TIMESTAMP_PRECISION = new PropertyDescriptor.Builder()
        .name("target-system-timestamp-precision")
        .displayName("Target System Timestamp Precision")
        .description("Specify timestamp precision at the target system."
                + " Since this processor uses timestamp of entities to decide which should be listed, it is crucial to use the right timestamp precision.")
        .required(true)
        .allowableValues(PRECISION_AUTO_DETECT, PRECISION_MILLIS, PRECISION_SECONDS, PRECISION_MINUTES)
        .defaultValue(PRECISION_AUTO_DETECT.getValue())
        .build();

    public static final Relationship REL_SUCCESS = new Relationship.Builder()
        .name("success")
        .description("All FlowFiles that are received are routed to success")
        .build();

    /**
     * Represents the timestamp of an entity which was the latest one within those listed at the previous cycle.
     * It does not necessary mean it has been processed as well.
     * Whether it was processed or not depends on target system time precision and how old the entity timestamp was.
     */
    private volatile Long lastListedLatestEntryTimestampMillis = null;
    /**
     * Represents the timestamp of an entity which was the latest one
     * within those picked up and written to the output relationship at the previous cycle.
     */
    private volatile Long lastProcessedLatestEntryTimestampMillis = 0L;
    private volatile Long lastRunTimeNanos = 0L;
    private volatile boolean justElectedPrimaryNode = false;
    private volatile boolean resetState = false;
    private volatile List<String> latestIdentifiersProcessed = new ArrayList<>();

    /*
     * A constant used in determining an internal "yield" of processing files. Given the logic to provide a pause on the newest
     * files according to timestamp, it is ensured that at least the specified millis has been eclipsed to avoid getting scheduled
     * near instantaneously after the prior iteration effectively voiding the built in buffer
     */
    public static final Map<TimeUnit, Long> LISTING_LAG_MILLIS;
    static {
        final Map<TimeUnit, Long> nanos = new HashMap<>();
        nanos.put(TimeUnit.MILLISECONDS, 100L);
        nanos.put(TimeUnit.SECONDS, 1_000L);
        nanos.put(TimeUnit.MINUTES, 60_000L);
        LISTING_LAG_MILLIS = Collections.unmodifiableMap(nanos);
    }
    static final String LATEST_LISTED_ENTRY_TIMESTAMP_KEY = "listing.timestamp";
    static final String LAST_PROCESSED_LATEST_ENTRY_TIMESTAMP_KEY = "processed.timestamp";
    static final String IDENTIFIER_PREFIX = "id";

    public File getPersistenceFile() {
        return new File("conf/state/" + getIdentifier());
    }

    @Override
    protected List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        final List<PropertyDescriptor> properties = new ArrayList<>();
        properties.add(DISTRIBUTED_CACHE_SERVICE);
        properties.add(TARGET_SYSTEM_TIMESTAMP_PRECISION);
        return properties;
    }

    @Override
    public void onPropertyModified(final PropertyDescriptor descriptor, final String oldValue, final String newValue) {
        if (isConfigurationRestored() && isListingResetNecessary(descriptor)) {
            resetTimeStates(); // clear lastListingTime so that we have to fetch new time
            resetState = true;
        }
    }


    @Override
    public Set<Relationship> getRelationships() {
        final Set<Relationship> relationships = new HashSet<>();
        relationships.add(REL_SUCCESS);
        return relationships;
    }

    @OnPrimaryNodeStateChange
    public void onPrimaryNodeChange(final PrimaryNodeState newState) {
        justElectedPrimaryNode = (newState == PrimaryNodeState.ELECTED_PRIMARY_NODE);
    }

    @OnScheduled
    public final void updateState(final ProcessContext context) throws IOException {
        final String path = getPath(context);
        final DistributedMapCacheClient client = context.getProperty(DISTRIBUTED_CACHE_SERVICE).asControllerService(DistributedMapCacheClient.class);

        // Check if state already exists for this path. If so, we have already migrated the state.
        final StateMap stateMap = context.getStateManager().getState(getStateScope(context));
        if (stateMap.getVersion() == -1L) {
            try {
                // Migrate state from the old way of managing state (distributed cache service and local file)
                // to the new mechanism (State Manager).
                migrateState(path, client, context.getStateManager(), getStateScope(context));
            } catch (final IOException ioe) {
                throw new IOException("Failed to properly migrate state to State Manager", ioe);
            }
        }

        // When scheduled to run, check if the associated timestamp is null, signifying a clearing of state and reset the internal timestamp
        if (lastListedLatestEntryTimestampMillis != null && stateMap.get(LATEST_LISTED_ENTRY_TIMESTAMP_KEY) == null) {
            getLogger().info("Detected that state was cleared for this component.  Resetting internal values.");
            resetTimeStates();
        }

        if (resetState) {
            context.getStateManager().clear(getStateScope(context));
            resetState = false;
        }
    }

    /**
     * This processor used to use the DistributedMapCacheClient in order to store cluster-wide state, before the introduction of
     * the StateManager. This method will migrate state from that DistributedMapCacheClient, or from a local file, to the StateManager,
     * if any state already exists. More specifically, this will extract out the relevant timestamp for when the processor last ran
     *
     * @param path         the path to migrate state for
     * @param client       the DistributedMapCacheClient that is capable of obtaining the current state
     * @param stateManager the StateManager to use in order to store the new state
     * @param scope        the scope to use
     * @throws IOException if unable to retrieve or store the state
     */
    private void migrateState(final String path, final DistributedMapCacheClient client, final StateManager stateManager, final Scope scope) throws IOException {
        Long minTimestamp = null;

        // Retrieve state from Distributed Cache Client, establishing the latest file seen
        if (client != null) {
            final StringSerDe serde = new StringSerDe();
            final String serializedState = client.get(getKey(path), serde, serde);
            if (serializedState != null && !serializedState.isEmpty()) {
                final EntityListing listing = deserialize(serializedState);
                minTimestamp = listing.getLatestTimestamp().getTime();
            }

            // remove entry from distributed cache server
            if (client != null) {
                try {
                    client.remove(path, new StringSerDe());
                } catch (final IOException ioe) {
                    getLogger().warn("Failed to remove entry from Distributed Cache Service. However, the state has already been migrated to use the new "
                        + "State Management service, so the Distributed Cache Service is no longer needed.");
                }
            }
        }

        // Retrieve state from locally persisted file, and compare these to the minTimestamp established from the distributedCache, if there was one
        final File persistenceFile = getPersistenceFile();
        if (persistenceFile.exists()) {
            final Properties props = new Properties();

            try (final FileInputStream fis = new FileInputStream(persistenceFile)) {
                props.load(fis);
            }

            final String locallyPersistedValue = props.getProperty(path);
            if (locallyPersistedValue != null) {
                final EntityListing listing = deserialize(locallyPersistedValue);
                final long localTimestamp = listing.getLatestTimestamp().getTime();
                // if the local file's latest timestamp is beyond that of the value provided from the cache, replace
                if (minTimestamp == null || localTimestamp > minTimestamp) {
                    minTimestamp = localTimestamp;
                    latestIdentifiersProcessed.clear();
                    latestIdentifiersProcessed.addAll(listing.getMatchingIdentifiers());
                }
            }

            // delete the local file, since it is no longer needed
            if (persistenceFile.exists() && !persistenceFile.delete()) {
                getLogger().warn("Migrated state but failed to delete local persistence file");
            }
        }

        if (minTimestamp != null) {
            persist(minTimestamp, minTimestamp, latestIdentifiersProcessed, stateManager, scope);
        }
    }

    private void persist(final long latestListedEntryTimestampThisCycleMillis,
                         final long lastProcessedLatestEntryTimestampMillis,
                         final List<String> processedIdentifiesWithLatestTimestamp,
                         final StateManager stateManager, final Scope scope) throws IOException {
        final Map<String, String> updatedState = new HashMap<>(processedIdentifiesWithLatestTimestamp.size() + 2);
        updatedState.put(LATEST_LISTED_ENTRY_TIMESTAMP_KEY, String.valueOf(latestListedEntryTimestampThisCycleMillis));
        updatedState.put(LAST_PROCESSED_LATEST_ENTRY_TIMESTAMP_KEY, String.valueOf(lastProcessedLatestEntryTimestampMillis));
        for (int i = 0; i < processedIdentifiesWithLatestTimestamp.size(); i++) {
            updatedState.put(IDENTIFIER_PREFIX + "." + i, processedIdentifiesWithLatestTimestamp.get(i));
        }
        stateManager.setState(updatedState, scope);
    }

    protected String getKey(final String directory) {
        return getIdentifier() + ".lastListingTime." + directory;
    }

    private EntityListing deserialize(final String serializedState) throws JsonParseException, JsonMappingException, IOException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(serializedState, EntityListing.class);
    }


    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) throws ProcessException {
        Long minTimestampToListMillis = lastListedLatestEntryTimestampMillis;

        if (this.lastListedLatestEntryTimestampMillis == null || this.lastProcessedLatestEntryTimestampMillis == null || justElectedPrimaryNode) {
            try {
                // Attempt to retrieve state from the state manager if a last listing was not yet established or
                // if just elected the primary node
                final StateMap stateMap = context.getStateManager().getState(getStateScope(context));
                latestIdentifiersProcessed.clear();
                for (Map.Entry<String, String> state : stateMap.toMap().entrySet()) {
                    final String k = state.getKey();
                    final String v = state.getValue();
                    if (v == null || v.isEmpty()) {
                        continue;
                    }

                    if (LATEST_LISTED_ENTRY_TIMESTAMP_KEY.equals(k)) {
                        minTimestampToListMillis = Long.parseLong(v);
                        // If our determined timestamp is the same as that of our last listing, skip this execution as there are no updates
                        if (minTimestampToListMillis.equals(this.lastListedLatestEntryTimestampMillis)) {
                            context.yield();
                            return;
                        } else {
                            this.lastListedLatestEntryTimestampMillis = minTimestampToListMillis;
                        }
                    } else if (LAST_PROCESSED_LATEST_ENTRY_TIMESTAMP_KEY.equals(k)) {
                        this.lastProcessedLatestEntryTimestampMillis = Long.parseLong(v);
                    } else if (k.startsWith(IDENTIFIER_PREFIX)) {
                        latestIdentifiersProcessed.add(v);
                    }
                }
                justElectedPrimaryNode = false;
            } catch (final IOException ioe) {
                getLogger().error("Failed to retrieve timestamp of last listing from the State Manager. Will not perform listing until this is accomplished.");
                context.yield();
                return;
            }
        }

        final List<T> entityList;
        final long currentRunTimeNanos = System.nanoTime();
        final long currentRunTimeMillis = System.currentTimeMillis();
        try {
            // track of when this last executed for consideration of the lag nanos
            entityList = performListing(context, minTimestampToListMillis);
        } catch (final IOException e) {
            getLogger().error("Failed to perform listing on remote host due to {}", e);
            context.yield();
            return;
        }

        if (entityList == null || entityList.isEmpty()) {
            context.yield();
            return;
        }

        Long latestListedEntryTimestampThisCycleMillis = null;
        final TreeMap<Long, List<T>> orderedEntries = new TreeMap<>();

        // Build a sorted map to determine the latest possible entries
        boolean targetSystemHasMilliseconds = false;
        boolean targetSystemHasSeconds = false;
        for (final T entity : entityList) {
            final long entityTimestampMillis = entity.getTimestamp();
            if (!targetSystemHasMilliseconds) {
                targetSystemHasMilliseconds = entityTimestampMillis % 1000 > 0;
            }
            if (!targetSystemHasSeconds) {
                targetSystemHasSeconds = entityTimestampMillis % 60_000 > 0;
            }
            // New entries are all those that occur at or after the associated timestamp
            final boolean newEntry = minTimestampToListMillis == null || entityTimestampMillis >= minTimestampToListMillis && entityTimestampMillis >= lastProcessedLatestEntryTimestampMillis;

            if (newEntry) {
                List<T> entitiesForTimestamp = orderedEntries.get(entity.getTimestamp());
                if (entitiesForTimestamp == null) {
                    entitiesForTimestamp = new ArrayList<T>();
                    orderedEntries.put(entity.getTimestamp(), entitiesForTimestamp);
                }
                entitiesForTimestamp.add(entity);
            }
        }

        int flowfilesCreated = 0;

        if (orderedEntries.size() > 0) {
            latestListedEntryTimestampThisCycleMillis = orderedEntries.lastKey();

            // Determine target system time precision.
            String specifiedPrecision = context.getProperty(TARGET_SYSTEM_TIMESTAMP_PRECISION).getValue();
            if (StringUtils.isBlank(specifiedPrecision)) {
                // If TARGET_SYSTEM_TIMESTAMP_PRECISION is not supported by the Processor, then specifiedPrecision can be null, instead of its default value.
                specifiedPrecision = getDefaultTimePrecision();
            }
            final TimeUnit targetSystemTimePrecision
                    = PRECISION_AUTO_DETECT.getValue().equals(specifiedPrecision)
                        ? targetSystemHasMilliseconds ? TimeUnit.MILLISECONDS : targetSystemHasSeconds ? TimeUnit.SECONDS : TimeUnit.MINUTES
                    : PRECISION_MILLIS.getValue().equals(specifiedPrecision) ? TimeUnit.MILLISECONDS
                    : PRECISION_SECONDS.getValue().equals(specifiedPrecision) ? TimeUnit.SECONDS : TimeUnit.MINUTES;
            final Long listingLagMillis = LISTING_LAG_MILLIS.get(targetSystemTimePrecision);

            // If the last listing time is equal to the newest entries previously seen,
            // another iteration has occurred without new files and special handling is needed to avoid starvation
            if (latestListedEntryTimestampThisCycleMillis.equals(lastListedLatestEntryTimestampMillis)) {
                /* We need to wait for another cycle when either:
                 *   - If we have not eclipsed the minimal listing lag needed due to being triggered too soon after the last run
                 *   - The latest listed entity timestamp is equal to the last processed time, meaning we handled those items originally passed over. No need to process it again.
                 */
                final long  listingLagNanos = TimeUnit.MILLISECONDS.toNanos(listingLagMillis);
                if (currentRunTimeNanos - lastRunTimeNanos < listingLagNanos
                        || (latestListedEntryTimestampThisCycleMillis.equals(lastProcessedLatestEntryTimestampMillis)
                            && orderedEntries.get(latestListedEntryTimestampThisCycleMillis).stream()
                                    .allMatch(entity -> latestIdentifiersProcessed.contains(entity.getIdentifier())))) {
                    context.yield();
                    return;
                }

            } else {
                // Convert minimum reliable timestamp into target system time unit, in order to truncate unreliable digits.
                final long minimumReliableTimestampInFilesystemTimeUnit = targetSystemTimePrecision.convert(currentRunTimeMillis - listingLagMillis, TimeUnit.MILLISECONDS);
                final long minimumReliableTimestampMillis = targetSystemTimePrecision.toMillis(minimumReliableTimestampInFilesystemTimeUnit);
                // If the latest listed entity is not old enough, compared with the minimum timestamp, then wait for another cycle.
                // The minimum timestamp should be reliable to determine that no further entries will be added with the same timestamp based on the target system time precision.
                if (minimumReliableTimestampMillis < latestListedEntryTimestampThisCycleMillis) {
                    // Otherwise, newest entries are held back one cycle to avoid issues in writes occurring exactly when the listing is being performed to avoid missing data
                    orderedEntries.remove(latestListedEntryTimestampThisCycleMillis);
                }
            }

            for (Map.Entry<Long, List<T>> timestampEntities : orderedEntries.entrySet()) {
                List<T> entities = timestampEntities.getValue();
                if (timestampEntities.getKey().equals(lastProcessedLatestEntryTimestampMillis)) {
                    // Filter out previously processed entities.
                    entities = entities.stream().filter(entity -> !latestIdentifiersProcessed.contains(entity.getIdentifier())).collect(Collectors.toList());
                }

                for (T entity : entities) {
                    // Create the FlowFile for this path.
                    final Map<String, String> attributes = createAttributes(entity, context);
                    FlowFile flowFile = session.create();
                    flowFile = session.putAllAttributes(flowFile, attributes);
                    session.transfer(flowFile, REL_SUCCESS);
                    flowfilesCreated++;
                }
            }
        }

        // As long as we have a listing timestamp, there is meaningful state to capture regardless of any outputs generated
        if (latestListedEntryTimestampThisCycleMillis != null) {
            boolean processedNewFiles = flowfilesCreated > 0;
            if (processedNewFiles) {
                // If there have been files created, update the last timestamp we processed.
                // Retrieving lastKey instead of using latestListedEntryTimestampThisCycleMillis is intentional here,
                // because latestListedEntryTimestampThisCycleMillis might be removed if it's not old enough.
                if (!orderedEntries.lastKey().equals(lastProcessedLatestEntryTimestampMillis)) {
                    // If the latest timestamp at this cycle becomes different than the previous one, we need to clear identifiers.
                    // If it didn't change, we need to add identifiers.
                    latestIdentifiersProcessed.clear();
                }
                // Capture latestIdentifierProcessed.
                latestIdentifiersProcessed.addAll(orderedEntries.lastEntry().getValue().stream().map(T::getIdentifier).collect(Collectors.toList()));
                lastProcessedLatestEntryTimestampMillis = orderedEntries.lastKey();
                getLogger().info("Successfully created listing with {} new objects", new Object[]{flowfilesCreated});
                session.commit();
            }

            lastRunTimeNanos = currentRunTimeNanos;

            if (!latestListedEntryTimestampThisCycleMillis.equals(lastListedLatestEntryTimestampMillis) || processedNewFiles) {
                // We have performed a listing and pushed any FlowFiles out that may have been generated
                // Now, we need to persist state about the Last Modified timestamp of the newest file
                // that we evaluated. We do this in order to avoid pulling in the same file twice.
                // However, we want to save the state both locally and remotely.
                // We store the state remotely so that if a new Primary Node is chosen, it can pick up where the
                // previously Primary Node left off.
                // We also store the state locally so that if the node is restarted, and the node cannot contact
                // the distributed state cache, the node can continue to run (if it is primary node).
                try {
                    lastListedLatestEntryTimestampMillis = latestListedEntryTimestampThisCycleMillis;
                    persist(latestListedEntryTimestampThisCycleMillis, lastProcessedLatestEntryTimestampMillis, latestIdentifiersProcessed, context.getStateManager(), getStateScope(context));
                } catch (final IOException ioe) {
                    getLogger().warn("Unable to save state due to {}. If NiFi is restarted before state is saved, or "
                        + "if another node begins executing this Processor, data duplication may occur.", ioe);
                }
            }

        } else {
            getLogger().debug("There is no data to list. Yielding.");
            context.yield();

            // lastListingTime = 0 so that we don't continually poll the distributed cache / local file system
            if (lastListedLatestEntryTimestampMillis == null) {
                lastListedLatestEntryTimestampMillis = 0L;
            }

            return;
        }
    }

    /**
     * This method is intended to be overridden by SubClasses those do not support TARGET_SYSTEM_TIMESTAMP_PRECISION property.
     * So that it use return different precisions than PRECISION_AUTO_DETECT.
     * If TARGET_SYSTEM_TIMESTAMP_PRECISION is supported as a valid Processor property,
     * then PRECISION_AUTO_DETECT will be the default value when not specified by a user.
     * @return
     */
    protected String getDefaultTimePrecision() {
        return TARGET_SYSTEM_TIMESTAMP_PRECISION.getDefaultValue();
    }

    private void resetTimeStates() {
        lastListedLatestEntryTimestampMillis = null;
        lastProcessedLatestEntryTimestampMillis = 0L;
        lastRunTimeNanos = 0L;
        latestIdentifiersProcessed.clear();
    }

    /**
     * Creates a Map of attributes that should be applied to the FlowFile to represent this entity. This processor will emit a FlowFile for each "new" entity
     * (see the documentation for this class for a discussion of how this class determines whether or not an entity is "new"). The FlowFile will contain no
     * content. The attributes that will be included are exactly the attributes that are returned by this method.
     *
     * @param entity  the entity represented by the FlowFile
     * @param context the ProcessContext for obtaining configuration information
     * @return a Map of attributes for this entity
     */
    protected abstract Map<String, String> createAttributes(T entity, ProcessContext context);

    /**
     * Returns the path to perform a listing on.
     * Many resources can be comprised of a "path" (or a "container" or "bucket", etc.) as well as name or identifier that is unique only
     * within that path. This method is responsible for returning the path that is currently being polled for entities. If this does concept
     * does not apply for the concrete implementation, it is recommended that the concrete implementation return "." or "/" for all invocations of this method.
     *
     * @param context the ProcessContex to use in order to obtain configuration
     * @return the path that is to be used to perform the listing, or <code>null</code> if not applicable.
     */
    protected abstract String getPath(final ProcessContext context);

    /**
     * Performs a listing of the remote entities that can be pulled. If any entity that is returned has already been "discovered" or "emitted"
     * by this Processor, it will be ignored. A discussion of how the Processor determines those entities that have already been emitted is
     * provided above in the documentation for this class. Any entity that is returned by this method with a timestamp prior to the minTimestamp
     * will be filtered out by the Processor. Therefore, it is not necessary that implementations perform this filtering but can be more efficient
     * if the filtering can be performed on the server side prior to retrieving the information.
     *
     * @param context      the ProcessContex to use in order to pull the appropriate entities
     * @param minTimestamp the minimum timestamp of entities that should be returned.
     * @return a Listing of entities that have a timestamp >= minTimestamp
     */
    protected abstract List<T> performListing(final ProcessContext context, final Long minTimestamp) throws IOException;

    /**
     * Determines whether or not the listing must be reset if the value of the given property is changed
     *
     * @param property the property that has changed
     * @return <code>true</code> if a change in value of the given property necessitates that the listing be reset, <code>false</code> otherwise.
     */
    protected abstract boolean isListingResetNecessary(final PropertyDescriptor property);

    /**
     * Returns a Scope that specifies where the state should be managed for this Processor
     *
     * @param context the ProcessContext to use in order to make a determination
     * @return a Scope that specifies where the state should be managed for this Processor
     */
    protected abstract Scope getStateScope(final ProcessContext context);


    private static class StringSerDe implements Serializer<String>, Deserializer<String> {
        @Override
        public String deserialize(final byte[] value) throws DeserializationException, IOException {
            if (value == null) {
                return null;
            }

            return new String(value, StandardCharsets.UTF_8);
        }

        @Override
        public void serialize(final String value, final OutputStream out) throws SerializationException, IOException {
            out.write(value.getBytes(StandardCharsets.UTF_8));
        }
    }
}
