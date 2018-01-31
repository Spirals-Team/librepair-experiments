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
package org.apache.nifi.toolkit.cli.command.nifi.client;

import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.nifi.registry.security.util.KeystoreType;
import org.apache.nifi.toolkit.cli.command.CommandOption;
import org.apache.nifi.toolkit.cli.command.nifi.client.impl.JerseyNiFiClient;

import java.io.IOException;
import java.util.Properties;

/**
 * Factory for obtaining an instance of NiFiClient from the given properties.
 */
public class NiFiClientFactory {

    public NiFiClient createClient(final Properties properties) throws MissingOptionException {
        final String url = properties.getProperty(CommandOption.URL.getLongName());
        if (StringUtils.isBlank(url)) {
            throw new MissingOptionException("Missing required option '" + CommandOption.URL.getLongName() + "'");
        }

        final String keystore = properties.getProperty(CommandOption.KEYSTORE.getLongName());
        final String keystoreType = properties.getProperty(CommandOption.KEYSTORE_TYPE.getLongName());
        final String keystorePasswd = properties.getProperty(CommandOption.KEYSTORE_PASSWORD.getLongName());
        final String keyPasswd = properties.getProperty(CommandOption.KEY_PASSWORD.getLongName());

        final String truststore = properties.getProperty(CommandOption.TRUSTSTORE.getLongName());
        final String truststoreType = properties.getProperty(CommandOption.TRUSTSTORE_TYPE.getLongName());
        final String truststorePasswd = properties.getProperty(CommandOption.TRUSTSTORE_PASSWORD.getLongName());

        final String proxiedEntity = properties.getProperty(CommandOption.PROXIED_ENTITY.getLongName());
        final String protocol = properties.getProperty(CommandOption.PROTOCOL.getLongName());

        final boolean secureUrl = url.startsWith("https");

        if (secureUrl && (StringUtils.isBlank(truststore)
                || StringUtils.isBlank(truststoreType)
                || StringUtils.isBlank(truststorePasswd))
                ) {
            throw new MissingOptionException(CommandOption.TRUSTSTORE.getLongName() + ", " + CommandOption.TRUSTSTORE_TYPE.getLongName()
                    + ", and " + CommandOption.TRUSTSTORE_PASSWORD.getLongName() + " are required when using an https url");
        }

        final NiFiClientConfig.Builder clientConfigBuilder = new NiFiClientConfig.Builder()
                .baseUrl(url);

        if (secureUrl) {
            if (!StringUtils.isBlank(keystore)) {
                clientConfigBuilder.keystoreFilename(keystore);
            }
            if (!StringUtils.isBlank(keystoreType)) {
                clientConfigBuilder.keystoreType(KeystoreType.valueOf(keystoreType.toUpperCase()));
            }
            if (!StringUtils.isBlank(keystorePasswd)) {
                clientConfigBuilder.keystorePassword(keystorePasswd);
            }
            if (!StringUtils.isBlank(keyPasswd)) {
                clientConfigBuilder.keyPassword(keyPasswd);
            }
            if (!StringUtils.isBlank(truststore)) {
                clientConfigBuilder.truststoreFilename(truststore);
            }
            if (!StringUtils.isBlank(truststoreType)) {
                clientConfigBuilder.truststoreType(KeystoreType.valueOf(truststoreType.toUpperCase()));
            }
            if (!StringUtils.isBlank(truststorePasswd)) {
                clientConfigBuilder.truststorePassword(truststorePasswd);
            }
            if (!StringUtils.isBlank(protocol)) {
                clientConfigBuilder.protocol(protocol);
            }
        }

        final NiFiClient client = new JerseyNiFiClient.Builder().config(clientConfigBuilder.build()).build();

        // if a proxied entity was specified then return a wrapped client, otherwise return the regular client
        if (!StringUtils.isBlank(proxiedEntity)) {
            return new NiFiClientFactory.ProxiedNiFiClient(client, proxiedEntity);
        } else {
            return client;
        }
    }

    /**
     * Wraps a NiFiClient and ensures that all methods to obtain a more specific client will
     * call the proxied-entity variation so that callers don't have to care if proxying is taking place.
     */
    private static class ProxiedNiFiClient implements NiFiClient {

        private final String proxiedEntity;
        private final NiFiClient wrappedClient;

        public ProxiedNiFiClient(final NiFiClient wrappedClient, final String proxiedEntity) {
            this.proxiedEntity = proxiedEntity;
            this.wrappedClient = wrappedClient;
        }

        @Override
        public ControllerClient getControllerClient() {
            return wrappedClient.getControllerClient(proxiedEntity);
        }

        @Override
        public ControllerClient getControllerClient(String... proxiedEntity) {
            return wrappedClient.getControllerClient(proxiedEntity);
        }

        @Override
        public ControllerClient getControllerClient(String token) {
            return wrappedClient.getControllerClient(token);
        }

        @Override
        public void close() throws IOException {
            wrappedClient.close();
        }
    }
}
