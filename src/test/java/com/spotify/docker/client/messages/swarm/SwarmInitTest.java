/*-
 * -\-\-
 * docker-client
 * --
 * Copyright (C) 2016 - 2017 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package com.spotify.docker.client.messages.swarm;

import static com.spotify.docker.FixtureUtil.fixture;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.docker.client.ObjectMapperProvider;
import org.junit.Test;

public class SwarmInitTest {

  private ObjectMapper objectMapper = ObjectMapperProvider.objectMapper();

  @Test
  public void test1_32_WithoutNullables() throws Exception {
    final SwarmInit init = objectMapper.readValue(fixture(
        "fixtures/1.32/swarmInitWithoutNullables.json"), SwarmInit.class);
    assertThat(init.listenAddr(), equalTo("0.0.0.0:2377"));
    assertThat(init.advertiseAddr(), equalTo("192.168.1.1:2377"));
    assertThat(init.forceNewCluster(), is(nullValue()));
    assertThat(init.swarmSpec(), is(nullValue()));
  }
}