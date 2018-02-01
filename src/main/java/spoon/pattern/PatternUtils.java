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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import spoon.SpoonException;

/**
 * Basic building unit of a Pattern
 */
public abstract class PatternUtils {

	private PatternUtils() {
	}

	/**
	 * @param node to be inspected Pattern
	 * @return Map of parameter name to {@link ParameterInfo} for all parameters of `node`
	 */
	public static Map<String, ParameterInfo> getParameters(ValueResolver node) {
		Map<String, ParameterInfo> parameters = new HashMap<>();
		node.forEachParameterInfo((parameter, valueResolver) -> {
			ParameterInfo existingParameter = parameters.get(parameter.getName());
			if (existingParameter != null) {
				if (existingParameter == parameter) {
					//OK, this parameter is already there
					return;
				}
				throw new SpoonException("There is already a parameter: " + parameter.getName());
			}
			parameters.put(parameter.getName(), parameter);
		});
		return Collections.unmodifiableMap(parameters);
	}
	/**
	 * Converts {@link Map} to {@link ParameterValueProvider}
	 * @param params a to be converted map
	 * @return a {@link ParameterValueProvider}, which contains same data like origin `params`
	 */
	public static ParameterValueProvider toParameterValueProvider(Map<String, Object> params) {
		if (params instanceof ParameterValueProvider) {
			return (ParameterValueProvider) params;
		}
		return new UnmodifiableParameterValueProvider(params);
	}
}
