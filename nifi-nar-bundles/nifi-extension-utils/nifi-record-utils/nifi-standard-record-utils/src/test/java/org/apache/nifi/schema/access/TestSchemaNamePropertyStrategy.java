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

import org.apache.nifi.components.PropertyValue;
import org.apache.nifi.schemaregistry.services.SchemaRegistry;
import org.apache.nifi.serialization.SimpleRecordSchema;
import org.apache.nifi.serialization.record.RecordField;
import org.apache.nifi.serialization.record.RecordFieldType;
import org.apache.nifi.serialization.record.RecordSchema;
import org.apache.nifi.serialization.record.SchemaIdentifier;
import org.apache.nifi.util.MockPropertyValue;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TestSchemaNamePropertyStrategy {

    private SchemaRegistry schemaRegistry;
    private RecordSchema recordSchema;

    @Before
    public void setup() {
        this.schemaRegistry = Mockito.mock(SchemaRegistry.class);

        final List<RecordField> fields = new ArrayList<>();
        fields.add(new RecordField("firstName", RecordFieldType.STRING.getDataType()));
        fields.add(new RecordField("lastName", RecordFieldType.STRING.getDataType()));

        final SchemaIdentifier schemaIdentifier = SchemaIdentifier.builder()
                .name("person").branch("master").version(1).id(1L).build();

        this.recordSchema = new SimpleRecordSchema(fields, schemaIdentifier);
    }

    @Test
    public void testNameOnly() throws SchemaNotFoundException, IOException {
        final PropertyValue nameValue = new MockPropertyValue("person");
        final PropertyValue branchValue = new MockPropertyValue(null);
        final PropertyValue versionValue = new MockPropertyValue(null);

        final SchemaNamePropertyStrategy schemaNamePropertyStrategy = new SchemaNamePropertyStrategy(
                schemaRegistry, nameValue, branchValue, versionValue);

        final SchemaIdentifier expectedSchemaIdentifier = SchemaIdentifier.builder()
                .name(nameValue.getValue())
                .build();

        when(schemaRegistry.retrieveSchema(argThat(new SchemaIdentifierMatcher(expectedSchemaIdentifier))))
                .thenReturn(recordSchema);

        final RecordSchema retrievedSchema = schemaNamePropertyStrategy.getSchema(Collections.emptyMap(), null, recordSchema);
        assertNotNull(retrievedSchema);
    }

    @Test
    public void testNameAndVersion() throws SchemaNotFoundException, IOException {
        final PropertyValue nameValue = new MockPropertyValue("person");
        final PropertyValue branchValue = new MockPropertyValue(null);
        final PropertyValue versionValue = new MockPropertyValue("1");

        final SchemaNamePropertyStrategy schemaNamePropertyStrategy = new SchemaNamePropertyStrategy(
                schemaRegistry, nameValue, branchValue, versionValue);

        final SchemaIdentifier expectedSchemaIdentifier = SchemaIdentifier.builder()
                .name(nameValue.getValue())
                .version(versionValue.asInteger())
                .build();

        when(schemaRegistry.retrieveSchema(argThat(new SchemaIdentifierMatcher(expectedSchemaIdentifier))))
                .thenReturn(recordSchema);

        final RecordSchema retrievedSchema = schemaNamePropertyStrategy.getSchema(Collections.emptyMap(), null, recordSchema);
        assertNotNull(retrievedSchema);
    }

    @Test
    public void testNameAndBlankVersion() throws SchemaNotFoundException, IOException {
        final PropertyValue nameValue = new MockPropertyValue("person");
        final PropertyValue branchValue = new MockPropertyValue(null);
        final PropertyValue versionValue = new MockPropertyValue("   ");

        final SchemaNamePropertyStrategy schemaNamePropertyStrategy = new SchemaNamePropertyStrategy(
                schemaRegistry, nameValue, branchValue, versionValue);

        final SchemaIdentifier expectedSchemaIdentifier = SchemaIdentifier.builder()
                .name(nameValue.getValue())
                .build();

        when(schemaRegistry.retrieveSchema(argThat(new SchemaIdentifierMatcher(expectedSchemaIdentifier))))
                .thenReturn(recordSchema);

        final RecordSchema retrievedSchema = schemaNamePropertyStrategy.getSchema(Collections.emptyMap(), null, recordSchema);
        assertNotNull(retrievedSchema);
    }

    @Test(expected = SchemaNotFoundException.class)
    public void testNameAndNonNumericVersion() throws SchemaNotFoundException, IOException {
        final PropertyValue nameValue = new MockPropertyValue("person");
        final PropertyValue branchValue = new MockPropertyValue(null);
        final PropertyValue versionValue = new MockPropertyValue("XYZ");

        final SchemaNamePropertyStrategy schemaNamePropertyStrategy = new SchemaNamePropertyStrategy(
                schemaRegistry, nameValue, branchValue, versionValue);

        schemaNamePropertyStrategy.getSchema(Collections.emptyMap(), null, recordSchema);
    }

    @Test
    public void testNameAndBranch() throws SchemaNotFoundException, IOException {
        final PropertyValue nameValue = new MockPropertyValue("person");
        final PropertyValue branchValue = new MockPropertyValue("test");
        final PropertyValue versionValue = new MockPropertyValue(null);

        final SchemaNamePropertyStrategy schemaNamePropertyStrategy = new SchemaNamePropertyStrategy(
                schemaRegistry, nameValue, branchValue, versionValue);

        final SchemaIdentifier expectedSchemaIdentifier = SchemaIdentifier.builder()
                .name(nameValue.getValue())
                .branch(branchValue.getValue())
                .build();

        when(schemaRegistry.retrieveSchema(argThat(new SchemaIdentifierMatcher(expectedSchemaIdentifier))))
                .thenReturn(recordSchema);

        final RecordSchema retrievedSchema = schemaNamePropertyStrategy.getSchema(Collections.emptyMap(), null, recordSchema);
        assertNotNull(retrievedSchema);
    }

    @Test
    public void testNameAndBlankBranch() throws SchemaNotFoundException, IOException {
        final PropertyValue nameValue = new MockPropertyValue("person");
        final PropertyValue branchValue = new MockPropertyValue("  ");
        final PropertyValue versionValue = new MockPropertyValue(null);

        final SchemaNamePropertyStrategy schemaNamePropertyStrategy = new SchemaNamePropertyStrategy(
                schemaRegistry, nameValue, branchValue, versionValue);

        final SchemaIdentifier expectedSchemaIdentifier = SchemaIdentifier.builder()
                .name(nameValue.getValue())
                .build();

        when(schemaRegistry.retrieveSchema(argThat(new SchemaIdentifierMatcher(expectedSchemaIdentifier))))
                .thenReturn(recordSchema);

        final RecordSchema retrievedSchema = schemaNamePropertyStrategy.getSchema(Collections.emptyMap(), null, recordSchema);
        assertNotNull(retrievedSchema);
    }

    /**
     * ArgumentMatcher for SchemaIdentifier.
     */
    private static final class SchemaIdentifierMatcher extends ArgumentMatcher<SchemaIdentifier> {

        private final SchemaIdentifier expectedIdentifier;

        public SchemaIdentifierMatcher(final SchemaIdentifier expectedIdentifier) {
            this.expectedIdentifier = expectedIdentifier;
        }

        @Override
        public boolean matches(final Object argument) {
            if (argument == null || !(argument instanceof SchemaIdentifier)) {
                return false;
            }

            final SchemaIdentifier other = (SchemaIdentifier) argument;
            return other.equals(expectedIdentifier);
        }
    }
}
