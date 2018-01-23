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
package org.apache.nifi.toolkit.registry.command.flow;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.nifi.registry.client.FlowSnapshotClient;
import org.apache.nifi.registry.client.NiFiRegistryClient;
import org.apache.nifi.registry.client.NiFiRegistryException;
import org.apache.nifi.registry.flow.VersionedFlowSnapshot;
import org.apache.nifi.toolkit.registry.command.AbstractCommand;

import java.io.IOException;

public class ExportFlow extends AbstractCommand {

    public ExportFlow() {
        super("export-flow");
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
        final String bucket = getRequiredArg(commandLine, BUCKET_ID_ARG);
        final String flow = getRequiredArg(commandLine, FLOW_ID_ARG);
        final Integer version = getRequiredIntArg(commandLine, FLOW_VERSION_ARG);

        logger.info("Retrieving flow with bucket {}, flow {}, and version {}", new Object[] {bucket, flow, version});

        try (NiFiRegistryClient client = createClient(commandLine)) {
            FlowSnapshotClient flowSnapshotClient = client.getFlowSnapshotClient();
            VersionedFlowSnapshot versionedFlowSnapshot = flowSnapshotClient.get(bucket, flow, version);
            writeResult(commandLine, versionedFlowSnapshot);
        }
    }

}
