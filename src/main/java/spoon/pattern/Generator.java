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

/**
 * API of parameterized generator.
 * Generates AST model depending on it's implementation, required result type and input parameters
 */
public interface Generator extends Parameterized {

	/**
	 * Generates zero, one or more target depending on kind of this {@link Generator}, expected `result` and input `parameters`
	 */
	<T> void generateTargets(ResultHolder<T> result, ParameterValueProvider parameters);

	/**
	 * Generates one target depending on kind of this {@link Generator}, expected `expectedType` and input `parameters`
	 *
	 * @param parameters {@link ParameterValueProvider}
	 * @param expectedType defines {@link Class} of returned value
	 * @return a generate value or null
	 */
	default <T> T generateTarget(ParameterValueProvider parameters, Class<T> expectedType) {
		ResultHolder.Single<T> result = new ResultHolder.Single<>(expectedType);
		generateTargets(result, parameters);
		return result.getResult();
	}

	/**
	 * Generates zero, one or more targets depending on kind of this {@link Generator}, expected `expectedType` and input `parameters`
	 *
	 * @param parameters {@link ParameterValueProvider}
	 * @param expectedType defines {@link Class} of returned value
	 * @return a {@link List} of generated targets
	 */
	default <T> List<T> generateTargets(ParameterValueProvider parameters, Class<T> expectedType) {
		ResultHolder.Multiple<T> result = new ResultHolder.Multiple<>(expectedType);
		generateTargets(result, parameters);
		return result.getResult();
	}
}
