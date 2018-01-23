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
package org.apache.nifi.toolkit.registry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.nifi.toolkit.registry.command.bucket.CreateBucket;
import org.apache.nifi.toolkit.registry.command.flow.ExportFlow;
import org.apache.nifi.toolkit.registry.command.bucket.ListBuckets;
import org.apache.nifi.toolkit.registry.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NiFiRegistryMain {

    public static final Map<String, Command> COMMANDS;
    static {
        final List<Command> commandList = new ArrayList<>();
        commandList.add(new ListBuckets());
        commandList.add(new CreateBucket());
        commandList.add(new ExportFlow());

        final Map<String,Command> commandMap = new TreeMap<>();
        commandList.stream().forEach(cmd -> {
                cmd.initialize();
                commandMap.put(cmd.getName(), cmd);
        });

        COMMANDS = Collections.unmodifiableMap(commandMap);
    }

    public static void printBasicUsage(String errorMessage) {
        if (errorMessage != null) {
            System.out.println(errorMessage);
            System.out.println();
        }

        System.out.println("\nusage: <command> <args>");
        System.out.println("\ncommands:\n");
        COMMANDS.keySet().stream().forEach(k -> System.out.println("\t" + k));
        System.out.println("\thelp\n");
    }

    public static void printCommandUsage(String errorMessage, Command command) {
        if (errorMessage != null) {
            System.out.println(errorMessage);
            System.out.println();
            System.out.println();
        }

        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.setWidth(160);
        helpFormatter.printHelp(command.getName(), command.getOptions());
        System.out.flush();
    }

    private static CommandLine parseCli(Command command, String[] args) throws ParseException {
        final Options options = command.getOptions();
        final CommandLineParser parser = new DefaultParser();
        final CommandLine commandLine = parser.parse(options, args);

        if (commandLine.hasOption(Command.HELP_ARG)) {
            printCommandUsage(null, command);
            System.exit(1);
        }

        return commandLine;
    }

    public static void main(String[] args) {
        // if no args or only a help arg, then print the basic usage and exit
        if (args == null || args.length == 0 || (args.length == 1 && Command.HELP_ARG.equalsIgnoreCase(args[0])) ) {
            printBasicUsage(null);
            System.exit(1);
        }

        final String commandStr = args[0];

        // ensure the first arg is a valid command
        if (!COMMANDS.containsKey(commandStr)) {
            printBasicUsage("Unknown command: " + commandStr);
            System.exit(1);
        }

        final Command command = COMMANDS.get(commandStr);

        // valid command with no args, print command specific usage and exit
        if (args.length == 1) {
            printCommandUsage(null, command);
            System.exit(1);
        }

        try {
            final String[] otherArgs = Arrays.copyOfRange(args, 1, args.length, String[].class);
            final CommandLine commandLine = parseCli(command, otherArgs);
            command.execute(commandLine);
        } catch (Exception e) {
            printCommandUsage(e.getMessage(), command);
            System.out.println("");
            e.printStackTrace();
        }
    }

}
