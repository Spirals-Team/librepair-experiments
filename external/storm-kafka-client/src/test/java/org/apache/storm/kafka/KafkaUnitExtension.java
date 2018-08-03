/*
 * Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package org.apache.storm.kafka;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class KafkaUnitExtension implements BeforeEachCallback, AfterEachCallback {

    private final KafkaUnit kafkaUnit;

    public KafkaUnitExtension() {
        this.kafkaUnit = new KafkaUnit();
    }

    @Override
    public void beforeEach(ExtensionContext ctx) throws Exception {
        kafkaUnit.setUp();
    }

    @Override
    public void afterEach(ExtensionContext ctx) throws Exception {
        kafkaUnit.tearDown();
    }

    public KafkaUnit getKafkaUnit() {
        return this.kafkaUnit;
    }
}
