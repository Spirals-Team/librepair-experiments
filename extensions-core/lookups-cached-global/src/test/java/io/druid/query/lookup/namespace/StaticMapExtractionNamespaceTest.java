/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.query.lookup.namespace;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import io.druid.jackson.DefaultObjectMapper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

public class StaticMapExtractionNamespaceTest
{
  private static final Map<String, String> MAP = ImmutableMap.<String, String>builder().put("foo", "bar").build();
  private static final ObjectMapper MAPPER = new DefaultObjectMapper();
  private static String MAP_STRING;

  @BeforeClass
  public static void setUpStatic() throws Exception
  {
    MAP_STRING = MAPPER.writeValueAsString(MAP);
  }

  @Test
  public void testSimpleSerDe() throws Exception
  {
    final String str = "{\"type\":\"staticMap\", \"map\":" + MAP_STRING + "}";
    final StaticMapExtractionNamespace extractionNamespace = MAPPER.readValue(str, StaticMapExtractionNamespace.class);
    Assert.assertEquals(MAP, extractionNamespace.getMap());
    Assert.assertEquals(0L, extractionNamespace.getPollMs());
    Assert.assertEquals(extractionNamespace, MAPPER.readValue(str, StaticMapExtractionNamespace.class));
    Assert.assertNotEquals(
        extractionNamespace,
        new StaticMapExtractionNamespace(ImmutableMap.<String, String>of("foo", "not_bar"))
    );
    Assert.assertNotEquals(
        extractionNamespace,
        new StaticMapExtractionNamespace(ImmutableMap.<String, String>of("not_foo", "bar"))
    );
  }
}
