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

package org.apache.jackrabbit.oak.segment.standby.codec;

import static org.apache.jackrabbit.oak.segment.standby.codec.Messages.newGetReferencesRequest;
import static org.junit.Assert.assertEquals;

import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;

public class GetReferencesRequestEncoderTest {

    @Test
    public void encodeRequest() throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(new GetReferencesRequestEncoder());
        channel.writeOutbound(new GetReferencesRequest("clientId", "segmentId"));
        String message = (String) channel.readOutbound();
        assertEquals(newGetReferencesRequest("clientId", "segmentId"), message);
    }

}
