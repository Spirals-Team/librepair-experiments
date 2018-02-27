/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.mongodb.core;

import static org.mockito.Mockito.*;
import static org.springframework.data.mongodb.test.util.Assertions.*;

import java.lang.reflect.Proxy;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate.SessionBoundMongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.DeleteOptions;
import com.mongodb.session.ClientSession;

/**
 * @author Christoph Strobl
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class SessionBoundMongoTemplateUnitTests {

	private static final String COLLECTION_NAME = "collection-1";

	MongoOperations template;

	@Mock MongoDbFactory factory;
	@Mock MongoCollection collection;
	@Mock MongoDatabase database;
	@Mock ClientSession clientSession;
	@Mock FindIterable findIterable;
	@Mock MongoCursor cursor;

	MappingMongoConverter converter;
	MongoMappingContext mappingContext;

	@Before
	public void setUp() {

		when(factory.getDb()).thenReturn(database);
		when(factory.getExceptionTranslator()).thenReturn(new MongoExceptionTranslator());
		when(database.getCollection(anyString(), any())).thenReturn(collection);
		when(collection.find(any(ClientSession.class), any(), any())).thenReturn(findIterable);
		when(findIterable.iterator()).thenReturn(cursor);
		when(cursor.hasNext()).thenReturn(false);
		when(findIterable.projection(any())).thenReturn(findIterable);

		this.mappingContext = new MongoMappingContext();
		this.converter = new MappingMongoConverter(new DefaultDbRefResolver(factory), mappingContext);
		this.template = new SessionBoundMongoTemplate(clientSession, factory, converter);
	}

	// @Test // DATAMONGO-1880
	// public void delegatesFindToFindWithSession() {
	//
	// template.prepareCollection(collection).find();
	//
	// verify(collection, never()).find();
	// verify(collection).find(clientSession);
	// }

	@Test // DATAMONGO-1880
	public void executeUsesProxiedCollectionInCallback() {

		template.execute("collection", MongoCollection::find);

		verify(collection, never()).find();
		verify(collection).find(eq(clientSession));
	}

	@Test // DATAMONGO-1880
	public void executeUsesProxiedDatabaseInCallback() {

		template.execute(MongoDatabase::listCollectionNames);

		verify(database, never()).listCollectionNames();
		verify(database).listCollectionNames(eq(clientSession));
	}

	@Test // DATAMONGO-1880
	public void findOneUsesProxiedCollection() {

		template.findOne(new Query(), Person.class);

		verify(collection).find(eq(clientSession), any(), any());
	}

	@Test // DATAMONGO-1880
	public void findShouldUseProxiedCollection() {

		template.find(new Query(), Person.class);

		verify(collection).find(eq(clientSession), any(), any());
	}

	@Test // DATAMONGO-1880
	public void findAllShouldUseProxiedCollection() {

		template.findAll(Person.class);

		verify(collection).find(eq(clientSession), any(), any());
	}

	@Test // DATAMONGO-1880
	public void executeCommandShouldUseProxiedDatabase() {

		template.executeCommand("{}");

		verify(database).runCommand(eq(clientSession), any(), any(Class.class));
	}

	@Test // DATAMONGO-1880
	public void removeShouldUseProxiedDatabase() {

		template.remove(new Query(), Person.class);

		verify(collection).deleteMany(eq(clientSession), any(), any(DeleteOptions.class));
	}

	@Test // DATAMONGO-1880
	public void insertShouldUseProxiedDatabase() {

		template.insert(new Person());

		verify(collection).insertOne(eq(clientSession), any(Document.class));
	}

	@Test // DATAMONGO-1880
	public void getCollectionShouldShouldJustReturnTheCollection/*No ClientSession binding*/() {
		assertThat(template.getCollection(COLLECTION_NAME)).isNotInstanceOf(Proxy.class);
	}

	// @Test // DATAMONGO-1880
	// public void getDbShouldJustReturnTheDatabase/*No ClientSession binding*/() {
	// assertThat(template.getDb()).isNotInstanceOf(Proxy.class);
	// }

	@Test // DATAMONGO-1880
	public void indexOpsShouldUseProxiedCollection() {

		template.indexOps(COLLECTION_NAME).dropIndex("index-name");

		verify(collection).dropIndex(eq(clientSession), eq("index-name"));
	}

	@Test // DATAMONGO-1880
	public void bulkOpsShouldUseProxiedCollection() {

		BulkOperations bulkOps = template.bulkOps(BulkMode.ORDERED, COLLECTION_NAME);
		bulkOps.insert(new Document());

		bulkOps.execute();

		verify(collection).bulkWrite(eq(clientSession), anyList(), any());

	}

	@Test // DATAMONGO-1880
	public void scriptOpsShouldUseProxiedDatabase() {

		when(database.runCommand(eq(clientSession), any())).thenReturn(new Document("retval", new Object()));
		template.scriptOps().call("W-O-P-R");

		verify(database).runCommand(eq(clientSession), any());
	}

}
