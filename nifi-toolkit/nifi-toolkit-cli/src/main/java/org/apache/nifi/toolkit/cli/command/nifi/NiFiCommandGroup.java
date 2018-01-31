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
package org.apache.nifi.toolkit.cli.command.nifi;

import org.apache.nifi.toolkit.cli.command.AbstractCommandGroup;
import org.apache.nifi.toolkit.cli.command.Command;
import org.apache.nifi.toolkit.cli.command.nifi.pg.ImportPG;
import org.apache.nifi.toolkit.cli.command.nifi.registry.CreateRegistryClient;
import org.apache.nifi.toolkit.cli.command.nifi.registry.ListRegistryClients;
import org.apache.nifi.toolkit.cli.command.nifi.registry.UpdateRegistryClient;

import java.util.ArrayList;
import java.util.List;

/**
 * CommandGroup for NiFi commands.
 */
public class NiFiCommandGroup extends AbstractCommandGroup {

    public NiFiCommandGroup() {
        super("nifi");
    }

    @Override
    protected List<Command> createCommands() {
        final List<Command> commands = new ArrayList<>();
        commands.add(new ListRegistryClients());
        commands.add(new CreateRegistryClient());
        commands.add(new UpdateRegistryClient());
        commands.add(new ImportPG());
        return commands;
    }
}
