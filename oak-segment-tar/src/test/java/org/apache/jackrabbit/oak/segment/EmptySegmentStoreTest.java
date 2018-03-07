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
 *
 */

package org.apache.jackrabbit.oak.segment;

import static org.apache.jackrabbit.oak.segment.SegmentStore.EMPTY_STORE;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Test;

public class EmptySegmentStoreTest {
    @Test
    public void containsSegment() {
        assertFalse(EMPTY_STORE.containsSegment(SegmentId.NULL));
    }

    @Test(expected = SegmentNotFoundException.class)
    public void readSegment() {
        EMPTY_STORE.readSegment(SegmentId.NULL);
    }

    @Test(expected = IOException.class)
    public void writeSegment() throws IOException {
        EMPTY_STORE.writeSegment(SegmentId.NULL, new byte[0], 0, 0);
    }
}
