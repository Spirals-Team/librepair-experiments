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
package spoon.generating;

import spoon.processing.AbstractManualProcessor;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtVariableAccess;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtTypeMember;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtScanner;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

public class CtBiScannerGenerator extends AbstractManualProcessor {
	private static final String TARGET_BISCANNER_PACKAGE = "spoon.reflect.visitor";
	private static final String GENERATING_BISCANNER_PACKAGE = "spoon.generating.scanner";
	private static final String GENERATING_BISCANNER = GENERATING_BISCANNER_PACKAGE + ".CtBiScannerTemplate";

	public void process() {

		final CtLocalVariable<?> peekElement = getFactory().Class()
				.get(GENERATING_BISCANNER_PACKAGE + ".PeekElementTemplate")
				.getMethod("statement")
				.getBody().getStatement(0);
		final CtClass<Object> target = createBiScanner();

		for (CtTypeMember tm : getFactory().Class().get(CtScanner.class).getTypeMembers()) {
			if (!(tm instanceof CtMethod)) {
				continue;
			}
			CtMethod<?> element = (CtMethod) tm;
			if (!element.getSimpleName().startsWith("visitCt")) {
				continue;
			}

			Factory factory = element.getFactory();
			CtMethod<?> clone = factory.Core().clone(element);
			clone.addComment(getFactory().Code().createComment("autogenerated by " + getClass().getSimpleName(), CtComment.CommentType.INLINE));

			// Peek element from Stack.
			final CtLocalVariable<?> peek = factory.Core().clone(peekElement);
			final CtTypeReference type = factory.Core().clone(clone.getParameters().get(0).getType());
			type.getActualTypeArguments().clear();
			peek.getDefaultExpression().addTypeCast(type);
			peek.setType(type);
			clone.getBody().insertBegin(peek);

			for (int i = 2; i < clone.getBody().getStatements().size() - 1; i++) {
				List<CtExpression> invArgs = ((CtInvocation) clone.getBody().getStatement(i)).getArguments();
				if (invArgs.size() <= 1) {
					throw new RuntimeException("You forget the role argument in line "+i+" of method "+element.getSimpleName()+" from "+element.getDeclaringType().getQualifiedName());
				}
				final CtInvocation targetInvocation = (CtInvocation) invArgs.get(1);
				if ("getValue".equals(targetInvocation.getExecutable().getSimpleName()) && "CtLiteral".equals(targetInvocation.getExecutable().getDeclaringType().getSimpleName())) {
					clone.getBody().getStatement(i--).delete();
					continue;
				}
				CtInvocation<?> replace = (CtInvocation<?>) clone.getBody().getStatement(i).clone();

				// Changes to biScan method.
				replace.getExecutable().setSimpleName("biScan");

				// Creates other inv.
				final CtVariableAccess<?> otherRead = factory.Code().createVariableRead(peek.getReference(), false);
				replace.addArgument(factory.Code().createInvocation(otherRead, ((CtInvocation) replace.getArguments().get(1)).getExecutable()));

				if ("Map".equals(targetInvocation.getExecutable().getType().getSimpleName())) {
					((CtExpression) replace.getArguments().get(1)).replace(factory.Code().createInvocation(targetInvocation, factory.Executable().createReference("List Map#values()")));
					CtInvocation invocation = factory.Code().createInvocation(replace.getArguments().get(2).clone(), factory.Executable().createReference("List Map#values()"));
					replace.getArguments().get(2).replace(invocation);
				}

				clone.getBody().getStatement(i).replace(replace);
			}

			target.addMethod(clone);
		}

	}

	private CtClass<Object> createBiScanner() {
		final CtPackage aPackage = getFactory().Package().getOrCreate(TARGET_BISCANNER_PACKAGE);
		final CtClass<Object> target = getFactory().Class().get(GENERATING_BISCANNER);
		//remove class from the old package so it can be added into new package
		target.delete();
		target.setSimpleName("CtBiScannerDefault");
		target.addModifier(ModifierKind.PUBLIC);
		aPackage.addType(target);
		target.addComment(getFactory().Code().createComment("autogenerated by " + getClass().getSimpleName(), CtComment.CommentType.INLINE));
		final List<CtTypeReference> references = target.getElements(new TypeFilter<CtTypeReference>(CtTypeReference.class) {
			@Override
			public boolean matches(CtTypeReference reference) {
				return GENERATING_BISCANNER.equals(reference.getQualifiedName());
			}
		});
		for (CtTypeReference reference : references) {
			reference.setSimpleName("CtBiScannerDefault");
			reference.setPackage(aPackage.getReference());
		}
		return target;
	}
}
