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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import spoon.SpoonException;
import spoon.pattern.matcher.Quantifier;
import spoon.reflect.declaration.CtElement;
import spoon.support.template.ParameterMatcher;
import spoon.template.TemplateMatcher;

/**
 * Represents the parameter of {@link Pattern}
 * defines acceptable value of parameter value during matching. For example type, filter on attribute values.
 */
public class ParameterInfo {
	public static final int UNLIMITED_OCCURENCES = Integer.MAX_VALUE;
	/**
	 * uniquely identifies parameter in scope of owner {@link Pattern}
	 */
	private final String name;
	private boolean multiple = false;
	private int minOccurences = 0;
	private int maxOccurences = UNLIMITED_OCCURENCES;
	private Quantifier matchingStrategy = Quantifier.GREEDY;
	private List<ParameterMatcher> matchConditions;
	private ValueConvertor valueConvertor;
	private Class<?> parameterValueType;

	ParameterInfo(ValueConvertor valueConvertor, String name) {
		super();
		this.name = name;
		if (valueConvertor == null) {
			throw new SpoonException("valueConvertor must not be null");
		}
		this.valueConvertor = valueConvertor;
	}

	public String getName() {
		return name;
	}

	void addMatchCondition(Predicate<Object> matchCondition) {
		addMatchCondition((tm, t, o) -> matchCondition.test(o));
	}
	void addMatchCondition(ParameterMatcher matchCondition) {
		if (matchConditions == null) {
			matchConditions = new ArrayList<>();
		}
		matchConditions.add(matchCondition);
	}

	public boolean matches(TemplateMatcher templateMatcher, CtElement target, CtElement template) {
		if (matchConditions == null) {
			//there is no matching condition. Everything matches
			return true;
		}
		//there is some matching condition. At least one must match
		for (ParameterMatcher predicate : matchConditions) {
			if (predicate.match(templateMatcher, template, target)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName());
		if (isMultiple()) {
			sb.append("[]");
		}
		if (getParameterValueType() != null) {
			sb.append(" : ");
			sb.append(getParameterValueType().getName());
		}
		return sb.toString();
	}

	/**
	 * @return true if the value container has to be a List, otherwise the container will be a single value
	 */
	public boolean isMultiple() {
		return multiple;
	}

	public ParameterInfo setMultiple(boolean multiple) {
		this.multiple = multiple;
		return this;
	}

	/**
	 * @return a type of parameter value - if known
	 *
	 * Note: Pattern builder needs to know the value type to be able to select substitute node.
	 * For example patter:
	 *   return _expression_.S();
	 * either replaces only `_expression_.S()` if the parameter value is an expression
	 * or replaces `return _expression_.S()` if the parameter value is a CtBlock
	 */
	public Class<?> getParameterValueType() {
		return parameterValueType;
	}

	public ParameterInfo setParameterValueType(Class<?> parameterValueType) {
		this.parameterValueType = parameterValueType;
		return this;
	}

	/**
	 * Adds `newValue` into `existingValue` and returns object which contains all the values together
	 * @param existingValue existing immutable container
	 * @param itemAccessors optional item accessors
	 * @param newValue the value which has to be added into `existingValue` container following `itemAccessors`
	 * @return new immutable container which contains newValue
	 */
	@SuppressWarnings("unchecked")
	public Object addValue(Object existingValue, List<ItemAccessor> itemAccessors, Object newValue) {
		if (isMultiple()) {
			/**
			 * The ParameterValueProvider is immutable, because PatternMatching process needs
			 * immutable copies during matching process on different potential matches.
			 * So we must not try to modify it's values
			 */
			Collection<Object> values = null;
			if (existingValue instanceof Collection) {
				values = createMultiValueContainer((Collection<Object>) existingValue);
			} else if (existingValue == null) {
				values = createMultiValueContainer(null);
			} else {
				throw new SpoonException("Unsupported multivalue container: " + existingValue.getClass());
			}
			//TODO use itemAccessors somehow ...
			values.add(newValue);
			return unmodifiable(values);
		}
		if (existingValue != null) {
			if (existingValue.equals(newValue) == false) {
				throw new SpoonException("Cannot add multiple different values into parameter " + getName());
			}
		}
		return existingValue;
	}

	protected Object unmodifiable(Object container) {
		return Collections.unmodifiableList((List<?>) container);
	}

	protected Collection<Object> createMultiValueContainer(Collection<Object> oldCollection) {
		if (oldCollection == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(oldCollection);
	}

	public int getMinOccurences() {
		return minOccurences;
	}

	void setMinOccurences(int minOccurences) {
		this.minOccurences = minOccurences;
	}

	/**
	 * @return maximum number of values in this parameter.
	 * Note: if {@link #isMultiple()}==false, then it never returns value &gt; 1
	 */
	public int getMaxOccurences() {
		return multiple ? maxOccurences : Math.min(maxOccurences, 1);
	}

	void setMaxOccurences(int maxOccurences) {
		this.maxOccurences = maxOccurences;
	}

	public Quantifier getMatchingStrategy() {
		return matchingStrategy;
	}

	/**
	 * @return the {@link ValueConvertor} used by reading and writing into parameter values defined by this {@link ParameterInfo}
	 */
	public ValueConvertor getValueConvertor() {
		return valueConvertor;
	}

	/**
	 * @param valueConvertor the {@link ValueConvertor} used by reading and writing into parameter values defined by this {@link ParameterInfo}
	 */
	public void setValueConvertor(ValueConvertor valueConvertor) {
		if (valueConvertor == null) {
			throw new SpoonException("valueConvertor must not be null");
		}
		this.valueConvertor = valueConvertor;
	}
}
