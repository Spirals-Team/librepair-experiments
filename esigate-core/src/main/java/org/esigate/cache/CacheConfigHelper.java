/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.esigate.cache;

import java.util.Properties;

import org.apache.http.impl.client.cache.CacheConfig;
import org.esigate.ConfigurationException;
import org.esigate.Parameters;

public final class CacheConfigHelper {

    private CacheConfigHelper() {

    }

    public static CacheConfig createCacheConfig(Properties properties) {

        // Heuristic caching
        boolean heuristicCachingEnabled = Parameters.HEURISTIC_CACHING_ENABLED.getValue(properties);
        float heuristicCoefficient = Parameters.HEURISTIC_COEFFICIENT.getValue(properties);
        long heuristicDefaultLifetimeSecs = Parameters.HEURISTIC_DEFAULT_LIFETIME_SECS.getValue(properties);
        int maxCacheEntries = Parameters.MAX_CACHE_ENTRIES.getValue(properties);
        long maxObjectSize = Parameters.MAX_OBJECT_SIZE.getValue(properties);

        // Asynchronous revalidation
        int minAsynchronousWorkers = Parameters.MIN_ASYNCHRONOUS_WORKERS.getValue(properties);
        int maxAsynchronousWorkers = Parameters.MAX_ASYNCHRONOUS_WORKERS.getValue(properties);
        int asynchronousWorkerIdleLifetimeSecs = Parameters.ASYNCHRONOUS_WORKER_IDLE_LIFETIME_SECS.getValue(properties);
        int maxUpdateRetries = Parameters.MAX_UPDATE_RETRIES.getValue(properties);
        int revalidationQueueSize = Parameters.REVALIDATION_QUEUE_SIZE.getValue(properties);

        CacheConfig.Builder builder = CacheConfig.custom();
        builder.setHeuristicCachingEnabled(heuristicCachingEnabled);
        builder.setHeuristicCoefficient(heuristicCoefficient);
        builder.setHeuristicDefaultLifetime(heuristicDefaultLifetimeSecs);
        builder.setMaxCacheEntries(maxCacheEntries);
        long usedMaxObjectSize = Long.MAX_VALUE;
        if (maxObjectSize > 0) {
            usedMaxObjectSize = maxObjectSize;
        }
        builder.setMaxObjectSize(usedMaxObjectSize);
        builder.setAsynchronousWorkersCore(minAsynchronousWorkers);
        builder.setAsynchronousWorkersMax(maxAsynchronousWorkers);
        builder.setAsynchronousWorkerIdleLifetimeSecs(asynchronousWorkerIdleLifetimeSecs);
        builder.setMaxUpdateRetries(maxUpdateRetries).setRevalidationQueueSize(revalidationQueueSize);
        builder.setSharedCache(true);
        return builder.build();
    }

    public static CacheStorage createCacheStorage(Properties properties) {
        String cacheStorageClass = Parameters.CACHE_STORAGE.getValue(properties);
        Object cacheStorageObject;
        try {
            cacheStorageObject = Class.forName(cacheStorageClass).newInstance();
        } catch (Exception e) {
            throw new ConfigurationException("Could not instantiate cacheStorageClass", e);
        }
        if (!(cacheStorageObject instanceof CacheStorage)) {
            throw new ConfigurationException("Cache storage class must extend org.esigate.cache.CacheStorage.");
        }
        CacheStorage cacheStorage = (CacheStorage) cacheStorageObject;
        cacheStorage.init(properties);
        return cacheStorage;
    }

}
