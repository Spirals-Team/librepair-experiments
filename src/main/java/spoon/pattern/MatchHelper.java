/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import spoon.SpoonException;
import spoon.pattern.matcher.Matchers;
import spoon.pattern.matcher.ChainOfMatchersImpl;
import spoon.pattern.matcher.MapEntryMatcher;
import spoon.reflect.meta.ContainerKind;

/**
 * Container for single or multiple values of required type
 */
public abstract class MatchHelper {
	public static Matchers createChainOfMatchers(SubstitutionRequestProvider substitutionRequestProvider, ContainerKind containerKind, Object templates, boolean matchAllTargets) {
		switch (containerKind) {
		case LIST:
			return createChainOfMatchers(substitutionRequestProvider, (List) templates, matchAllTargets);
		case SET:
			return createChainOfMatchers(substitutionRequestProvider, (Set) templates, matchAllTargets);
		case MAP:
			return createChainOfMatchers(substitutionRequestProvider, (Map) templates, matchAllTargets);
		case SINGLE:
			return createChainOfMatchers(substitutionRequestProvider, templates, matchAllTargets);
		}
		throw new SpoonException("Unexpected RoleHandler containerKind: " + containerKind);
	}

	public static Matchers createChainOfMatchers(SubstitutionRequestProvider substitutionRequestProvider, List<?> matchers, boolean matchAllTargets) {
		return ChainOfMatchersImpl.create(matchers.stream().map(i -> substitutionRequestProvider.getTemplateValueResolver(i)).collect(Collectors.toList()), matchAllTargets);
	}

	public static Matchers createChainOfMatchers(SubstitutionRequestProvider substitutionRequestProvider, Set<?> templates, boolean matchAllTargets) {
		//collect plain template nodes without any substitution request as List, because Spoon Sets have predictable order.
		List<Matcher> constantMatchers = new ArrayList<>(templates.size());
		//collect template nodes with a substitution request
		List<Matcher> variableMatchers = new ArrayList<>();
		for (Object template : templates) {
			Matcher matcher = substitutionRequestProvider.getTemplateValueResolver(template);
			if (matcher instanceof NodeMatcher) {
				constantMatchers.add(matcher);
			} else {
				variableMatchers.add(matcher);
			}
		}
		//first match the Set with constant matchers and then with variable matchers
		constantMatchers.addAll(variableMatchers);
		return ChainOfMatchersImpl.create(constantMatchers, matchAllTargets);
	}

	public static Matchers createChainOfMatchers(SubstitutionRequestProvider substitutionRequestProvider, Map<String, ?> map, boolean matchAllTargets) {
		//collect Entries with constant matcher keys
		List<MapEntryMatcher> constantMatchers = new ArrayList<>(map.size());
		//collect Entries with variable matcher keys
		List<MapEntryMatcher> variableMatchers = new ArrayList<>();
		Matchers last = null;
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			MapEntryMatcher mem = new MapEntryMatcher(
					substitutionRequestProvider.getTemplateValueResolver(entry.getKey()),
					substitutionRequestProvider.getTemplateValueResolver(entry.getValue()));
			if (mem.getKey() == entry.getKey()) {
				constantMatchers.add(mem);
			} else {
				variableMatchers.add(mem);
			}
		}
		//first match the Map.Entries with constant matchers and then with variable matchers
		constantMatchers.addAll(variableMatchers);
		return ChainOfMatchersImpl.create(constantMatchers, matchAllTargets);
	}
	public static Matchers createChainOfMatchers(SubstitutionRequestProvider substitutionRequestProvider, Object template, boolean matchAllTargets) {
		return ChainOfMatchersImpl.create(matchAllTargets, substitutionRequestProvider.getTemplateValueResolver(template));
	}
}
