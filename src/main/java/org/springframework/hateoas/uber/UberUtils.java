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

package org.springframework.hateoas.uber;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class UberUtils {

	/**
	 * Transform an object into a {@link UberContainer}.
	 * 
	 * @param object
	 * @param objectMapper
	 * @return
	 */
	public static UberDocument toUber(final Object object, final ObjectMapper objectMapper) {

		if (object == null) {
			return null;
		}

		if (object instanceof UberDocument) {
			return (UberDocument) object;
		}

		if (object instanceof Iterable) {

		} else if (object instanceof Map) {

		}

		throw new IllegalArgumentException("Don't know how to handle type : " + object.getClass());
	}

	static String getModelProperty(String href, Affordance affordance) {

		if (affordance == null) {
			return null;
		}

		UriTemplate uriTemplate = new UriTemplate(href);
		String model;

		switch (affordance.getHttpMethod()) {
			case GET:
			case DELETE: {
				model = buildModel(uriTemplate.getVariableNames(), "{?", ",", "}", "%s");
				break;
			}
			case POST:
			case PUT:
			case PATCH: {
				model = buildModel(uriTemplate.getVariableNames(), "", "&", "", "%s={%s}");
				break;
			}
			default:
				model = null;
		}
		return StringUtils.isEmpty(model) ? null : model;
	}

	private static String buildModel(List<String> variableNames, String prefix, String separator, String suffix,
			String parameterTemplate) {

		if (variableNames.isEmpty()) {
			return "";
		}

		List<String> transformedVariableNames = new ArrayList<String>();

		for (String variable : variableNames) {
			transformedVariableNames.add(String.format(parameterTemplate, variable, variable));
		}

		return prefix + StringUtils.collectionToDelimitedString(transformedVariableNames, separator) + suffix;
	}

	/**
	 * Turn a {@list List} of {@link Link}s into a {@link Map}, where you can see ALL the rels of a given
	 * link.
	 *
	 * @param links
	 * @return a map with links mapping onto a {@link List} of rels
	 */
	public static Map<String, LinkAndRels> urlRelMap(List<Link> links) {

		Map<String, LinkAndRels> urlRelMap = new LinkedHashMap<String, LinkAndRels>();

		for (Link link : links) {
			if (!urlRelMap.containsKey(link.getHref())) {
				urlRelMap.put(link.getHref(), new LinkAndRels());
			}
			LinkAndRels linkAndRels = urlRelMap.get(link.getHref());
			linkAndRels.setLink(link);
			linkAndRels.getRels().add(link.getRel());
		}

		return urlRelMap;
	}

	/**
	 * Holds both a {@link Link} and related {@literal rels}.
	 * 
	 */
	@Data
	public static class LinkAndRels {

		 private Link link;
		 private List<String> rels = new ArrayList<>();
	}
}
