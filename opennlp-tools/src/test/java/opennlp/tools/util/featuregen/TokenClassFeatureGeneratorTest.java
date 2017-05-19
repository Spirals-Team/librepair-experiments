/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opennlp.tools.util.featuregen;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TokenClassFeatureGeneratorTest {

  private List<String> features;
  static String[] testSentence = new String[] {"This", "is", "an", "Example", "sentence"};

  @Before
  public void setUp() throws Exception {
    features = new ArrayList<>();
  }

  @Test
  public void testGenWAC() {

    final int testTokenIndex = 3;

    AdaptiveFeatureGenerator generator = new TokenClassFeatureGenerator(true);

    generator.createFeatures(features, testSentence, testTokenIndex, null);

    Assert.assertEquals(2, features.size());
    Assert.assertEquals("wc=ic", features.get(0));
    Assert.assertEquals("w&c=example,ic", features.get(1));
  }

  @Test
  public void testNoWAC() {

    final int testTokenIndex = 3;

    AdaptiveFeatureGenerator generator = new TokenClassFeatureGenerator(false);

    generator.createFeatures(features, testSentence, testTokenIndex, null);

    Assert.assertEquals(1, features.size());
    Assert.assertEquals("wc=ic", features.get(0));
  }
}
