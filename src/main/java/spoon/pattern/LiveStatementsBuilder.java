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
import java.util.function.Consumer;

import spoon.SpoonException;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtForEach;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtStatement;
import spoon.reflect.reference.CtVariableReference;
import spoon.reflect.visitor.CtAbstractVisitor;
import spoon.reflect.visitor.filter.TypeFilter;

import static spoon.pattern.PatternBuilder.bodyToStatements;

/**
 * Builds live statements of Pattern
 *
 * For example if the `for` statement in this pattern model
 * <pre><code>
 * for(Object x : $iterable$) {
 *	System.out.println(x);
 * }
 * </code></pre>
 * is configured as live statement and a Pattern is substituted
 * using parameter <code>$iterable$ = new String[]{"A", "B", "C"}</code>
 * then pattern generated this code
 * <pre><code>
 * System.out.println("A");
 * System.out.println("B");
 * System.out.println("C");
 * </code></pre>
 * because live statements are executed during substitution process and are not included in generated result.
 *
 * The live statements may be used in PatternMatching process (opposite to Pattern substitution) too.
 */
public class LiveStatementsBuilder {

	private final PatternBuilder patternBuilder;
	private boolean failOnMissingParameter = true;

	public LiveStatementsBuilder(PatternBuilder patternBuilder) {
		this.patternBuilder = patternBuilder;
	}

	public LiveStatementsBuilder byVariableName(String variableName) {
		patternBuilder.patternQuery
			.filterChildren(new TypeFilter<>(CtVariableReference.class))
			.map((CtVariableReference<?> varRef) -> {
				return variableName.equals(varRef.getSimpleName()) ? varRef.getParent(CtStatement.class) : null;
			}).forEach((CtStatement stmt) -> {
				//called for first parent statement of all variables named `variableName`
				stmt.accept(new CtAbstractVisitor() {
					@Override
					public void visitCtForEach(CtForEach foreach) {
						markLive(foreach);
					}
					@Override
					public void visitCtIf(CtIf ifElement) {
						markLive(ifElement);
					}
				});
			});
		return this;
	}

	public LiveStatementsBuilder markLive(CtForEach foreach) {
		//detect meta elements by different way - e.g. comments?
		ValueResolver vr = patternBuilder.pattern.getTemplateValueResolver(foreach.getExpression());
		if ((vr instanceof SimpleValueResolver) == false) {
			throw new SpoonException("Each live `for(x : iterable)` statement must have defined pattern parameter for `iterable` expression");
		}
		SimpleValueResolver parameterOfExpression = (SimpleValueResolver) vr;
		MultiModelValueResolver mvr = new MultiModelValueResolver();
		ModelValueResolver modelVR = new ModelValueResolver(patternBuilder.pattern, bodyToStatements(foreach.getBody()));
		mvr.setNestedModel(modelVR);
		mvr.setIterableParameter(parameterOfExpression);
		CtLocalVariable<?> lv = foreach.getVariable();
		//create locally unique name of this local parameter
		String paramName = lv.getSimpleName();
		configureParameters(modelVR, pb -> {
			pb.parameter(paramName).byVariable(lv);
		});
		mvr.setLocalParameter(modelVR.getParameterInfo(paramName, true));
		/*
		 * create Substitution request for whole `foreach`,
		 * resolve the expressions at substitution time
		 * and substitute the body of `foreach` as subpattern
		 * 0 or more times - once for each value of Iterable expression.
		 */
		patternBuilder.pattern.addSubstitutionRequest(mvr, foreach);
		return this;
	}

	public LiveStatementsBuilder markLive(CtIf ifElement) {
		SwitchValueResolver osp = new SwitchValueResolver(patternBuilder.pattern);
		boolean[] canBeLive = new boolean[]{true};
		forEachIfCase(ifElement, (expression, block) -> {
			//detect meta elements by different way - e.g. comments?
			if (expression != null) {
				//expression is not null, it is: if(expression) {}
				ValueResolver vrOfExpression = patternBuilder.pattern.getTemplateValueResolver(expression);
				if (vrOfExpression.getNodeType() != NodeType.NODE) {
					if (failOnMissingParameter) {
						throw new SpoonException("Each live `if` statement must have defined pattern parameter in expression. If you want to ignore this, then call LiveStatementsBuilder#setFailOnMissingParameter(false) first.");
					} else {
						canBeLive[0] = false;
						return;
					}
				}
				if (vrOfExpression instanceof SimpleValueResolver) {
					osp.addCase((SimpleValueResolver) vrOfExpression, new ModelValueResolver(patternBuilder.pattern, bodyToStatements(block)));
				} else {
					throw new SpoonException("Live `if` statement have defined single value pattern parameter in expression. But there is " + vrOfExpression.getClass().getName());
				}
			} else {
				//expression is null, it is: else {}
				osp.addCase(null, new ModelValueResolver(patternBuilder.pattern, bodyToStatements(block)));
			}
		});
		if (canBeLive[0]) {
			/*
			 * create Substitution request for whole `if`,
			 * resolve the expressions at substitution time and substitute only the `if` then/else statements, not `if` itself.
			 */
			patternBuilder.pattern.addSubstitutionRequest(osp, ifElement);
		}
		return this;
	}

	private void configureParameters(ModelValueResolver mvr, Consumer<ParametersBuilder> parametersBuilder) {
		ParametersBuilder pb = new ParametersBuilder(mvr);
		parametersBuilder.accept(pb);
		pb.removeSubstitutionRequestsUnderNodeSubstitutionRequests();
	}

	/**
	 * calls function once for each expression/then block and at the end calls function for last else block.
	 *
	 * @param ifElement
	 * @param consumer
	 * @return true if all function calls returns true or if there is no function call
	 */
	private void forEachIfCase(CtIf ifElement, BiConsumer<CtExpression<Boolean>, CtStatement> consumer) {
		consumer.accept(ifElement.getCondition(), ifElement.getThenStatement());
		CtStatement elseStmt = getElseIfStatement(ifElement.getElseStatement());
		if (elseStmt instanceof CtIf) {
			//another else if case
			forEachIfCase((CtIf) elseStmt, consumer);
		} else if (elseStmt != null) {
			//last else
			consumer.accept(null, elseStmt);
		}
	}

	private CtStatement getElseIfStatement(CtStatement elseStmt) {
		if (elseStmt instanceof CtBlock<?>) {
			CtBlock<?> block = (CtBlock<?>) elseStmt;
			if (block.isImplicit()) {
				List<CtStatement> stmts = block.getStatements();
				if (stmts.size() == 1) {
					if (stmts.get(0) instanceof CtIf) {
						return stmts.get(0);
					}
				}
			}
		}
		if (elseStmt instanceof CtIf) {
			return (CtIf) elseStmt;
		}
		return elseStmt;
	}

	public boolean isFailOnMissingParameter() {
		return failOnMissingParameter;
	}

	/**
	 * @param failOnMissingParameter set true if it should fail when some statement cannot be handled as live
	 * set false if ssuch statement should be kept as part of template.
	 * @return this to support fluent API
	 */
	public LiveStatementsBuilder setFailOnMissingParameter(boolean failOnMissingParameter) {
		this.failOnMissingParameter = failOnMissingParameter;
		return this;
	}
}
