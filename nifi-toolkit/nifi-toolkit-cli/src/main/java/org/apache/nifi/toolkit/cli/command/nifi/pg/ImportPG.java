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
package org.apache.nifi.toolkit.cli.command.nifi.pg;

import org.apache.nifi.toolkit.cli.command.nifi.AbstractNiFiCommand;
import org.apache.nifi.toolkit.cli.command.nifi.client.NiFiClient;

import java.util.Properties;

/**
 * Command for importing a flow to NiFi from NiFi Registry.
 */
public class ImportPG extends AbstractNiFiCommand {

    public ImportPG() {
        super("import-gp");
    }

    @Override
    protected void doExecute(final NiFiClient client, final Properties properties) {
        println("Executing " + getName());
    }

}
