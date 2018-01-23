/*
 * Copyright 2017 the original author or authors.
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
package org.springframework.hateoas.uber;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.springframework.hateoas.support.MappingUtils.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author Greg Turnquist
 */
public class UberSerializationTest {

	ObjectMapper objectMapper;

	@Before
	public void setUp() {

		this.objectMapper = new ObjectMapper();
		this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}

	@Test
	public void deserializingUberDocumentShouldWork() throws IOException {

		NewUberDocument uberDocument = this.objectMapper.readValue(
			read(new ClassPathResource("reference-1.json", getClass())), NewUberDocument.class);

		assertThat(uberDocument.getUber().getLinks().size(), is(8));

		assertThat(uberDocument.getUber().getLinks().get(0), is(new Link("http://example.org/", "self")));
		assertThat(uberDocument.getUber().getLinks().get(1), is(new Link("http://example.org/list/", "collection")));
		assertThat(uberDocument.getUber().getLinks().get(2), is(new Link("http://example.org/search{?title}", "search")));
		assertThat(uberDocument.getUber().getLinks().get(3), is(new Link("http://example.org/search{?title}", "collection")));
		assertThat(uberDocument.getUber().getLinks().get(4), is(new Link("http://example.org/list/1", "item")));
		assertThat(uberDocument.getUber().getLinks().get(5), is(new Link("http://example.org/list/1", "http://example.org/rels/todo")));
		assertThat(uberDocument.getUber().getLinks().get(6), is(new Link("http://example.org/list/2", "item")));
		assertThat(uberDocument.getUber().getLinks().get(7), is(new Link("http://example.org/list/2", "http://example.org/rels/todo")));

		assertThat(uberDocument.getUber().getData().size(), is(5));

		assertThat(uberDocument.getUber().getData().get(0), is(new NewUberData()
			.withRel(Arrays.asList("self"))
			.withUrl("http://example.org/")));

		assertThat(uberDocument.getUber().getData().get(1), is(new NewUberData()
			.withName("list")
			.withLabel("ToDo List")
			.withRel(Arrays.asList("collection"))
			.withUrl("http://example.org/list/")));

		assertThat(uberDocument.getUber().getData().get(2), is(new NewUberData()
			.withName("search")
			.withLabel("Search")
			.withRel(Arrays.asList("search", "collection"))
			.withUrl("http://example.org/search{?title}")));

		assertThat(uberDocument.getUber().getData().get(3), is(new NewUberData()
			.withName("todo")
			.withRel(Arrays.asList("item", "http://example.org/rels/todo"))
			.withUrl("http://example.org/list/1")
			.withData(Arrays.asList(new NewUberData()
				.withName("title")
				.withLabel("Title")
				.withValue("Clean house")
			, new NewUberData()
				.withName("dueDate")
				.withLabel("Date Due")
				.withValue("2014-05-01")))));

		assertThat(uberDocument.getUber().getData().get(4), is(new NewUberData()
			.withName("todo")
			.withRel(Arrays.asList("item", "http://example.org/rels/todo"))
			.withUrl("http://example.org/list/2")
				.withData(Arrays.asList(new NewUberData()
				.withName("title")
				.withLabel("Title")
				.withValue("Paint the fence"),
				new NewUberData()
				.withName("dueDate")
				.withLabel("Date Due")
				.withValue("2014-06-01")))));
	}

	@Test
	public void serializingUberDocumentShouldWork() throws IOException {

		NewUber uberContainer = new NewUber()
			.withVersion("1.0")
			.withData(Arrays.asList(
				new NewUberData()
					.withRel(Arrays.asList("self"))
					.withUrl("http://example.org/")
				, new NewUberData()
					.withName("list")
					.withLabel("ToDo List")
					.withRel(Arrays.asList("collection"))
					.withUrl("http://example.org/list/")
				, new NewUberData()
					.withName("search")
					.withLabel("Search")
					.withRel(Arrays.asList("search", "collection"))
					.withUrl("http://example.org/search{?title}")
				, new NewUberData()
					.withName("todo")
					.withRel(Arrays.asList("item", "http://example.org/rels/todo"))
					.withUrl("http://example.org/list/1")
					.withData(Arrays.asList(
						new NewUberData()
							.withName("title")
							.withLabel("Title")
							.withValue("Clean house")
							,
						new NewUberData()
							.withName("dueDate")
							.withLabel("Date Due")
							.withValue("2014-05-01")))
				, new NewUberData()
					.withName("todo")
					.withRel(Arrays.asList("item", "http://example.org/rels/todo"))
					.withUrl("http://example.org/list/2")
					.withData(Arrays.asList(new NewUberData()
						.withName("title")
						.withLabel("Title")
						.withValue("Paint the fence")
					, new NewUberData()
						.withName("dueDate")
						.withLabel("Date Due")
						.withValue("2014-06-01")))));


		String actual = this.objectMapper.writeValueAsString(new NewUberDocument(uberContainer));
		assertThat(actual, is(read(new ClassPathResource("nicely-formatted-reference.json", getClass()))));
	}

	@Test
	public void errorsShouldDeserializeProperly() throws IOException {

		NewUberDocument uberDocument = this.objectMapper.readValue(
			read(new ClassPathResource("reference-2.json", getClass())), NewUberDocument.class);

		assertThat(uberDocument.getUber().getError(), is(notNullValue()));

		assertThat(uberDocument.getUber().getError().getData().size(), is(4));

		assertThat(uberDocument.getUber().getError().getData().get(0), is(new NewUberData()
			.withName("type")
			.withRel(Arrays.asList("https://example.com/rels/http-problem#type"))
			.withValue("out-of-credit")));
		assertThat(uberDocument.getUber().getError().getData().get(1), is(new NewUberData()
			.withName("title")
			.withRel(Arrays.asList("https://example.com/rels/http-problem#title"))
			.withValue("You do not have enough credit")));
		assertThat(uberDocument.getUber().getError().getData().get(2), is(new NewUberData()
			.withName("detail")
			.withRel(Arrays.asList("https://example.com/rels/http-problem#detail"))
			.withValue("Your balance is 30, but the cost is 50.")));
		assertThat(uberDocument.getUber().getError().getData().get(3), is(new NewUberData()
			.withName("balance")
			.withRel(Arrays.asList("https://example.com/rels/http-problem#balance"))
			.withValue("30")));
	}

	@Test
	public void anotherSpecUberDocumentToTest() throws IOException {

		NewUberDocument uberDocument = this.objectMapper.readValue(
			read(new ClassPathResource("reference-3.json", getClass())), NewUberDocument.class);

		assertThat(uberDocument.getUber().getLinks().size(), is(6));
		assertThat(uberDocument.getUber().getLinks().get(0), is(new Link("http://example.org/", "self")));
		assertThat(uberDocument.getUber().getLinks().get(1), is(new Link("http://example.org/profiles/people-and-places", "profile")));
		assertThat(uberDocument.getUber().getLinks().get(2), is(new Link("http://example.org/people/", "collection")));
		assertThat(uberDocument.getUber().getLinks().get(3), is(new Link("http://example.org/people/", "http://example.org/rels/people")));
		assertThat(uberDocument.getUber().getLinks().get(4), is(new Link("http://example.org/places/", "collection")));
		assertThat(uberDocument.getUber().getLinks().get(5), is(new Link("http://example.org/places/", "http://example.org/rels/places")));

		assertThat(uberDocument.getUber().getData().get(2).getId(), is("people"));
		assertThat(uberDocument.getUber().getData().get(2).getData().get(0).getAction(), is(UberAction.APPEND));
		assertThat(uberDocument.getUber().getData().get(2).getData().get(2).getData().get(3).getName(), is("avatarUrl"));
		assertThat(uberDocument.getUber().getData().get(2).getData().get(2).getData().get(3).isTransclude(), is(true));
		assertThat(uberDocument.getUber().getData().get(2).getData().get(2).getData().get(3).getUrl(), is("http://example.org/avatars/1"));
		assertThat(uberDocument.getUber().getData().get(2).getData().get(2).getData().get(3).getValue().toString(), is("User Photo"));
		assertThat(uberDocument.getUber().getData().get(2).getData().get(2).getData().get(3).getAccepting(), hasItems("image/*"));
	}
}
