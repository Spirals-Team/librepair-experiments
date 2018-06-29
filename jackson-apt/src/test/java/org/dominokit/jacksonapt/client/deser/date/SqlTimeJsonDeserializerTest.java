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

package org.dominokit.jacksonapt.client.deser.date;

import org.dominokit.jacksonapt.client.deser.AbstractJsonDeserializerTest;
import org.dominokit.jacksonapt.deser.BaseDateJsonDeserializer.SqlTimeJsonDeserializer;

import java.sql.Time;

/**
 * @author Nicolas Morel
 */
public class SqlTimeJsonDeserializerTest extends AbstractJsonDeserializerTest<Time> {

    @Override
    protected SqlTimeJsonDeserializer createDeserializer() {
        return SqlTimeJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization(new Time(1377543971773l), "1377543971773");
        Time time = new Time(getUTCTime(2012, 8, 18, 15, 45, 56, 543));
        assertEquals(time.toString(), deserialize("\"" + time.toString() + "\"").toString());
    }
}
