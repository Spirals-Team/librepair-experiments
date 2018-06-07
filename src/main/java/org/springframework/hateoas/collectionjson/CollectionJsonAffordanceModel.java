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
package org.springframework.hateoas.collectionjson;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.GenericAffordanceModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.QueryParameter;
import org.springframework.hateoas.support.PropertyUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 * @author Greg Turnquist
 */
public class CollectionJsonAffordanceModel extends GenericAffordanceModel {

	private final @Getter Collection<MediaType> mediaTypes = Collections.singleton(MediaTypes.COLLECTION_JSON);
	private final @Getter List<CollectionJsonData> inputProperties;
	private final @Getter List<CollectionJsonData> queryProperties;
	
	public CollectionJsonAffordanceModel(Affordance affordance, Link link) {

		super(affordance, link);

		this.inputProperties = determineInputs();
		this.queryProperties = determineQueryProperties();
	}

	/**
	 * Look at the inputs for a Spring MVC controller method to decide the {@link Affordance}'s properties.
	 * Then transform them into a list of {@link CollectionJsonData} objects.
	 */
	private List<CollectionJsonData> determineInputs() {

		if (Arrays.asList(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH).contains(getHttpMethod())) {

			return getInputType()
				.map(PropertyUtils::findPropertyNames)
				.orElse(Collections.emptyList())
				.stream()
				.map(propertyName -> new CollectionJsonData()
					.withName(propertyName)
					.withValue(""))
				.collect(Collectors.toList());

		} else {
			return Collections.emptyList();

		}
	}

	/**
	 * Transform a list of {@link QueryParameter}s into a list of {@link CollectionJsonData} objects.
	 *
	 * @return
	 */
	private List<CollectionJsonData> determineQueryProperties() {

		if (getHttpMethod().equals(HttpMethod.GET)) {

			return getQueryMethodParameters().stream()
				.map(queryProperty -> new CollectionJsonData()
					.withName(queryProperty.getName())
					.withValue(""))
				.collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}

	public String getRel() {

		if (getHttpMethod().equals(HttpMethod.GET)) {
			return getName();
		} else {
			return "";
		}
	}

	public String getURI() {

		if (getHttpMethod().equals(HttpMethod.GET)) {
			return getLink().expand().getHref();
		} else {
			return "";
		}
	}

}
