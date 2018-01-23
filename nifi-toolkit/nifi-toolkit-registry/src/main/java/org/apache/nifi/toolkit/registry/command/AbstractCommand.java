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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.nifi.registry.client.NiFiRegistryClient;
import org.apache.nifi.registry.client.NiFiRegistryClientConfig;
import org.apache.nifi.registry.client.NiFiRegistryException;
import org.apache.nifi.registry.client.impl.JerseyNiFiRegistryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Base class for all commands.
 */
public abstract class AbstractCommand implements Command {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ObjectMapper MAPPER = new ObjectMapper();
    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.setPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.NON_NULL));
        MAPPER.setAnnotationIntrospector(new JaxbAnnotationIntrospector(MAPPER.getTypeFactory()));
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected static final ObjectWriter OBJECT_WRITER = MAPPER.writerWithDefaultPrettyPrinter();


    private final String name;
    private final Options options;

    public AbstractCommand(final String name) {
        this.name = name;
        this.options = new Options();
        this.options.addOption("u", URL_ARG, true, "The URL of a NiFi Registry instance, such as http://localhost:18080");
        this.options.addOption("h", HELP_ARG, false, "Show help message and exit");
    }

    @Override
    public void initialize() {
        // sub-classes should override this method to add additional options via the helper method provided in this class
    }

    protected void addOptionWithArg(final String arg, final String longArg, final String description) {
        addOptionWithArg(arg, longArg, description, null);
    }

    protected void addOptionNoArg(final String arg, final String longArg, final String description) {
        options.addOption(arg, longArg, false, description);
    }

    protected void addOptionWithArg(final String arg, final String longArg, final String description, final Object defaultVal) {
        String fullDescription = description;
        if (defaultVal != null) {
            fullDescription += " (default: " + defaultVal + ")";
        }
        options.addOption(arg, longArg, true, fullDescription);
    }

    protected void addBucketIdentifierOption() {
        addOptionWithArg("b", BUCKET_ID_ARG, "A bucket identifier");
    }

    protected void addFlowIdentifierOption() {
        addOptionWithArg("f", FLOW_ID_ARG, "A flow identifier");
    }

    protected void addFlowVersionOption() {
        addOptionWithArg("v", FLOW_VERSION_ARG, "A numeric flow version");
    }

    protected void addOutputFileOption() {
        addOptionWithArg("o", OUTPUT_FILE_ARG, "A file to write results to, if not specified output is written to standard out");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void execute(final CommandLine commandLine) throws ParseException, NiFiRegistryException, IOException {
        if (!commandLine.hasOption(URL_ARG)) {
            throw new MissingOptionException("Missing required option: " + URL_ARG);
        }

        doExecute(commandLine);
    }

    /**
     * Sub-classes implement to perform the desired command given the provided CLI.
     *
     * @param commandLine the CLI for the command
     * @throws MissingOptionException if an option is missing
     */
    protected abstract void doExecute(final CommandLine commandLine) throws ParseException, IOException, NiFiRegistryException;


    protected NiFiRegistryClient createClient(final CommandLine commandLine) {
        final String url = commandLine.getOptionValue(URL_ARG);

        final NiFiRegistryClientConfig.Builder clientConfigBuilder = new NiFiRegistryClientConfig.Builder()
                .baseUrl(url);

        return new JerseyNiFiRegistryClient.Builder().config(clientConfigBuilder.build()).build();
    }

    protected OutputStream getResultOutputStream(final CommandLine commandLine) throws FileNotFoundException {
        if (commandLine.hasOption(OUTPUT_FILE_ARG)) {
            final String outputFile = commandLine.getOptionValue(OUTPUT_FILE_ARG);
            return new FileOutputStream(outputFile);
        } else {
            return System.out;
        }
    }

    protected void writeResult(final CommandLine commandLine, final Object result) throws IOException {
        try (final OutputStream resultOut = getResultOutputStream(commandLine)) {
            OBJECT_WRITER.writeValue(resultOut, result);
        }
    }

    protected String getArg(final CommandLine commandLine, final String argName) {
        return commandLine.getOptionValue(argName);
    }

    protected String getRequiredArg(final CommandLine commandLine, final String argName) throws MissingOptionException {
        final String argValue = commandLine.getOptionValue(argName);
        if (StringUtils.isBlank(argValue)) {
            throw new MissingOptionException("Missing required option: " + argName);
        }
        return argValue;
    }

    protected Integer getRequiredIntArg(final CommandLine commandLine, final String argName) throws ParseException {
        final String argValue = commandLine.getOptionValue(argName);
        if (StringUtils.isBlank(argValue)) {
            throw new MissingOptionException("Missing required option: " + argName);
        }

        try {
            return Integer.valueOf(argValue);
        } catch (Exception e) {
            throw new ParseException("Version must be numeric: " + argValue);
        }
    }
}
