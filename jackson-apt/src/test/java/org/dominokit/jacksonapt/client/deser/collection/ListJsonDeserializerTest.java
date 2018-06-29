/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dominokit.jacksonapt.client.deser.collection;

import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.client.deser.AbstractJsonDeserializerTest;
import org.dominokit.jacksonapt.deser.StringJsonDeserializer;
import org.dominokit.jacksonapt.deser.collection.ListJsonDeserializer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Nicolas Morel
 */
public class ListJsonDeserializerTest extends AbstractJsonDeserializerTest<List<String>> {

    @Override
    protected JsonDeserializer<List<String>> createDeserializer() {
        return ListJsonDeserializer.newInstance(StringJsonDeserializer.getInstance());
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization(Arrays.asList("Hello", " ", "World", "!"), "[Hello, \" \", \"World\", \"!\"]");
        assertDeserialization(Collections.<String>emptyList(), "[]");
    }

}
