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

package io.hekate.messaging;

import io.hekate.cluster.ClusterNodeId;

/**
 * Remote messaging endpoint.
 *
 * <p>
 * This interface provides information about the remote peer of a {@link Message}.
 * </p>
 *
 * @param <T> Base type of messages that can be supported by this endpoint.
 *
 * @see Message#endpoint()
 */
public interface MessagingEndpoint<T> {
    /**
     * Returns the universally unique identifier of the remote cluster node.
     *
     * @return Universally unique identifier of the remote cluster node.
     */
    ClusterNodeId remoteNodeId();

    /**
     * Returns the custom context object that was set via {@link #setContext(Object)}.
     *
     * @param <C> Type of context object.
     *
     * @return Context object.
     */
    <C> C getContext();

    /**
     * Sets the custom context object that should be associated with this endpoint.
     *
     * @param ctx Context object.
     */
    void setContext(Object ctx);

    /**
     * Returns the messaging channel of this endpoint.
     *
     * @return Messaging channel.
     */
    MessagingChannel<T> channel();
}
