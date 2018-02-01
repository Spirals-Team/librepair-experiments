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

import java.util.List;

import spoon.SpoonException;
import spoon.pattern.AbstractNodeSubstitutionRequest;
import spoon.pattern.ModelValueResolver;
import spoon.pattern.NodeAttributesSubstitionRequest;
import spoon.pattern.NodeSubstitutionRequest;
import spoon.pattern.ParameterValueProvider;
import spoon.pattern.ValueResolver;
import spoon.reflect.declaration.CtElement;

/**
 * List of Template nodes.
 */
public class TemplatesList {
	private final ModelValueResolver pattern;
	private final List<? extends Object> templates;
	private final int templateIdx;
	private final boolean matchAllTargets;

	public TemplatesList(ModelValueResolver pattern, boolean matchAllTargets) {
		this(pattern, pattern.getModel(), matchAllTargets);
	}

	public TemplatesList(ModelValueResolver pattern, List<? extends Object> templates, boolean matchAllTargets) {
		this(pattern, templates, 0, matchAllTargets);
	}

	private TemplatesList(ModelValueResolver pattern, List<? extends Object> templates, int templateIdx, boolean matchAllTargets) {
		super();
		this.pattern = pattern;
		this.templates = templates;
		this.templateIdx = templateIdx;
		this.matchAllTargets = matchAllTargets;
	}

	private TemplatesList nextTemplates() {
		return new TemplatesList(pattern, templates, templateIdx + 1, matchAllTargets);
	}

	public boolean matchesInContext(ListMatch parameters) {
		return matchAllWith(parameters.getTargets(), parameters.getParameters()).isMatching();
	}
	/**
	 * Matches all nodes of this {@link TemplatesList} against `targets` nodes with respect to predefined or already matched parameters
	 * @param targets the list of to be matched target nodes
	 * @param parameters the matching parameters TODO assure that params are never modified by this call. And count with that in client's code
	 * @return {@link ListMatch}, which contains remaining (not matched) targets and new {@link ParameterValueProvider} with old + newly matched values
	 */
	public ListMatch matchAllWith(List<? extends Object> targets, ParameterValueProvider parameters) {
		if (templateIdx >= templates.size()) {
			//there are no template items
			if (matchAllTargets) {
				//we are in mode of matching of all targets. So it matches only when there is no remaining target element
				return targets.isEmpty() ? new ListMatch(parameters, targets) : ListMatch.EMPTY;
			}
			//all NodeMatchers matched. There might remain some unmatched target! Check parameters.hasNextTarget()
			return new ListMatch(parameters, targets);
		}
		final Object template = templates.get(templateIdx);
		AbstractNodeSubstitutionRequest substReq = pattern.getSubstitutionRequest(template);
		if (substReq instanceof NodeSubstitutionRequest) {
			//Current template is node substitution. It can match 0, 1 or more targets
			ValueResolver valueResolver = ((NodeSubstitutionRequest) substReq).getValueResolver();
			return valueResolver.matches(targets, parameters, nextTemplates());
		}
		SingleValueMatcher valueMatcher;
		if (substReq instanceof NodeAttributesSubstitionRequest) {
			valueMatcher = new SingleNodeSubstitutionRequestMatcherImpl((NodeAttributesSubstitionRequest) substReq);
		} else if (substReq == null) {
			//we need 1:1 matching of CtElement or CtElement attribute value
			if (template instanceof CtElement) {
				valueMatcher = new SingleNodeMatcherImpl(pattern, (CtElement) template);
			} else {
				//case of CtElement attribute value matching
				valueMatcher = new SingleValueMatcherImpl(template);
			}
		} else {
			throw new SpoonException("Unexpected implementation of AbstractNodeSubstitutionRequest");
		}
		if (targets.isEmpty()) {
			return ListMatch.EMPTY;
		}
		parameters = valueMatcher.matches(parameters, targets.get(0));
		if (parameters == null) {
			return ListMatch.EMPTY;
		}
		return nextTemplates().matchAllWith(targets.subList(1, targets.size()), parameters);
	}
}
