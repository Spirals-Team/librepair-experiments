/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package io.ballerina.messaging.broker.amqp.codec;

/**
 * Keep constants related to AMQP codec which are shared in many places.
 */
public class AmqConstant {
    public static final int COMMAND_INVALID = 503;

    /**
     * Reply code to be used when the broker enforces connection close.
     */
    public static final int CONNECTION_FORCED = 320;

    /**
     * Reply code to be used when the broker enforces channel close.
     */
    public static final int CHANNEL_CLOSED = 410;

    public static final String TRANSPORT_PROPERTY_CHANNEL_ID = "channelId";
    public static final String TRANSPORT_PROPERTY_CONNECTION_ID = "connectionId";

    private AmqConstant() {
    }
}
