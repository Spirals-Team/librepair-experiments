/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.streaming.examples.queryablestate;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.queryablestate.client.QueryableStateClient;
import org.apache.flink.queryablestate.exceptions.UnknownKeyOrNamespaceException;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Javadoc.
 */
public class QsBugClient {

	private static final String QS_HOST = "127.0.0.1";
	private static final JobID JOB_ID = JobID.fromHexString("9441bbe93eaa3505da1da0d1089616d7"); // TODO: changeme

	public static void main(final String[] args) throws Exception {
		QueryableStateClient client = new QueryableStateClient(QS_HOST, 9069);

		MapStateDescriptor<EmailId, EmailInformation> stateDescriptor = new MapStateDescriptor<>(
				"state",
				TypeInformation.of(new TypeHint<EmailId>() {
				}),
				TypeInformation.of(new TypeHint<EmailInformation>() {
				})
		);

		client.setExecutionConfig(new ExecutionConfig());

		String yesterdayAsADateString = DateTimeFormatter
				.ofPattern("yyyy-MM-dd")
				.withZone(ZoneId.of("UTC"))
				.format(Instant.now().minus(Duration.ofDays(1)));

		while (true) {
			CompletableFuture<MapState<EmailId, EmailInformation>> resultFuture =
					client.getKvState(
							JOB_ID,
							"state",
							yesterdayAsADateString,
							BasicTypeInfo.STRING_TYPE_INFO,
							stateDescriptor);

			final MapState<EmailId, EmailInformation> mapState;
			try {
				mapState = resultFuture.get();
			} catch (ExecutionException e) {
				if (e.getCause() instanceof UnknownKeyOrNamespaceException) {
					System.err.println("State doesn't exist yet; sleeping 500ms");
					Thread.sleep(500);
					continue;
				}

				throw(e);
			}

			int i = 0;
			for (Map.Entry<EmailId, EmailInformation> entry : mapState.entries()) {
				i++;
				final EmailId emailId = entry.getKey();
				final EmailInformation emailInformation = entry.getValue();
			}

			System.out.println("Found " + i + " records.");

			Thread.sleep(100);
		}
	}
}
