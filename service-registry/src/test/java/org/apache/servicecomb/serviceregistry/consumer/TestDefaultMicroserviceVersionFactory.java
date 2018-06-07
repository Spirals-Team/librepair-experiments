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

package org.apache.servicecomb.serviceregistry.consumer;

import org.apache.servicecomb.serviceregistry.RegistryUtils;
import org.apache.servicecomb.serviceregistry.api.registry.Microservice;
import org.junit.Assert;
import org.junit.Test;

import mockit.Expectations;
import mockit.Mocked;

public class TestDefaultMicroserviceVersionFactory {
  @Test
  public void create(@Mocked Microservice microservice) {
    String microserviceId = "id";
    new Expectations(RegistryUtils.class) {
      {
        RegistryUtils.getMicroservice(microserviceId);
        result = microservice;
        microservice.getVersion();
        result = "1.0.0";
      }
    };

    MicroserviceVersion microserviceVersion = new DefaultMicroserviceVersionFactory().create("", microserviceId);
    Assert.assertSame(microservice, microserviceVersion.getMicroservice());
    Assert.assertEquals("1.0.0", microserviceVersion.getVersion().getVersion());
  }
}
