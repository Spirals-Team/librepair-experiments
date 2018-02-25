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

import spoon.pattern.matcher.Matchers;
import spoon.pattern.matcher.TobeMatched;

/**
 * API of matcher.
 * Gets container of to be matched nodes, input/current parameters, a chain of next matchers
 */
public interface Matcher extends Parameterized {
	/**
	 * @param targets to be matched target nodes and input parameters
	 * @param nextMatchers Chain of matchers which has to be processed after this {@link Matcher}
	 * @return new parameters and container with remaining targets
	 */
	TobeMatched matchTargets(TobeMatched targets, Matchers nextMatchers);
}
