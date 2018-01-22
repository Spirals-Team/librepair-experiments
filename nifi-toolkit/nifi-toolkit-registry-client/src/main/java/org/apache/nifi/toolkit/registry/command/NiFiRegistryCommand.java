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
package org.apache.nifi.toolkit.registry.command;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.nifi.registry.client.NiFiRegistryException;

import java.io.IOException;

/**
 * Represents a command to execute against NiFi registry.
 */
public interface NiFiRegistryCommand {

    String URL_ARG = "url";
    String BUCKET_ID_ARG = "bucketIdentifier";
    String FLOW_ID_ARG = "flowIdentifier";
    String FLOW_VERSION_ARG = "version";
    String OUTPUT_FILE_ARG = "outputFile";
    String HELP_ARG = "help";

    /**
     * Called directly after instantiation of the given command before any other method is called.
     */
    void initialize();

    /**
     * @return the name of the command that will be specified as the first argument to the tool
     */
    String getName();

    /**
     * @return the CLI options of the command
     */
    Options getOptions();

    /**
     * Executes the command with the given CLI params.
     *
     * @param cli the parsed CLI for the command
     */
    void execute(CommandLine cli) throws ParseException, NiFiRegistryException, IOException;

}
