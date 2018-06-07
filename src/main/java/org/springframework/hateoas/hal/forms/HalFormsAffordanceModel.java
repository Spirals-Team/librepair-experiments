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
package org.springframework.hateoas.hal.forms;

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
import org.springframework.hateoas.support.PropertyUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 * {@link GenericAffordanceModel} for a HAL-FORMS {@link org.springframework.http.MediaType}.
 * 
 * @author Greg Turnquist
 */
public class HalFormsAffordanceModel extends GenericAffordanceModel {

	private final @Getter Collection<MediaType> mediaTypes = Collections.singleton(MediaTypes.HAL_FORMS_JSON);
	private final @Getter List<HalFormsProperty> inputProperties;
	private final boolean required;

	public HalFormsAffordanceModel(Affordance affordance, Link link) {

		super(affordance, link);
		
		this.inputProperties = determineAffordanceInputs(affordance);
		this.required = Arrays.asList(HttpMethod.POST, HttpMethod.PUT).contains(affordance.getHttpMethod());
	}

	/**
	 * Look at the inputs for a Spring MVC controller method to decide the {@link Affordance}'s properties.
	 * 
	 * @param affordance
	 */
	private List<HalFormsProperty> determineAffordanceInputs(Affordance affordance) {

		if (Arrays.asList(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH).contains(affordance.getHttpMethod())) {
			
			return affordance.getInputType()
				.map(PropertyUtils::findPropertyNames)
				.orElse(Collections.emptyList()).stream()
				.map(propertyName -> HalFormsProperty.named(propertyName).withRequired(required))
				.collect(Collectors.toList());
			
		} else {
			return Collections.emptyList();
		}
	}

	public String getURI() {
		return getLink().expand().getHref();
	}
}
