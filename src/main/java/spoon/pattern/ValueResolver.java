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

/**
 * Basic building unit of a Pattern
 */
public interface ValueResolver {

	/**
	 * Provides zero, one or more values depending on kind of this ValueResolver, expected `result` and input `parameters`
	 */
	<T> void resolveValues(ResultHolder<T> result, ParameterValueProvider parameters);

	/**
	 * Matches this {@link ValueResolver} on `targets` using `parameters` and then matches all next `templates`
	 * @param targets to be matched target nodes
	 * @param parameters input parameters
	 * @param templates next to be matched templates
	 * @return {@link ListMatch} contains matching result.
	 */
	ListMatch matches(List<? extends Object> targets, ParameterValueProvider parameters, TemplatesList templates);

	void forEachParameterInfo(BiConsumer<ParameterInfo, ValueResolver> consumer);
}
