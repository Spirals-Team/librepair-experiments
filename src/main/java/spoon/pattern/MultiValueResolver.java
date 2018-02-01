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

import java.util.List;
import java.util.function.BiConsumer;

import spoon.pattern.matcher.ListMatch;
import spoon.pattern.matcher.TemplatesList;

import static spoon.pattern.AbstractValueResolver.forEachItem;

/**
 * Pattern node of multiple occurrences of the same model, just with different parameters.
 * Example with three occurrences of model `System.out.println(_x_)`, with parameter `_x_`
 * <code><pre>
 * System.out.println("a")
 * System.out.println("b")
 * System.out.println(getStringOf(p1, p2))
 * </pre></code>
 * where parameter values are _x_ = ["a", "b", getStringOf(p1, p2)]
 */
public class MultiValueResolver implements ValueResolver {

	private ParameterInfo iterableParameter;
	private ModelValueResolver nestedModel;
	private ParameterInfo localParameter;

	public MultiValueResolver() {
		super();
	}

	@Override
	public <T> void resolveValues(ResultHolder<T> result, ParameterValueProvider parameters) {
		Object iterable = parameters.get(iterableParameter.getName());
		forEachItem(iterable, parameterValue -> {
			nestedModel.resolveValues(result, parameters.putIntoCopy(localParameter.getName(), parameterValue));
		});
	}

	@Override
	public ListMatch matches(List<? extends Object> targets, ParameterValueProvider parameters, TemplatesList templates) {
		//create matching context
		ListMatch match = new ListMatch(parameters, targets);
		int countOfMatches = 0;
		//TODO iterableParameter.getMatchingStrategy()
		while (countOfMatches < iterableParameter.getMaxOccurences()) {
			//try to match nestedModel
			ListMatch match2 = new TemplatesList(nestedModel, false).matchAllWith(match.getTargets(), match.getParameters());
			if (match2.isMatching() == false) {
				//nested model did not matched. Leave the while
				break;
			}
			//it matched. Use that matching context
			match = match2;
			//increment count of matches of nestedModel and try next match of nested model
			countOfMatches++;
		}
		if (countOfMatches < iterableParameter.getMinOccurences()) {
			//there was not enough matches -> no match at all
			return ListMatch.EMPTY;
		}
		return templates.matchAllWith(match.getTargets(), match.getParameters());
	}

	@Override
	public void forEachParameterInfo(BiConsumer<ParameterInfo, ValueResolver> consumer) {
		consumer.accept(iterableParameter, this);
		consumer.accept(localParameter, this);
	}

	public void setNestedModel(ModelValueResolver valueResolver) {
		this.nestedModel = valueResolver;
	}

	public void setIterableParameter(ParameterInfo substRequestOfIterable) {
		this.iterableParameter = substRequestOfIterable;
	}

	public void setLocalParameter(ParameterInfo parameterInfo) {
		this.localParameter = parameterInfo;
	}
}
