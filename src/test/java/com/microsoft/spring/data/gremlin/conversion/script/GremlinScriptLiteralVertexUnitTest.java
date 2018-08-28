/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.spring.data.gremlin.conversion.script;

import com.microsoft.spring.data.gremlin.common.domain.Person;
import com.microsoft.spring.data.gremlin.conversion.MappingGremlinConverter;
import com.microsoft.spring.data.gremlin.conversion.source.GremlinSource;
import com.microsoft.spring.data.gremlin.mapping.GremlinMappingContext;
import com.microsoft.spring.data.gremlin.repository.support.GremlinEntityInformation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GremlinScriptLiteralVertexUnitTest {

    private MappingGremlinConverter converter;
    private GremlinMappingContext mappingContext;
    private GremlinSource gremlinSource;

    @Mock
    private ApplicationContext applicationContext;

    @Before
    public void setup() {
        this.mappingContext = new GremlinMappingContext();
        this.mappingContext.setApplicationContext(this.applicationContext);
        this.mappingContext.afterPropertiesSet();
        this.mappingContext.getPersistentEntity(Person.class);
        this.converter = new MappingGremlinConverter(this.mappingContext);

        final Person person = new Person("123", "bill");
        @SuppressWarnings("unchecked") final GremlinEntityInformation info = new GremlinEntityInformation(Person.class);
        gremlinSource = info.getGremlinSource();
        this.converter.write(person, gremlinSource);
    }

    @Test
    public void testGenerateCountScript() {
        final List<String> queryList = new GremlinScriptLiteralVertex().generateCountScript(gremlinSource);
        assertEquals(queryList.get(0), "g.V()");
    }

    @Test
    public void testGenerateFindByIdScript() {
        final List<String> queryList = new GremlinScriptLiteralVertex().generateFindByIdScript(gremlinSource);
        assertEquals(queryList.get(0), "g.V('123')");
    }

    @Test
    public void testGenerateFindAllScript() {
        final List<String> queryList = new GremlinScriptLiteralVertex().generateFindAllScript(gremlinSource);
        assertEquals(queryList.get(0), "g.V().has(label, 'label-person')");
    }

    @Test
    public void testGenerateInsertScript() {
        final List<String> queryList = new GremlinScriptLiteralVertex().generateInsertScript(gremlinSource);
        assertEquals(queryList.get(0), "g.addV('label-person').property(id, '123').property('name', 'bill')");
    }

    @Test
    public void testGenerateUpdateScript() {
        final List<String> queryList = new GremlinScriptLiteralVertex().generateUpdateScript(gremlinSource);
        assertEquals(queryList.get(0), "g.V('123').property('name', 'bill')");
    }

    @Test
    public void testGenerateDeleteByIdScript() {
        final List<String> queryList = new GremlinScriptLiteralVertex().generateDeleteByIdScript(gremlinSource);
        assertEquals(queryList.get(0), "g.V('123').drop()");
    }

    @Test
    public void testGenerateDeleteAllScript() {
        final List<String> queryList = new GremlinScriptLiteralVertex().generateDeleteAllScript(gremlinSource);
        assertEquals(queryList.get(0), "g.V().drop()");
    }

    @Test
    public void testGenerateDeleteAllByClassScript() {
        final List<String> queryList = new GremlinScriptLiteralVertex().generateDeleteAllByClassScript(gremlinSource);
        assertEquals(queryList.get(0), "g.V().has(label, 'label-person').drop()");
    }
}
