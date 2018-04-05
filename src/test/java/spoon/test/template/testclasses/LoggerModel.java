/*
 * Copyright (C) 2006-2015 INRIA and contributors
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

package spoon.test.template.testclasses;

import spoon.reflect.code.CtBlock;
import spoon.test.template.testclasses.logger.Logger;

public class LoggerModel {
	private String _classname_;
	private String _methodName_;
	private CtBlock<?> _block_;

	public void block() throws Throwable {
		try {
			Logger.enter(_classname_, _methodName_);
			_block_.S();
		} finally {
			Logger.exit(_methodName_);
		}
	}
}
