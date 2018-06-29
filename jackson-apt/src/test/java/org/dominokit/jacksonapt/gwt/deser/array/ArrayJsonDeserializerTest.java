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

package org.dominokit.jacksonapt.gwt.deser.array;

import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.deser.StringJsonDeserializer;
import org.dominokit.jacksonapt.deser.array.ArrayJsonDeserializer;
import org.dominokit.jacksonapt.gwt.deser.AbstractJsonDeserializerTest;

import java.util.Arrays;

/**
 * @author Nicolas Morel
 */
public class ArrayJsonDeserializerTest extends AbstractJsonDeserializerTest<String[]> {

    @Override
    protected JsonDeserializer<String[]> createDeserializer() {
        return ArrayJsonDeserializer.newInstance(StringJsonDeserializer.getInstance(), String[]::new);
    }

    @Override
    public void testDeserializeValue() {
        assertTrue(Arrays.deepEquals(new String[]{"Hello", " ", "World", "!"}, deserialize("[Hello, \" \", \"World\", " +
                "" + "\"!\"]")));
        assertTrue(Arrays.deepEquals(new String[0], deserialize("[]")));
    }

}
