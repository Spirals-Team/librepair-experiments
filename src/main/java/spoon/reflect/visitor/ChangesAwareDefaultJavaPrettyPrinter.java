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
package spoon.reflect.visitor;

import spoon.SpoonException;
import spoon.compiler.Environment;
import spoon.experimental.modelobs.ChangeCollector;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;

/**
 */
public class ChangesAwareDefaultJavaPrettyPrinter extends DefaultJavaPrettyPrinter {

	private final MutableTokenWriter mutableTokenWriter;
	private final DirectPrinterHelper directPrinterHelper;
	private final ChangeCollector changeCollector;

	public ChangesAwareDefaultJavaPrettyPrinter(Environment env) {
		super(env);
		this.changeCollector = ChangeCollector.getChangeCollector(env);
		if (this.changeCollector == null) {
			throw new SpoonException(ChangeCollector.class.getSimpleName() + " was not attached to the Environment");
		}
		directPrinterHelper = new DirectPrinterHelper(env);
		mutableTokenWriter = new MutableTokenWriter(new DefaultTokenWriter(directPrinterHelper));
		setPrinterTokenWriter(mutableTokenWriter);
	}

	private static class DirectPrinterHelper extends PrinterHelper {

		DirectPrinterHelper(Environment env) {
			super(env);
		}

		/**
		 * Prints `str` directly into output buffer ignoring any Environment rules.
		 * @param str to be printed string
		 */
		void directPrint(String str) {
			autoWriteTabs();
			sbf.append(str);
		}
	}

	@Override
	public ChangesAwareDefaultJavaPrettyPrinter scan(CtElement e) {
		if (mutableTokenWriter.isMuted() == false) {
			//it is not muted yet, so some child is modified
			//check if we have source code for this element
			String sourceCode = null;
			SourcePosition pos = e.getPosition();
			if (pos != null) {
				int sourceStart = pos.getSourceStart();
				int sourceEnd = pos.getSourceEnd();
				if (pos != null && sourceStart >= 0 && sourceEnd >= 0) {
					CompilationUnit cu = pos.getCompilationUnit();
					if (cu != null) {
						sourceCode = cu.getOriginalSourceCode();
						if (sourceCode != null) {
							//we have origin source fragment
							//check if this element was changed
							boolean changed = changeCollector.isChangedOrChildChanged(e);
							if (changed == false) {
								//element was not changed and we know start and end of this fragment
								//use origin source instead of printed code
								mutableTokenWriter.setMuted(true);
								directPrinterHelper.directPrint(sourceCode.substring(sourceStart, sourceEnd + 1));
								super.scan(e);
								mutableTokenWriter.setMuted(false);
								return this;
							}
						}
					}
				}
			}
		}
		//it is already muted by an parent. Simply scan ignore all tokens, because the content is not modified and can be copied from source
		//
		super.scan(e);
		return this;
	}

}
