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
package org.apache.nifi.toolkit.cli;

import org.apache.commons.lang3.Validate;
import org.apache.nifi.toolkit.cli.command.nifi.client.NiFiClientFactory;
import org.apache.nifi.toolkit.cli.command.registry.client.NiFiRegistryClientFactory;

import java.io.PrintStream;

/**
 * Context for the CLI which will be passed to each command.
 */
public class CLIContext {

    private final NiFiClientFactory niFiClientFactory;
    private final NiFiRegistryClientFactory niFiRegistryClientFactory;
    private final Session session;
    private final PrintStream output;

    private CLIContext(final Builder builder) {
        this.niFiClientFactory = builder.niFiClientFactory;
        this.niFiRegistryClientFactory = builder.niFiRegistryClientFactory;
        this.session = builder.session;
        this.output = builder.output;

        Validate.notNull(this.niFiClientFactory);
        Validate.notNull(this.niFiRegistryClientFactory);
        Validate.notNull(this.session);
        Validate.notNull(this.output);
    }

    public NiFiClientFactory getNiFiClientFactory() {
        return niFiClientFactory;
    }

    public NiFiRegistryClientFactory getNiFiRegistryClientFactory() {
        return niFiRegistryClientFactory;
    }

    public Session getSession() {
        return session;
    }

    public PrintStream getOutput() {
        return output;
    }

    public static class Builder {
        private NiFiClientFactory niFiClientFactory;
        private NiFiRegistryClientFactory niFiRegistryClientFactory;
        private Session session;
        private PrintStream output;

        public Builder nifiClientFactory(final NiFiClientFactory niFiClientFactory) {
            this.niFiClientFactory = niFiClientFactory;
            return this;
        }

        public Builder nifiRegistryClientFactory(final NiFiRegistryClientFactory niFiRegistryClientFactory) {
            this.niFiRegistryClientFactory = niFiRegistryClientFactory;
            return this;
        }

        public Builder session(final Session session) {
            this.session = session;
            return this;
        }

        public Builder output(final PrintStream output) {
            this.output = output;
            return this;
        }

        public CLIContext build() {
            return new CLIContext(this);
        }

    }
}
