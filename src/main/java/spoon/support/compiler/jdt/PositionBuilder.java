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
package spoon.support.compiler.jdt;

import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.Javadoc;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;

import org.eclipse.jdt.internal.compiler.ast.TypeParameter;

import spoon.SpoonException;
import spoon.reflect.code.CtStatementList;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.factory.CoreFactory;

import static spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers;

/**
 * Created by bdanglot on 07/07/16.
 */
public class PositionBuilder {

	private final JDTTreeBuilder jdtTreeBuilder;

	public PositionBuilder(JDTTreeBuilder jdtTreeBuilder) {
		this.jdtTreeBuilder = jdtTreeBuilder;
	}

	SourcePosition buildPosition(int sourceStart, int sourceEnd) {
		CompilationUnit cu = this.jdtTreeBuilder.getContextBuilder().compilationUnitSpoon;
		final int[] lineSeparatorPositions = this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.compilationResult.lineSeparatorPositions;
		return this.jdtTreeBuilder.getFactory().Core().createSourcePosition(cu, sourceStart, sourceEnd, lineSeparatorPositions);
	}

	SourcePosition buildPositionCtElement(CtElement e, ASTNode node) {
		CoreFactory cf = this.jdtTreeBuilder.getFactory().Core();
		CompilationUnit cu = this.jdtTreeBuilder.getFactory().CompilationUnit().create(new String(this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.getFileName()));
		CompilationResult cr = this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.compilationResult;
		int[] lineSeparatorPositions = cr.lineSeparatorPositions;
		char[] contents = cr.compilationUnit.getContents();


		int sourceStart = node.sourceStart;
		int sourceEnd = node.sourceEnd;
		if ((node instanceof Annotation)) {
			Annotation ann = (Annotation) node;
			int declEnd = ann.declarationSourceEnd;

			if (declEnd > 0) {
				sourceEnd = declEnd;
			}
		} else if ((node instanceof Expression)) {
			Expression expression = (Expression) node;
			int statementEnd = expression.statementEnd;

			if (statementEnd > 0) {
				sourceEnd = statementEnd;
			}
		}

		if (node instanceof AbstractVariableDeclaration) {
			AbstractVariableDeclaration variableDeclaration = (AbstractVariableDeclaration) node;
			int modifiersSourceStart = variableDeclaration.modifiersSourceStart;
			int declarationSourceStart = variableDeclaration.declarationSourceStart;
			int declarationSourceEnd = variableDeclaration.declarationSourceEnd;
			int declarationEnd = variableDeclaration.declarationEnd;

			Annotation[] annotations = variableDeclaration.annotations;
			if (annotations != null && annotations.length > 0) {
				if (annotations[0].sourceStart() == sourceStart) {
					modifiersSourceStart = annotations[annotations.length - 1].sourceEnd() + 2;
				}
			}
			if (modifiersSourceStart == 0) {
				modifiersSourceStart = declarationSourceStart;
			}
			int modifiersSourceEnd;
			if (variableDeclaration.type != null) {
				modifiersSourceEnd = variableDeclaration.type.sourceStart() - 2;
			} else {
				// variable that has no type such as TypeParameter
				modifiersSourceEnd = declarationSourceStart - 1;
			}

			// when no modifier
			if (modifiersSourceStart > modifiersSourceEnd) {
				modifiersSourceEnd = modifiersSourceStart - 1;
			}

			return cf.createDeclarationSourcePosition(cu,
					sourceStart, sourceEnd,
					modifiersSourceStart, modifiersSourceEnd,
					declarationSourceStart, declarationSourceEnd,
					lineSeparatorPositions);
		} else if (node instanceof TypeDeclaration) {
			TypeDeclaration typeDeclaration = (TypeDeclaration) node;

			int declarationSourceStart = typeDeclaration.declarationSourceStart;
			int declarationSourceEnd = typeDeclaration.declarationSourceEnd;
			int modifiersSourceStart = typeDeclaration.modifiersSourceStart;
			int bodyStart = typeDeclaration.bodyStart;
			int bodyEnd = typeDeclaration.bodyEnd;

			Annotation[] annotations = typeDeclaration.annotations;
			if (annotations != null && annotations.length > 0) {
				if (annotations[0].sourceStart() == declarationSourceStart) {
					modifiersSourceStart = findNextNonWhitespace(contents, annotations[annotations.length - 1].declarationSourceEnd + 1);
				}
			}
			if (modifiersSourceStart == 0) {
				modifiersSourceStart = declarationSourceStart;
			}
			//look for start of first keyword before the type keyword e.g. "class". `sourceStart` points at first char of type name
			int modifiersSourceEnd = findPrevNonWhitespace(contents, findPrevWhitespace(contents, findPrevNonWhitespace(contents, sourceStart - 1)));
			if (modifiersSourceEnd < modifiersSourceStart) {
				//there is no modifier
				modifiersSourceEnd = modifiersSourceStart - 1;
			}

			return cf.createBodyHolderSourcePosition(cu, sourceStart, sourceEnd,
					modifiersSourceStart, modifiersSourceEnd,
					declarationSourceStart, declarationSourceEnd,
					bodyStart - 1, bodyEnd,
					lineSeparatorPositions);
		} else if (node instanceof AbstractMethodDeclaration) {
			AbstractMethodDeclaration methodDeclaration = (AbstractMethodDeclaration) node;
			int bodyStart = methodDeclaration.bodyStart;
			int bodyEnd = methodDeclaration.bodyEnd;
			int declarationSourceStart = methodDeclaration.declarationSourceStart;
			int declarationSourceEnd = methodDeclaration.declarationSourceEnd;
			int modifiersSourceStart = methodDeclaration.modifiersSourceStart;

			if (modifiersSourceStart == 0) {
				modifiersSourceStart = declarationSourceStart;
			}

			if (node instanceof AnnotationMethodDeclaration && bodyStart == bodyEnd) {
				//The ";" at the end of annotation method declaration is not part of body
				//let it behave same like in abstract MethodDeclaration
				bodyEnd--;
			}

			Javadoc javadoc = methodDeclaration.javadoc;
			if (javadoc != null && javadoc.sourceEnd() > declarationSourceStart) {
				modifiersSourceStart = javadoc.sourceEnd() + 1;
			}
			Annotation[] annotations = methodDeclaration.annotations;
			if (annotations != null && annotations.length > 0) {
				if (annotations[0].sourceStart() == declarationSourceStart) {
					modifiersSourceStart = annotations[annotations.length - 1].sourceEnd() + 2;
				}
			}

			int modifiersSourceEnd = sourceStart - 1;

			if (methodDeclaration instanceof MethodDeclaration && ((MethodDeclaration) methodDeclaration).returnType != null) {
				modifiersSourceEnd = ((MethodDeclaration) methodDeclaration).returnType.sourceStart() - 2;
			}

			TypeParameter[] typeParameters = methodDeclaration.typeParameters();
			if (typeParameters != null && typeParameters.length > 0) {
				modifiersSourceEnd = typeParameters[0].declarationSourceStart - 3;
			}

			if (getModifiers(methodDeclaration.modifiers).isEmpty()) {
				modifiersSourceStart = modifiersSourceEnd + 1;
			}


			sourceEnd = sourceStart + methodDeclaration.selector.length - 1;

			if (e instanceof CtStatementList) {
				return cf.createSourcePosition(cu, bodyStart - 1, bodyEnd + 1, lineSeparatorPositions);
			} else {
				if (bodyStart == 0) {
					return SourcePosition.NOPOSITION;
				} else {
					if (bodyStart < bodyEnd) {
						//include brackets if they are there
						if (contents[bodyStart - 1] == '{') {
							bodyStart--;
							if (contents[bodyEnd + 1] == '}') {
								bodyEnd++;
							} else {
								throw new SpoonException("Missing body end in\n" + new String(contents, sourceStart, sourceEnd - sourceStart));
							}
						}
					}
					return cf.createBodyHolderSourcePosition(cu,
							sourceStart, sourceEnd,
							modifiersSourceStart, modifiersSourceEnd,
							declarationSourceStart, declarationSourceEnd,
							bodyStart, bodyEnd,
							lineSeparatorPositions);
				}
			}
		}

		return cf.createSourcePosition(cu, sourceStart, sourceEnd, lineSeparatorPositions);
	}

	/**
	 * @return index of first non whitespace char, searching forward. Can return off if it is non whitespace.
	 */
	private int findNextNonWhitespace(char[] content, int off) {
		while (off >= 0) {
			char c = content[off];
			if (Character.isWhitespace(c) == false) {
				return off;
			}
			off++;
		}
		return -1;
	}

	/**
	 * @return index of first whitespace char, searching forward. Can return off if it is whitespace.
	 */
	private int findNextWhitespace(char[] content, int off) {
		while (off >= 0) {
			char c = content[off];
			if (Character.isWhitespace(c)) {
				return off;
			}
			off++;
		}
		return -1;
	}
	/**
	 * @return index of first non whitespace char, searching backward. Can return off if it is non whitespace.
	 */
	private int findPrevNonWhitespace(char[] content, int off) {
		while (off >= 0) {
			char c = content[off];
			if (Character.isWhitespace(c) == false) {
				return off;
			}
			off--;
		}
		return -1;
	}

	/**
	 * @return index of first whitespace char, searching backward. Can return off if it is whitespace.
	 */
	private int findPrevWhitespace(char[] content, int off) {
		while (off >= 0) {
			char c = content[off];
			if (Character.isWhitespace(c)) {
				return off;
			}
			off--;
		}
		return -1;
	}
}
