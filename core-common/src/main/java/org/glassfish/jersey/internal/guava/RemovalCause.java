/*
 * Copyright (C) 2011 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.glassfish.jersey.internal.guava;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * The reason why a cached entry was removed.
 *
 * @author Charles Fry
 * @since 10.0
 */
public enum RemovalCause {
    /**
     * The entry was manually removed by the user. This can result from the user invoking
     * {@link Cache#invalidate}, {@link Cache#invalidateAll(Iterable)}, {@link Cache#invalidateAll()},
     * {@link Map#remove}, {@link ConcurrentMap#remove}, or {@link Iterator#remove}.
     */
    EXPLICIT {
    },

    /**
     * The entry itself was not actually removed, but its value was replaced by the user. This can
     * result from the user invoking {@link Cache#put}, {@link LoadingCache#refresh}, {@link Map#put},
     * {@link Map#putAll}, {@link ConcurrentMap#replace(Object, Object)}, or
     * {@link ConcurrentMap#replace(Object, Object, Object)}.
     */
    REPLACED {
    },

    /**
     * The entry was removed automatically because its key or value was garbage-collected. This
     * can occur when using {@link CacheBuilder#weakKeys}, {@link CacheBuilder#weakValues}, or
     * {@link CacheBuilder#softValues}.
     */
    COLLECTED {
    },

    /**
     * The entry's expiration timestamp has passed. This can occur when using
     * {@link CacheBuilder#expireAfterWrite} or {@link CacheBuilder#expireAfterAccess}.
     */
    EXPIRED {
    },

    /**
     * The entry was evicted due to size constraints. This can occur when using
     * {@link CacheBuilder#maximumSize} or {@link CacheBuilder#maximumWeight}.
     */
    SIZE {
    }

}
