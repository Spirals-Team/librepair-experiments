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

import java.util.function.Consumer;

/**
 * Delivers item of array
 */
public class ArrayItemAccessor implements ItemAccessor {

	final int requiredIndex;

	ArrayItemAccessor(int requiredIndex) {
		super();
		this.requiredIndex = requiredIndex;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getItem(Object array) {
		class Context implements Consumer<Object> {
			int idx = 0;
			Object foundValue = null;

			@Override
			public void accept(Object value) {
				if (idx == requiredIndex) {
					foundValue = value;
				}
				idx++;
			}
		}
		Context ctx = new Context();
		AbstractSimpleValueResolver.forEachItem(array, ctx);
		return (T) ctx.foundValue;
	}
}
