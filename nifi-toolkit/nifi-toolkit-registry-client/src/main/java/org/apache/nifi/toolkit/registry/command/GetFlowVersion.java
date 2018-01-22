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
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.nifi.registry.client.FlowSnapshotClient;
import org.apache.nifi.registry.client.NiFiRegistryClient;
import org.apache.nifi.registry.client.NiFiRegistryException;
import org.apache.nifi.registry.flow.VersionedFlowSnapshot;

import java.io.IOException;
import java.io.OutputStream;

public class GetFlowVersion extends AbstractNiFiRegistryCommand {

    public GetFlowVersion() {
        super("get-flow-version");
    }

    @Override
    public void initialize() {
        addBucketIdentifierOption();
        addFlowIdentifierOption();
        addFlowVersionOption();
        addOutputFileOption();
    }

    @Override
    public void doExecute(final CommandLine commandLine) throws ParseException, IOException, NiFiRegistryException {
        final String bucket = commandLine.getOptionValue(BUCKET_ID_ARG);
        if (StringUtils.isBlank(bucket)) {
            throw new MissingOptionException("Missing required option: " + BUCKET_ID_ARG);
        }

        final String flow = commandLine.getOptionValue(FLOW_ID_ARG);
        if (StringUtils.isBlank(flow)) {
            throw new MissingOptionException("Missing required option: " + FLOW_ID_ARG);
        }

        final String versionStr = commandLine.getOptionValue(FLOW_VERSION_ARG);
        if (StringUtils.isBlank(versionStr)) {
            throw new MissingOptionException("Missing required option: " + FLOW_VERSION_ARG);
        }

        int version = -1;
        try {
            version = Integer.valueOf(versionStr);
        } catch (Exception e) {
            throw new ParseException("Version must be numeric: " + versionStr);
        }

        logger.info("Retrieving flow version with bucket {}, flow {}, and version {}", new Object[] {bucket, flow, versionStr});

        // TODO should we support proxied-entities?
        final NiFiRegistryClient client = createClient(commandLine);
        final FlowSnapshotClient flowSnapshotClient = client.getFlowSnapshotClient();
        final VersionedFlowSnapshot versionedFlowSnapshot = flowSnapshotClient.get(bucket, flow, version);

        try (final OutputStream resultOut = getResultOutputStream(commandLine)) {
            OBJECT_WRITER.writeValue(resultOut, versionedFlowSnapshot);
        }
    }

}
