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

package org.dominokit.jacksonapt.server.deser.map.key;

import org.dominokit.jacksonapt.deser.map.key.BaseNumberKeyDeserializer;
import org.junit.Test;

/**
 * @author Nicolas Morel
 */
public class FloatKeyDeserializerTest extends AbstractKeyDeserializerTest<Float> {

    @Override
    protected BaseNumberKeyDeserializer.FloatKeyDeserializer createDeserializer() {
        return BaseNumberKeyDeserializer.FloatKeyDeserializer.getInstance();
    }

    @Override
    @Test
	public void testDeserializeValue() {
        assertDeserialization(new Float("34.10245"), "34.10245");
        assertDeserialization(new Float("-784.15454"), "-784.15454");
    }
}
