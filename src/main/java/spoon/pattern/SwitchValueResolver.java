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
import java.util.function.BiConsumer;

import spoon.pattern.matcher.Matchers;
import spoon.pattern.matcher.ChainOfMatchersImpl;
import spoon.pattern.matcher.TobeMatched;

/**
 * Contains list of cases - only one of the cases (or none) can match
 */
public class SwitchValueResolver implements ValueResolver {

	private class Case {
		/*
		 * is null for the default case
		 */
		SimpleValueResolver vrOfExpression;
		ValueResolver statement;
		Case(SimpleValueResolver vrOfExpression, ValueResolver statement) {
			super();
			this.vrOfExpression = vrOfExpression;
			this.statement = statement;
		}
	}

	private final SubstitutionRequestProvider substitutionRequestProvider;
	private List<Case> cases = new ArrayList<>();

	public SwitchValueResolver(SubstitutionRequestProvider substitutionRequestProvider) {
		super();
		this.substitutionRequestProvider = substitutionRequestProvider;
	}

	/**
	 * Adds another case into this switch statement
	 * @param vrOfExpression if value of this parameter is true then statement has to be used. If vrOfExpression is null, then statement is always used
	 * @param statement optional statement
	 */
	public void addCase(SimpleValueResolver vrOfExpression, ValueResolver statement) {
		cases.add(new Case(vrOfExpression, statement));
	}

	@Override
	public <T> void generateTargets(ResultHolder<T> result, ParameterValueProvider parameters) {
		for (Case case1 : cases) {
			if (isCaseSelected(parameters, case1)) {
				case1.statement.generateTargets(result, parameters);
				return;
			}
		}
	}

	private boolean isCaseSelected(ParameterValueProvider parameters, Case case1) {
		if (case1.vrOfExpression == null) {
			return true;
		}
		Boolean value = case1.vrOfExpression.generateTarget(parameters, Boolean.class);
		return value == null ? false : value.booleanValue();
	}

	@Override
	public void forEachParameterInfo(BiConsumer<ParameterInfo, Parameterized> consumer) {
		for (Case case1 : cases) {
			if (case1.vrOfExpression != null) {
				case1.vrOfExpression.forEachParameterInfo(consumer);
			}
		}
	}

	@Override
	public TobeMatched matchTargets(TobeMatched targets, Matchers nextMatchers) {
		boolean hasDefaultCase = false;
		//detect which case is matching - if any
		for (Case case1 : cases) {
			TobeMatched match = ChainOfMatchersImpl.create(nextMatchers, new ExpressionParameterValuesSetter(case1), case1.statement).matchAllWith(targets);
			if (match != null) {
				return match;
			}
			if (case1.vrOfExpression == null) {
				hasDefaultCase = true;
			}
		}
		//no case matched
		if (hasDefaultCase) {
			//nothing matched and even the default case didn't matched, so whole switch cannot match
			return null;
		}
		/*
		 * else this switch is optional and matches 0 targets - OK, it is match too.
		 * 1) set all expressions to false and match nextMatchers
		 */
		return ChainOfMatchersImpl.create(nextMatchers, new ExpressionParameterValuesSetter(null)).matchAllWith(targets);
	}

	private class ExpressionParameterValuesSetter implements Matcher {
		private final Case matchingCase;
		ExpressionParameterValuesSetter(Case matchingCase) {
			super();
			this.matchingCase = matchingCase;
		}
		@Override
		public TobeMatched matchTargets(TobeMatched targets, Matchers nextMatchers) {
			ParameterValueProvider parameters = targets.getParameters();
			//set all switch parameter values following match case. Even no matching case is OK - everything is false then
			for (Case case1 : cases) {
				if (case1.vrOfExpression != null) {
					//set expression of this `if` depending on if this case matched or not
					parameters = case1.vrOfExpression.matchTarget(case1 == matchingCase, parameters);
					if (parameters == null) {
						//this value doesn't matches we cannot match this case
						return null;
					}
				}
			}
			return nextMatchers.matchAllWith(targets.copyAndSetParams(parameters));
		}
		@Override
		public void forEachParameterInfo(BiConsumer<ParameterInfo, Parameterized> consumer) {
			for (Case case1 : cases) {
				if (case1.vrOfExpression != null) {
					case1.vrOfExpression.forEachParameterInfo(consumer);
				}
			}
		}
	}
}
