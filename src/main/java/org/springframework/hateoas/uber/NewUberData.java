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

import static com.fasterxml.jackson.annotation.JsonInclude.*;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.support.PropertyUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Greg Turnquist
 */
@Value
@Wither(AccessLevel.PACKAGE)
@JsonInclude(Include.NON_NULL)
class NewUberData {

	private String id;
	private String name;
	private String label;
	private List<String> rel;
	private String url;
	private UberAction action;
	private boolean transclude;
	private String model;
	private List<String> sending;
	private List<String> accepting;
	private Object value;
	private List<NewUberData> data;

	@JsonCreator
	NewUberData(@JsonProperty("id") String id, @JsonProperty("name") String name,
				@JsonProperty("label") String label, @JsonProperty("rel") List<String> rel,
				@JsonProperty("url") String url, @JsonProperty("action") UberAction action,
				@JsonProperty("transclude") boolean transclude, @JsonProperty("model") String model,
				@JsonProperty("sending") List<String> sending, @JsonProperty("accepting") List<String> accepting,
				@JsonProperty("value") Object value, @JsonProperty("data") List<NewUberData> data) {

		this.id = id;
		this.name = name;
		this.label = label;
		this.rel = rel;
		this.url = url;
		this.action = action;
		this.transclude = transclude;
		this.model = model;
		this.sending = sending;
		this.accepting = accepting;
		this.value = value;
		this.data = data;
	}

	NewUberData() {
		this(null, null, null, null, null, UberAction.READ, false, null, null, null, null, null);
	}

	/**
	 * Don't render if it's {@link UberAction#READ}.
	 */
	public UberAction getAction() {

		if (this.action == UberAction.READ) {
			return null;
		}
		return this.action;
	}

	/*
	 * Don't render if {@literal null}.
	 */
	public List<String> getRel() {

		if (this.rel == null || this.rel.isEmpty()) {
			return null;
		}
		return this.rel;
	}

	/*
	 * Don't render if {@literal null}.
	 */
	public List<NewUberData> getData() {

		if (this.data == null || this.data.isEmpty()) {
			return null;
		}
		return this.data;
	}

	/*
	 * Use a {@link Boolean} to support returning {@literal null}, and if it is {@literal null}, don't render.
	 */
	public Boolean isTemplated() {

		return Optional.ofNullable(this.url)
			.map(s -> s.contains("{?") ? true : null)
			.orElse(null);
	}

	public void setTemplated(boolean __) {
		// Ignore since "templated" is a virtual property
	}

	/*
	 * Use a {@link Boolean} to support returning {@literal null}, and if it is {@literal null}, don't render.
	 */
	public Boolean isTransclude() {
		return this.transclude ? this.transclude : null;
	}

	/**
	 * Fetch all the links found in this {@link NewUberData}.
	 *
	 * @return
	 */
	@JsonIgnore
	public List<Link> getLinks() {

		return Optional.ofNullable(this.rel)
			.map(rels -> rels.stream()
				.map(rel -> new Link(this.url, rel))
				.collect(Collectors.toList()))
			.orElse(Collections.emptyList());
	}

	/**
	 * Convert a {@link List} of {@link Link}s into an {@link UberData}.
	 *
	 * @param links
	 * @return
	 */
	public static List<NewUberData> toUberData(List<Link> links) {

		return UberUtils.urlRelMap(links).entrySet().stream()
			.map(entry -> new NewUberData()
				.withRel(entry.getValue().getRels())
				.withUrl(entry.getKey())
				.withModel(UberUtils.getModelProperty(entry.getKey(), null)))
			.collect(Collectors.toList());

//		return UberUtils.urlRelMap(links).entrySet().stream()
//		    .flatMap(entry -> entry.getValue().getLink().getAffordances().stream()
//				.map(affordance -> new NewUberData()
//					.withRel(entry.getValue().getRels())
//					.withUrl(new UriTemplate(entry.getKey()).expand(Collections.emptyMap()).toString())
////						.withAction(UberAction.forRequestMethod(affordance.getHttpMethod))
//					.withModel(UberUtils.getModelProperty(entry.getKey(), affordance))))
//			.collect(Collectors.toList());
	}

	private static final Set<String> PROPERTIES_TO_IGNORE = new HashSet<>(Arrays.asList("links", "id", "class"));

	/**
	 * Convert a {@link ResourceSupport} into an {@link UberData}.
	 *
	 * @param resource
	 * @return
	 */
	public static NewUberData toUberData(ResourceSupport resource, ObjectMapper mapper) {

		Map<String, Object> resourceProperties = PropertyUtils.findProperties(resource);

		List<NewUberData> links = toUberData(resource.getLinks());

		try {
			if (resourceProperties.entrySet().size() > 0) {
				links.add(new NewUberData()
					.withLabel(resource.getClass().getName())
					.withValue(mapper.writeValueAsString(resourceProperties)));

				return new NewUberData()
					.withData(links);
			} else {
				return new NewUberData()
					.withData(links);
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

		/**
		 * Convert a {@link Resource} into an {@link UberData}.
		 *
		 * @param resource
		 * @param mapper
		 * @return
		 */
	public static NewUberData toUberData(Resource<?> resource, ObjectMapper mapper) {

		try {
			List<NewUberData> data = toUberData(resource.getLinks());

			data.add(new NewUberData()
				.withLabel(resource.getContent().getClass().getName())
				.withValue(mapper.writeValueAsString(resource.getContent())));

			if (data != null) {
				return new NewUberData()
					.withData(data);

			} else {
				return new NewUberData()
					.withData(Collections.singletonList(new NewUberData()
						.withLabel(resource.getContent().getClass().getName())
						.withValue(mapper.writeValueAsString(resource.getContent()))));
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static List<NewUberData> toUberData(Resources<?> resources, ObjectMapper mapper) {

		List<NewUberData> uberLinks = toUberData(resources.getLinks());

		List<NewUberData> uberData = resources.getContent().stream()
			.map(item -> {
				if (item instanceof Resource) {
					return toUberData((Resource<?>) item, mapper);
				} else if (item instanceof ResourceSupport) {
					return toUberData((ResourceSupport) item, mapper);
				} else {
					Resource<?> wrappedResource = new Resource<>(item);
					return toUberData(wrappedResource, mapper);
				}
			})
			.collect(Collectors.toList());

		uberLinks.addAll(uberData);

		return uberLinks;
	}


}
