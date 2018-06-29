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

package org.dominokit.jacksonapt.gwt.deser.date;

import org.dominokit.jacksonapt.deser.BaseDateJsonDeserializer.SqlTimestampJsonDeserializer;
import org.dominokit.jacksonapt.gwt.deser.AbstractJsonDeserializerTest;

import java.sql.Timestamp;

/**
 * @author Nicolas Morel
 */
public class SqlTimestampJsonDeserializerTest extends AbstractJsonDeserializerTest<Timestamp> {

    @Override
    protected SqlTimestampJsonDeserializer createDeserializer() {
        return SqlTimestampJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization(new Timestamp(1377543971773l), "1377543971773");
        assertDeserialization(new Timestamp(1345304756543l), "\"2012-08-18T15:45:56.543+0000\"");
    }
}
