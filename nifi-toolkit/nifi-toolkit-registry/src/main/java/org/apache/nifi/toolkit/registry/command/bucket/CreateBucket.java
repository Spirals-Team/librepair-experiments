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
package org.apache.nifi.toolkit.registry.command.bucket;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.nifi.registry.bucket.Bucket;
import org.apache.nifi.registry.client.BucketClient;
import org.apache.nifi.registry.client.NiFiRegistryClient;
import org.apache.nifi.registry.client.NiFiRegistryException;
import org.apache.nifi.toolkit.registry.command.AbstractCommand;

import java.io.IOException;

/**
 * Creates a new bucket in the registry.
 */
public class CreateBucket extends AbstractCommand {

    public CreateBucket() {
        super("create-bucket");
    }

    @Override
    public void initialize() {
        addOptionWithArg("bn", BUCKET_NAME_ARG, "The name of the bucket to create");
        addOptionWithArg("bd", BUCKET_DESC_ARG, "The description of the bucket to create (optional)");
    }

    @Override
    protected void doExecute(final CommandLine commandLine) throws ParseException, IOException, NiFiRegistryException {
        final String bucketName = getRequiredArg(commandLine, BUCKET_NAME_ARG);
        final String bucketDesc = getArg(commandLine, BUCKET_DESC_ARG);

        try (final NiFiRegistryClient client = createClient(commandLine);) {
            final BucketClient bucketClient = client.getBucketClient();

            logger.info("Creating bucket {}", new Object[] {bucketName});

            final Bucket bucket = new Bucket();
            bucket.setName(bucketName);
            bucket.setDescription(bucketDesc);

            final Bucket createdBucket = bucketClient.create(bucket);
            writeResult(commandLine, createdBucket);
        }
    }
}
