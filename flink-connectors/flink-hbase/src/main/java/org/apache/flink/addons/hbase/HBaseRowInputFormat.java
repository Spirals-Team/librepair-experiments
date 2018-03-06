/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.addons.hbase;

import org.apache.flink.addons.hbase.strategy.TableInputSplitStrategyImpl;
import org.apache.flink.api.common.io.InputFormat;
import org.apache.flink.api.java.typeutils.ResultTypeQueryable;
import org.apache.flink.types.Row;

/**
 * {@link InputFormat} subclass that wraps the access for HTables. Returns the result as {@link Row}.
 */
public class HBaseRowInputFormat extends HBaseRowInputFormatTemplate implements ResultTypeQueryable<Row> {

	public HBaseRowInputFormat(org.apache.hadoop.conf.Configuration conf, String tableName, HBaseTableSchema schema) {
		super(conf, tableName, schema);
		tableInputSplitStrategy = new TableInputSplitStrategyImpl();
	}
}
