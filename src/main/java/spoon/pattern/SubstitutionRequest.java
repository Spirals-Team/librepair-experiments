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

import java.util.function.BiConsumer;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.path.CtRole;

/**
 * Represents the request for substitution of one value in Pattern model
 */
public interface SubstitutionRequest {

	/**
	 * @return {@link CtRole} whose value will be substituted
	 */
	CtRole getRoleOfSubstitutedValue();

	/**
	 * @return element whose attribute or child node will be substituted
	 */
	CtElement getParentNode();

	/**
	 * @param consumer is called once for each ParameterInfo registered on this SubstitutionRequest
	 */
	void forEachParameterInfo(BiConsumer<ParameterInfo, ValueResolver> consumer);
}
