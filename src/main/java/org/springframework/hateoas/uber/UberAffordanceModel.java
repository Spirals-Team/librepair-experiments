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
package org.springframework.hateoas.uber;

import static org.springframework.hateoas.core.DummyInvocationUtils.*;

import java.util.Collection;
import java.util.Collections;

import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.AffordanceModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponents;

/**
 * @author Greg Turnquist
 */
public class UberAffordanceModel implements AffordanceModel {

	private final Affordance affordance;
	private final MethodInvocation invocationValue;
	private final UriComponents components;

	public UberAffordanceModel(Affordance affordance, MethodInvocation invocationValue, UriComponents components) {

		this.affordance = affordance;
		this.invocationValue = invocationValue;
		this.components = components;
	}

	@Override
	public Collection<MediaType> getMediaTypes() {
		return Collections.singleton(MediaTypes.UBER_JSON);
	}
}
