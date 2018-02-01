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

import spoon.pattern.matcher.ListMatch;
import spoon.pattern.matcher.TemplatesList;

/**
 */
public class SwitchValueResolver implements ValueResolver {

	private class Case {
		/*
		 * is null for the default case
		 */
		SimpleValueResolver vrOfExpression;
		ModelValueResolver statement;
		Case(SimpleValueResolver vrOfExpression, ModelValueResolver statement) {
			super();
			this.vrOfExpression = vrOfExpression;
			this.statement = statement;
		}
	}

	private List<Case> cases = new ArrayList<>();

	public SwitchValueResolver() {
		super();
	}

	/**
	 * Adds another case into this switch statement
	 * @param vrOfExpression if value of this parameter is true then statement has to be used. If vrOfExpression is null, then statement is always used
	 * @param statement optional statement
	 */
	public void addCase(SimpleValueResolver vrOfExpression, ModelValueResolver statement) {
		cases.add(new Case(vrOfExpression, statement));
	}

	@Override
	public <T> void resolveValues(ResultHolder<T> result, ParameterValueProvider parameters) {
		for (Case case1 : cases) {
			if (isCaseSelected(parameters, case1)) {
				case1.statement.resolveValues(result, parameters);
				return;
			}
		}
	}

	private boolean isCaseSelected(ParameterValueProvider parameters, Case case1) {
		if (case1.vrOfExpression == null) {
			return true;
		}
		ResultHolder.Single<Boolean> rh = new ResultHolder.Single<>(Boolean.class);
		case1.vrOfExpression.resolveValues(rh, parameters);
		Boolean value = rh.getResult();
		return value == null ? false : value.booleanValue();
	}

	@Override
	public void forEachParameterInfo(BiConsumer<ParameterInfo, ValueResolver> consumer) {
		for (Case case1 : cases) {
			if (case1.vrOfExpression != null) {
				case1.vrOfExpression.forEachParameterInfo(consumer);
			}
		}
	}

	@Override
	public ListMatch matches(List<? extends Object> targets, ParameterValueProvider parameters, TemplatesList templates) {
		for (Case case1 : cases) {
			{
				ListMatch match = isCaseMatching(targets, parameters, case1, templates);
				if (match.isMatching()) {
					return match;
				}
			}
			if (case1.vrOfExpression != null) {
				//set expression of this `if` to false, because it did not matched
				ParameterValueProvider resultParams = case1.vrOfExpression.matches(parameters, Boolean.FALSE);
				if (resultParams == null) {
					//FALSE doesn't matches with potentially existing value we cannot match this and any other case doesn't matches too
					return ListMatch.EMPTY;
				}
				parameters = resultParams;
			} else {
				//else it is last `else` without expression, and it did not matched -> no match
				return ListMatch.EMPTY;
			}
		}
		//there was no `else`, so try to match context without this switch
		return templates.matchAllWith(targets, parameters);
	}

	/**
	 * try to match body of case, then set expression of case to TRUE and then check that remaining templates are matching.
	 * If all pass, then return match. Else this case is not matching
	 *
	 * @param tmpParams
	 * @param case1
	 * @param templates
	 * @return
	 */
	private ListMatch isCaseMatching(List<? extends Object> targets, ParameterValueProvider parameters, Case case1, TemplatesList templates) {
		ListMatch match = new TemplatesList(case1.statement, false).matchAllWith(targets, parameters);
		if (match.isMatching() == false) {
			return match;
		}
		targets = match.getTargets();
		parameters = match.getParameters();
		//the statement of this case is matching
		if (case1.vrOfExpression != null) {
			//set expression of this `if` to true
			ParameterValueProvider resultParams = case1.vrOfExpression.matches(parameters, Boolean.TRUE);
			if (resultParams == null) {
				//TRUE doesn't matches with potentially existing value we cannot match this
				return ListMatch.EMPTY;
			}
			parameters = resultParams;
		}
		//try matching of remaining templates in this case
		return templates.matchAllWith(targets, parameters);
	}
}
