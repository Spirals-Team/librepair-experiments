/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.spring.data.gremlin.conversion;

import com.microsoft.spring.data.gremlin.common.TestConstants;
import com.microsoft.spring.data.gremlin.common.domain.Person;
import com.microsoft.spring.data.gremlin.common.domain.Project;
import com.microsoft.spring.data.gremlin.common.domain.Relationship;
import com.microsoft.spring.data.gremlin.conversion.source.GremlinSource;
import com.microsoft.spring.data.gremlin.mapping.GremlinMappingContext;
import com.microsoft.spring.data.gremlin.repository.support.GremlinEntityInformation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;

@RunWith(MockitoJUnitRunner.class)
public class MappingGremlinConverterUnitTest {

    private MappingGremlinConverter converter;
    private GremlinMappingContext mappingContext;

    @Mock
    private ApplicationContext applicationContext;

    @Before
    public void setup() {
        this.mappingContext = new GremlinMappingContext();

        this.mappingContext.setApplicationContext(this.applicationContext);
        this.mappingContext.afterPropertiesSet();
        this.mappingContext.getPersistentEntity(Person.class);

        this.converter = new MappingGremlinConverter(this.mappingContext);
    }

    @Test
    public void testMappingGremlinConverterGetter() {
        Assert.assertEquals(this.converter.getMappingContext(), this.mappingContext);
        Assert.assertNotNull(this.converter.getConversionService());

        final Person person = new Person(TestConstants.VERTEX_PERSON_ID, TestConstants.VERTEX_PERSON_NAME);
        final Field[] fields = Person.class.getDeclaredFields();

        Assert.assertNotNull(this.converter.getPropertyAccessor(person));
        Assert.assertEquals(fields.length, 2);
        Assert.assertEquals(converter.getFieldValue(person, fields[0].getName()), TestConstants.VERTEX_PERSON_ID);
        Assert.assertEquals(converter.getFieldValue(person, fields[1].getName()), TestConstants.VERTEX_PERSON_NAME);
    }

    @Test
    public void testMappingGremlinConverterVertexRead() {
        final Person person = new Person(TestConstants.VERTEX_PERSON_ID, TestConstants.VERTEX_PERSON_NAME);
        final GremlinEntityInformation<Person, String> info = new GremlinEntityInformation<>(Person.class);
        final GremlinSource source = info.getGremlinSource();

        this.converter.write(person, source);

        Assert.assertEquals(source.getId(), person.getId());
        Assert.assertEquals(source.getProperties().get(TestConstants.PROPERTY_NAME), person.getName());
    }

    @Test
    public void testMappingGremlinConverterEdgeRead() {
        final Person person = new Person(TestConstants.VERTEX_PERSON_ID, TestConstants.VERTEX_PERSON_NAME);
        final Project project = new Project(TestConstants.VERTEX_PROJECT_ID, TestConstants.VERTEX_PROJECT_NAME,
                TestConstants.VERTEX_PROJECT_URI);
        final Relationship relationship = new Relationship(TestConstants.EDGE_RELATIONSHIP_ID,
                TestConstants.EDGE_RELATIONSHIP_NAME, TestConstants.EDGE_RELATIONSHIP_LOCATION, person, project);
        final GremlinEntityInformation<Relationship, String> info = new GremlinEntityInformation<>(Relationship.class);
        final GremlinSource source = info.getGremlinSource();

        this.converter.write(relationship, source);

        Assert.assertEquals(source.getId(), relationship.getId());
        Assert.assertEquals(source.getProperties().get(TestConstants.PROPERTY_NAME), relationship.getName());
    }
}

