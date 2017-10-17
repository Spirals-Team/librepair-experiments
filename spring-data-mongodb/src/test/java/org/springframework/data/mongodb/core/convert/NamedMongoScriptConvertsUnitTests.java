/*
 * Copyright 2014-2017 the original author or authors.
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
package org.springframework.data.mongodb.core.convert;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.bson.Document;
import org.bson.types.Code;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoConverters.DocumentToNamedMongoScriptConverter;
import org.springframework.data.mongodb.core.convert.MongoConverters.NamedMongoScriptToDocumentConverter;
import org.springframework.data.mongodb.core.convert.NamedMongoScriptConvertsUnitTests.DocumentToNamedMongoScriptConverterUnitTests;
import org.springframework.data.mongodb.core.convert.NamedMongoScriptConvertsUnitTests.NamedMongoScriptToDocumentConverterUnitTests;
import org.springframework.data.mongodb.core.script.NamedMongoScript;

/**
 * Unit tests for {@link Converter} implementations for {@link NamedMongoScript}.
 * 
 * @author Christoph Strobl
 * @author Oliver Gierke
 * @since 1.7
 */
@RunWith(Suite.class)
@SuiteClasses({ NamedMongoScriptToDocumentConverterUnitTests.class, DocumentToNamedMongoScriptConverterUnitTests.class })
public class NamedMongoScriptConvertsUnitTests {

	static final String FUNCTION_NAME = "echo";
	static final String JS_FUNCTION = "function(x) { return x; }";
	static final NamedMongoScript ECHO_SCRIPT = new NamedMongoScript(FUNCTION_NAME, JS_FUNCTION);
	static final Document FUNCTION = new org.bson.Document().append("_id", FUNCTION_NAME).append("value",
			new Code(JS_FUNCTION));

	/**
	 * @author Christoph Strobl
	 */
	public static class NamedMongoScriptToDocumentConverterUnitTests {

		NamedMongoScriptToDocumentConverter converter = NamedMongoScriptToDocumentConverter.INSTANCE;

		@Test // DATAMONGO-479
		public void convertShouldReturnEmptyDocWhenScriptIsNull() {
			assertThat(converter.convert(null), is((Document) new Document()));
		}

		@Test // DATAMONGO-479
		public void convertShouldConvertScriptNameCorreclty() {

			Document document = converter.convert(ECHO_SCRIPT);

			Object id = document.get("_id");
			assertThat(id, is(instanceOf(String.class)));
			assertThat(id, is((Object) FUNCTION_NAME));
		}

		@Test // DATAMONGO-479
		public void convertShouldConvertScriptCodeCorreclty() {

			Document document = converter.convert(ECHO_SCRIPT);

			Object code = document.get("value");
			assertThat(code, is(instanceOf(Code.class)));
			assertThat(code, is((Object) new Code(JS_FUNCTION)));
		}
	}

	/**
	 * @author Christoph Strobl
	 */
	public static class DocumentToNamedMongoScriptConverterUnitTests {

		DocumentToNamedMongoScriptConverter converter = DocumentToNamedMongoScriptConverter.INSTANCE;

		@Test // DATAMONGO-479
		public void convertShouldReturnNullIfSourceIsNull() {
			assertThat(converter.convert(null), is(nullValue()));
		}

		@Test // DATAMONGO-479
		public void convertShouldConvertIdCorreclty() {

			NamedMongoScript script = converter.convert(FUNCTION);

			assertThat(script.getName(), is(FUNCTION_NAME));
		}

		@Test // DATAMONGO-479
		public void convertShouldConvertScriptValueCorreclty() {

			NamedMongoScript script = converter.convert(FUNCTION);

			assertThat(script.getCode(), is(notNullValue()));
			assertThat(script.getCode(), is(JS_FUNCTION));
		}
	}

}
