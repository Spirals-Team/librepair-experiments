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

package org.apache.storm.lambda;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;

public class LambdaConsumerBolt extends BaseBasicBolt {

    private SerializableConsumer<Tuple> consumer;

    public LambdaConsumerBolt(SerializableConsumer<Tuple> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        consumer.accept(input);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // this bolt dosen't emit to downstream bolts
    }
}
