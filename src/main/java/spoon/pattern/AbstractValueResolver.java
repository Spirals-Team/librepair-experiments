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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import spoon.Launcher;
import spoon.SpoonException;
import spoon.pattern.matcher.ListMatch;
import spoon.pattern.matcher.PatternMatcher;
import spoon.pattern.matcher.TemplatesList;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.reference.CtTypeReference;

/**
 * Delivers to be substituted value
 * Matches value
 */
abstract class AbstractValueResolver implements SimpleValueResolver {

	private Map<ParameterInfo, List<ItemAccessor>> itemAccessorsByParameter;

	protected AbstractValueResolver() {
	}

	protected <T> void getValueAs(ResultHolder<T> result, ParameterValueProvider parameters, ParameterInfo parameterInfo) {
		//get raw parameter value
		Object rawValue = parameters.get(parameterInfo.getName());
		if (parameterInfo.isMultiple() && rawValue instanceof CtBlock<?>)  {
			/*
			 * The CtBlock of this parameter is just implicit container of list of statements.
			 * Convert it to list here, so further code see list and not the single CtBlock element
			 */
			rawValue = ((CtBlock<?>) rawValue).getStatements();
		}
		if (itemAccessorsByParameter != null) {
			List<ItemAccessor> itemAccessors = itemAccessorsByParameter.get(parameterInfo);
			if (itemAccessors != null) {
				for (ItemAccessor itemAccessor : itemAccessors) {
					rawValue = itemAccessor.getItem(rawValue);
				}
			}
		}
		convertValue(result, parameterInfo, rawValue);
	}

	protected ParameterValueProvider addValueAs(ParameterValueProvider parameters, ParameterInfo parameterInfo, Object value) {
		Class<?> requiredType = parameterInfo.getParameterValueType();
		if (parameterInfo.isMultiple()) {
			//multiple value storage
			Object convertedValue = value;
			if (value != null && requiredType != null && requiredType.isInstance(value) == false) {
				//the value needs a conversion
				try {
					ResultHolder.Multiple<?> result = new ResultHolder.Multiple<>(requiredType);
					convertValue(result, parameterInfo, value);
					convertedValue = result.getResult();
				} catch (SpoonException e) {
					//
					return null;
				}
			}
			List<ItemAccessor> itemAccessors = null;
			if (itemAccessorsByParameter != null) {
				itemAccessors = itemAccessorsByParameter.get(parameterInfo);
			}
			Object existingValue = parameters.get(parameterInfo.getName());
			Object newValue = parameterInfo.addValue(existingValue, itemAccessors, convertedValue);
			if (newValue != existingValue) {
				parameters = parameters.putIntoCopy(parameterInfo.getName(), newValue);
			}
			return parameters;
		} else {
			//single value storage
			Object convertedValue = value;
			if (value != null && requiredType != null && requiredType.isInstance(value) == false) {
				//the value needs a conversion
				try {
					ResultHolder.Single<?> result = new ResultHolder.Single<>(requiredType);
					convertValue(result, parameterInfo, value);
					convertedValue = result.getResult();
				} catch (SpoonException e) {
					//
					return null;
				}
			}
			Object existingValue = parameters.get(parameterInfo.getName());
			if (existingValue != null) {
				if (existingValue.equals(convertedValue)) {
					//the value is already stored there
					return parameters;
				}
				if (convertedValue != null && existingValue.getClass().equals(value.getClass())) {
					if (value instanceof CtTypeReference) {
						if (((CtTypeReference<?>) value).getTypeErasure().equals(((CtTypeReference<?>) existingValue).getTypeErasure())) {
							//accept type references with different erasure
							return parameters;
						}
					}
				}
				// another value would be inserted. TemplateMatcher does not support
				// matching of different values for the same template parameter
				Launcher.LOGGER.debug("incongruent match on parameter " + parameterInfo.getName() + " with value " + value);
				return null;
			}
			if (convertedValue == null && parameterInfo.getMinOccurences() > 0) {
				//the value is not optional. Null doesn't matches
				return null;
			}
			return parameters.putIntoCopy(parameterInfo.getName(), convertedValue);
		}
	}

	protected <T> void convertValue(ResultHolder<T> result, ParameterInfo parameterInfo, Object rawValue) {
		ValueConvertor valueConvertor = parameterInfo.getValueConvertor();
		//convert raw parameter value to expected type
		if (result.isMultiple()) {
			forEachItem(rawValue, singleValue -> {
				T convertedValue = (T) valueConvertor.getValueAs(singleValue, result.getRequiredClass());
				if (convertedValue != null) {
					result.addResult(convertedValue);
				}
			});
		} else {
			//single value converts arrays in rawValues into single value
			result.addResult((T) valueConvertor.getValueAs(rawValue, result.getRequiredClass()));
		}
	}

	/**
	 * calls consumer.accept(Object) once for each item of the `multipleValues` collection or array.
	 * If it is not a collection or array then it calls consumer.accept(Object) once with `multipleValues`
	 * If `multipleValues` is null then consumer.accept(Object) is not called
	 * @param multipleValues to be iterated potential collection of items
	 * @param consumer the receiver of items
	 */
	@SuppressWarnings("unchecked")
	static void forEachItem(Object multipleValues, Consumer<Object> consumer) {
		if (multipleValues instanceof CtStatementList) {
			//CtStatementList extends Iterable, but we want to handle it as one node.
			consumer.accept(multipleValues);
			return;
		}
		if (multipleValues instanceof Iterable) {
			for (Object item : (Iterable<Object>) multipleValues) {
				consumer.accept(item);
			}
			return;
		}
		if (multipleValues instanceof Object[]) {
			for (Object item : (Object[]) multipleValues) {
				consumer.accept(item);
			}
			return;
		}
		consumer.accept(multipleValues);
	}

	@Override
	public void addItemAccessor(ParameterInfo parameter, ItemAccessor itemAccessor) {
		if (itemAccessorsByParameter == null) {
			itemAccessorsByParameter = new HashMap<>(1);
		}
		List<ItemAccessor> itemAccessors = itemAccessorsByParameter.get(parameter);
		if (itemAccessors == null) {
			itemAccessors = new ArrayList<>(1);
			itemAccessorsByParameter.put(parameter, itemAccessors);
		}
		itemAccessors.add(itemAccessor);
	}

	@Override
	public ListMatch matches(List<? extends Object> targets, ParameterValueProvider parameters, TemplatesList templates) {
		return PatternMatcher.matchesSubList2(this, targets, parameters, templates);
	}
}
