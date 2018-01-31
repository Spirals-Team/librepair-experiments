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

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Holds information for the current CLI session.
 *
 * In interactive mode there will be one session object created when the CLI starts and will be used
 * across commands for the duration of the CLI.
 *
 */
public class Session {

    private static final String NIFI_CLIENT_ID = UUID.randomUUID().toString();

    private Map<SessionVariable,String> variables = new HashMap<>();

    public String getNiFiClientID() {
        return NIFI_CLIENT_ID;
    }

    public void set(final SessionVariable variable, final String value) {
        this.variables.put(variable, value);
    }

    public String get(final SessionVariable variable) {
        return this.variables.get(variable);
    }

    public void remove(final SessionVariable variable) {
        this.variables.remove(variable);
    }

    public void printVariables(final PrintStream output) {
        output.println();
        output.println("Current Session:");
        output.println();

        for (final Map.Entry<SessionVariable,String> entry : variables.entrySet()) {
            output.println(entry.getKey().getVariableName() + " =\t\t" + entry.getValue());
        }

        output.println();
    }
}
