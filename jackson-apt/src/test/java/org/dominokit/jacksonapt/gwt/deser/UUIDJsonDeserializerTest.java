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

package org.dominokit.jacksonapt.gwt.deser;

import org.dominokit.jacksonapt.JsonDeserializer;
import org.dominokit.jacksonapt.deser.UUIDJsonDeserializer;

import java.util.UUID;

/**
 * @author Nicolas Morel
 */
public class UUIDJsonDeserializerTest extends AbstractJsonDeserializerTest<UUID> {

    private static String uuid = "550e8400-e29b-41d4-a716-446655440000";

    @Override
    protected JsonDeserializer<UUID> createDeserializer() {
        return UUIDJsonDeserializer.getInstance();
    }

    @Override
    public void testDeserializeValue() {
        assertDeserialization(UUID.fromString(uuid), "\"" + uuid + "\"");
    }
}
