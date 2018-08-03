/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.  The ASF licenses this file to you under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package org.apache.storm.redis.common.mapper;

import org.apache.storm.topology.OutputFieldsDeclarer;

/**
 * RedisFilterMapper is for defining spec. which is used for querying value from Redis and filtering.
 */
public interface RedisFilterMapper extends TupleMapper, RedisMapper {

    /**
     * declare what are the fields that this code will output.
     * @param declarer OutputFieldsDeclarer
     */
    void declareOutputFields(OutputFieldsDeclarer declarer);
}
