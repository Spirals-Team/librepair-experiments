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
package spoon.pattern.matcher;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import spoon.SpoonException;
import spoon.pattern.ModelValueResolver;
import spoon.pattern.ParameterValueProviderFactory;
import spoon.pattern.UnmodifiableParameterValueProvider;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.EarlyTerminatingScanner;
import spoon.reflect.visitor.chain.CtConsumer;

/**
 * Represents a Match of TemplateMatcher
 */
class MatchingScanner extends EarlyTerminatingScanner<Void> {
	private final ModelValueResolver pattern;
	private ParameterValueProviderFactory parameterValueProviderFactory = UnmodifiableParameterValueProvider.Factory.INSTANCE;
	private CtConsumer<? super Match> matchConsumer;

	MatchingScanner(ModelValueResolver pattern, CtConsumer<? super Match> matchConsumer) {
		this.pattern = pattern;
		this.matchConsumer = matchConsumer;
	}

	@Override
	public void scan(CtRole role, CtElement element) {
		//This is called only for elements which are in single value attribute. Like `CtType#superClass`
		if (searchMatchInList(role, Collections.singletonList(element), false) == 0) {
			super.scan(role, element);
		}
	}

	@Override
	public void scan(CtRole role, Collection<? extends CtElement> elements) {
		if (elements == null) {
			return;
		}
		if (elements instanceof List<?>) {
			@SuppressWarnings("unchecked")
			List<Object> list = (List) elements;
			searchMatchInList(role, list, true);
		} else if (elements instanceof Set<?>) {
			Set<Object> set = (Set) elements;
			searchMatchInSet(role, set);
		} else {
			throw new SpoonException("Unexpected Collection type " + elements.getClass());
		}
	}

	private int searchMatchInList(CtRole role, List<Object> targets, boolean scanChildren) {
		int matchCount = 0;
		if (targets.isEmpty()) {
			//try match for empty collection too
			ListMatch listMatch = new ListMatch(parameterValueProviderFactory.createParameterValueProvider(), targets);
			boolean matched = new TemplatesList(pattern, false).matchesInContext(listMatch);
			if (matched) {
				matchConsumer.accept(new Match(targets, listMatch.getParameters()));
				matchCount++;
			}
		} else {
			//try match for NON empty collection
			int nextToBeChecked = 0;
			while (nextToBeChecked < targets.size()) {
				ListMatch listMatch = new TemplatesList(pattern, false).matchAllWith(targets.subList(nextToBeChecked, targets.size()), parameterValueProviderFactory.createParameterValueProvider());
				if (listMatch.isMatching()) {
					matchCount++;
					//send information about match to client
					matchConsumer.accept(new Match(targets.subList(nextToBeChecked, targets.size() - listMatch.getTargets().size()), listMatch.getParameters()));
					//do not scan children of matched elements. They already matched, so we must not scan them again
					nextToBeChecked = targets.size() - listMatch.getTargets().size();
				} else {
					if (scanChildren) {
						//scan children of each not matched element too
						super.scan(role, targets.get(nextToBeChecked));
					}
					nextToBeChecked++;
					//nextToBeChecked is positioned on next element
				}
			}
		}
		return matchCount;
	}

	private void searchMatchInSet(CtRole role, Set<?> set) {
		// TODO Auto-generated method stub

	}

	@Override
	public void scan(CtRole role, Map<String, ? extends CtElement> elements) {
		// TODO Auto-generated method stub
		super.scan(role, elements);
	}
}
