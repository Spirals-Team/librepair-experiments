/**
 * Copyright © 2016-2018 The Thingsboard Authors
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
package org.thingsboard.server.extensions.rabbitmq.plugin;

import lombok.Data;

import java.util.List;

/**
 * @author Andrew Shvayka
 */
@Data
public class RabbitMqPluginConfiguration {
    private String host;
    private int port;
    private String virtualHost;

    private String userName;
    private String password;

    private Boolean automaticRecoveryEnabled;

    private Integer connectionTimeout;
    private Integer handshakeTimeout;

    private List<RabbitMqPluginProperties> clientProperties;

    @Data
    public static class RabbitMqPluginProperties {
        private String key;
        private String value;
    }

}
