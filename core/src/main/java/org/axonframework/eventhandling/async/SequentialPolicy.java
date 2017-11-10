/*
 * Copyright (c) 2010-2014. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.eventhandling.async;

/**
 * SequencingPolicy that requires serialized handling of all events delivered to an event handler. This is the default
 * policy for event handlers.
 *
 * @author Allard Buijze
 * @since 0.3
 */
public class SequentialPolicy implements SequencingPolicy<Object> {

    private static final Object FULL_SEQUENTIAL_POLICY = new Object();

    @Override
    public Object getSequenceIdentifierFor(Object task) {
        return FULL_SEQUENTIAL_POLICY;
    }
}
