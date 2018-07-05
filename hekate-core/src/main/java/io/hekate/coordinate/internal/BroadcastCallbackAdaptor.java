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

import io.hekate.coordinate.CoordinationBroadcastCallback;
import io.hekate.coordinate.CoordinationMember;
import io.hekate.coordinate.CoordinationRequestCallback;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

class BroadcastCallbackAdaptor implements CoordinationRequestCallback {
    private final int expected;

    private final Map<CoordinationMember, Object> responses;

    private final AtomicBoolean completed = new AtomicBoolean();

    private final CoordinationBroadcastCallback callback;

    public BroadcastCallbackAdaptor(int expectedResponses, CoordinationBroadcastCallback callback) {
        this.expected = expectedResponses;
        this.callback = callback;
        this.responses = new HashMap<>(expectedResponses, 1.0f);
    }

    @Override
    public void onResponse(Object response, CoordinationMember from) {
        boolean allReceived;

        synchronized (responses) {
            responses.put(from, response);

            allReceived = responses.size() == expected;
        }

        if (allReceived && completed.compareAndSet(false, true)) {
            callback.onResponses(responses);
        }
    }

    @Override
    public void onCancel() {
        if (completed.compareAndSet(false, true)) {
            Map<CoordinationMember, Object> responsesCopy;

            synchronized (responses) {
                responsesCopy = new HashMap<>(responses);
            }

            callback.onCancel(responsesCopy);
        }
    }
}
