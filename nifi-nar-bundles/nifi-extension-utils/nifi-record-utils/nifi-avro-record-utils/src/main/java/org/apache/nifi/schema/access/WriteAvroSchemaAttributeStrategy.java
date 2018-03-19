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

package org.apache.nifi.schema.access;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.avro.Schema;
import org.apache.nifi.avro.AvroTypeUtil;
import org.apache.nifi.serialization.record.RecordSchema;

public class WriteAvroSchemaAttributeStrategy implements SchemaAccessWriter {
    private final Map<RecordSchema, String> avroSchemaTextCache = new LinkedHashMap<RecordSchema, String>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<RecordSchema, String> eldest) {
            return size() > 10;
        }
    };

    @Override
    public void writeHeader(final RecordSchema schema, final OutputStream out) throws IOException {
    }

    @Override
    public Map<String, String> getAttributes(final RecordSchema schema) {
        // First, check if schema has the Avro Text available already.
        final Optional<String> schemaFormat = schema.getSchemaFormat();
        if (schemaFormat.isPresent() && AvroTypeUtil.AVRO_SCHEMA_FORMAT.equals(schemaFormat.get())) {
            final Optional<String> schemaText = schema.getSchemaText();
            if (schemaText.isPresent()) {
                return Collections.singletonMap("avro.schema", schemaText.get());
            }
        }

        String schemaText = avroSchemaTextCache.get(schema);
        if (schemaText == null) {
            final Schema avroSchema = AvroTypeUtil.extractAvroSchema(schema);
            schemaText = avroSchema.toString();
            avroSchemaTextCache.put(schema, schemaText);
        }

        return Collections.singletonMap("avro.schema", schemaText);
    }

    @Override
    public void validateSchema(final RecordSchema schema) throws SchemaNotFoundException {
    }

    @Override
    public Set<SchemaField> getRequiredSchemaFields() {
        return EnumSet.noneOf(SchemaField.class);
    }
}
