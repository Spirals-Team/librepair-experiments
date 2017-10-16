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
package spoon.reflect.meta.impl;


/**
 * Contains implementations of {@link RoleHandler}s for all {@link CtRole}s of all model elements
 */
class ModelRoleHandlers {
	static final spoon.reflect.meta.RoleHandler[] roleHandlers = new spoon.reflect.meta.RoleHandler[]{ new CtNamedElement_NAME_RoleHandler(), new CtReference_NAME_RoleHandler(), new CtArrayTypeReference_TYPE_RoleHandler(), new CtExecutableReference_TYPE_RoleHandler(), new CtMultiTypedElement_TYPE_RoleHandler(), new CtTypedElement_TYPE_RoleHandler(), new CtVariableReference_TYPE_RoleHandler(), new CtExecutableReference_DECLARING_TYPE_RoleHandler(), new CtFieldReference_DECLARING_TYPE_RoleHandler(), new CtTypeReference_DECLARING_TYPE_RoleHandler(), new CtPackage_CONTAINED_TYPES_RoleHandler(), new CtBodyHolder_BODY_RoleHandler(), new CtSynchronized_BODY_RoleHandler(), new CtShadowable_IS_SHADOW_RoleHandler(), new CtIntersectionTypeReference_BOUND_RoleHandler(), new CtTypeParameterReference_BOUNDING_TYPE_RoleHandler(), new CtFieldReference_IS_FINAL_RoleHandler(), new CtExecutableReference_IS_STATIC_RoleHandler(), new CtFieldReference_IS_STATIC_RoleHandler(), new CtTypeParameterReference_IS_UPPER_RoleHandler(), new CtElement_IS_IMPLICIT_RoleHandler(), new CtMethod_IS_DEFAULT_RoleHandler(), new CtParameter_IS_VARARGS_RoleHandler(), new CtAnnotationMethod_DEFAULT_EXPRESSION_RoleHandler(), new CtVariable_DEFAULT_EXPRESSION_RoleHandler(), new CtConditional_THEN_RoleHandler(), new CtIf_THEN_RoleHandler(), new CtConditional_ELSE_RoleHandler(), new CtIf_ELSE_RoleHandler(), new CtTypeReference_PACKAGE_REF_RoleHandler(), new CtPackage_SUB_PACKAGE_RoleHandler(), new CtAssert_CONDITION_RoleHandler(), new CtConditional_CONDITION_RoleHandler(), new CtIf_CONDITION_RoleHandler(), new CtBinaryOperator_RIGHT_OPERAND_RoleHandler(), new CtBinaryOperator_LEFT_OPERAND_RoleHandler(), new CtStatement_LABEL_RoleHandler(), new CtSwitch_CASE_RoleHandler(), new CtBinaryOperator_OPERATOR_KIND_RoleHandler(), new CtOperatorAssignment_OPERATOR_KIND_RoleHandler(), new CtUnaryOperator_OPERATOR_KIND_RoleHandler(), new CtCatch_PARAMETER_RoleHandler(), new CtExecutable_PARAMETER_RoleHandler(), new CtExecutableReference_PARAMETER_RoleHandler(), new CtArrayAccess_EXPRESSION_RoleHandler(), new CtAssert_EXPRESSION_RoleHandler(), new CtCase_EXPRESSION_RoleHandler(), new CtCodeSnippet_EXPRESSION_RoleHandler(), new CtDo_EXPRESSION_RoleHandler(), new CtFor_EXPRESSION_RoleHandler(), new CtForEach_EXPRESSION_RoleHandler(), new CtLambda_EXPRESSION_RoleHandler(), new CtNewArray_EXPRESSION_RoleHandler(), new CtReturn_EXPRESSION_RoleHandler(), new CtSwitch_EXPRESSION_RoleHandler(), new CtSynchronized_EXPRESSION_RoleHandler(), new CtUnaryOperator_EXPRESSION_RoleHandler(), new CtWhile_EXPRESSION_RoleHandler(), new CtTargetedExpression_TARGET_RoleHandler(), new CtAnnotationFieldAccess_VARIABLE_RoleHandler(), new CtFieldAccess_VARIABLE_RoleHandler(), new CtForEach_VARIABLE_RoleHandler(), new CtVariableAccess_VARIABLE_RoleHandler(), new CtTry_FINALIZER_RoleHandler(), new CtThrow_THROWN_RoleHandler(), new CtExecutable_THROWN_TYPE_RoleHandler(), new CtRHSReceiver_ASSIGNMENT_RoleHandler(), new CtAssignment_ASSIGNED_RoleHandler(), new CtModifiable_MODIFIER_RoleHandler(), new CtTypeInformation_MODIFIER_RoleHandler(), new CtElement_COMMENT_RoleHandler(), new CtAnnotation_ANNOTATION_TYPE_RoleHandler(), new CtTypeInformation_INTERFACE_RoleHandler(), new CtElement_ANNOTATION_RoleHandler(), new CtStatementList_STATEMENT_RoleHandler(), new CtAbstractInvocation_ARGUMENT_RoleHandler(), new CtTypeInformation_SUPER_TYPE_RoleHandler(), new CtNewClass_NESTED_TYPE_RoleHandler(), new CtType_NESTED_TYPE_RoleHandler(), new CtClass_CONSTRUCTOR_RoleHandler(), new CtAbstractInvocation_EXECUTABLE_REF_RoleHandler(), new CtExecutableReferenceExpression_EXECUTABLE_REF_RoleHandler(), new CtParameterReference_EXECUTABLE_REF_RoleHandler(), new CtType_METHOD_RoleHandler(), new CtClass_ANNONYMOUS_EXECUTABLE_RoleHandler(), new CtType_FIELD_RoleHandler(), new CtExpression_CAST_RoleHandler(), new CtAnnotation_VALUE_RoleHandler(), new CtEnum_VALUE_RoleHandler(), new CtLiteral_VALUE_RoleHandler(), new CtFor_FOR_UPDATE_RoleHandler(), new CtFor_FOR_INIT_RoleHandler(), new CtTryWithResource_TRY_RESOURCE_RoleHandler(), new CtNewArray_DIMENSION_RoleHandler(), new CtTry_CATCH_RoleHandler(), new CtLabelledFlowBreak_TARGET_LABEL_RoleHandler(), new CtActualTypeContainer_TYPE_PARAMETER_RoleHandler(), new CtFormalTypeDeclarer_TYPE_PARAMETER_RoleHandler(), new CtJavaDoc_COMMENT_TAG_RoleHandler(), new CtComment_COMMENT_CONTENT_RoleHandler(), new CtJavaDocTag_COMMENT_CONTENT_RoleHandler(), new CtComment_COMMENT_TYPE_RoleHandler(), new CtJavaDocTag_DOCUMENTATION_TYPE_RoleHandler(), new CtJavaDocTag_JAVADOC_TAG_VALUE_RoleHandler(), new CtElement_POSITION_RoleHandler() };

	private ModelRoleHandlers() {
	}

	static class CtNamedElement_NAME_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtNamedElement, java.lang.String> {
		private CtNamedElement_NAME_RoleHandler() {
			super(spoon.reflect.path.CtRole.NAME, spoon.reflect.declaration.CtNamedElement.class, java.lang.String.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getSimpleName();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setSimpleName(castValue(value));
		}
	}

	static class CtReference_NAME_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtReference, java.lang.String> {
		private CtReference_NAME_RoleHandler() {
			super(spoon.reflect.path.CtRole.NAME, spoon.reflect.reference.CtReference.class, java.lang.String.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getSimpleName();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setSimpleName(castValue(value));
		}
	}

	static class CtArrayTypeReference_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtArrayTypeReference, spoon.reflect.reference.CtTypeReference<?>> {
		private CtArrayTypeReference_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.TYPE, spoon.reflect.reference.CtArrayTypeReference.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getComponentType();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setComponentType(castValue(value));
		}
	}

	static class CtExecutableReference_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtExecutableReference, spoon.reflect.reference.CtTypeReference<?>> {
		private CtExecutableReference_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.TYPE, spoon.reflect.reference.CtExecutableReference.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getType();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setType(castValue(value));
		}
	}

	static class CtMultiTypedElement_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.declaration.CtMultiTypedElement, spoon.reflect.reference.CtTypeReference<?>> {
		private CtMultiTypedElement_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.TYPE, spoon.reflect.declaration.CtMultiTypedElement.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getMultiTypes();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setMultiTypes(castValue(value));
		}
	}

	static class CtTypedElement_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtTypedElement, spoon.reflect.reference.CtTypeReference<?>> {
		private CtTypedElement_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.TYPE, spoon.reflect.declaration.CtTypedElement.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getType();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setType(castValue(value));
		}
	}

	static class CtVariableReference_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtVariableReference, spoon.reflect.reference.CtTypeReference<?>> {
		private CtVariableReference_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.TYPE, spoon.reflect.reference.CtVariableReference.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getType();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setType(castValue(value));
		}
	}

	static class CtExecutableReference_DECLARING_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtExecutableReference, spoon.reflect.reference.CtTypeReference<?>> {
		private CtExecutableReference_DECLARING_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.DECLARING_TYPE, spoon.reflect.reference.CtExecutableReference.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getDeclaringType();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setDeclaringType(castValue(value));
		}
	}

	static class CtFieldReference_DECLARING_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtFieldReference, spoon.reflect.reference.CtTypeReference<?>> {
		private CtFieldReference_DECLARING_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.DECLARING_TYPE, spoon.reflect.reference.CtFieldReference.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getDeclaringType();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setDeclaringType(castValue(value));
		}
	}

	static class CtTypeReference_DECLARING_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtTypeReference, spoon.reflect.reference.CtTypeReference<?>> {
		private CtTypeReference_DECLARING_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.DECLARING_TYPE, spoon.reflect.reference.CtTypeReference.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getDeclaringType();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setDeclaringType(castValue(value));
		}
	}

	static class CtPackage_CONTAINED_TYPES_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SetHandler<spoon.reflect.declaration.CtPackage, spoon.reflect.declaration.CtType<?>> {
		private CtPackage_CONTAINED_TYPES_RoleHandler() {
			super(spoon.reflect.path.CtRole.CONTAINED_TYPES, spoon.reflect.declaration.CtPackage.class, spoon.reflect.declaration.CtType.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getTypes();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setTypes(castValue(value));
		}
	}

	static class CtBodyHolder_BODY_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtBodyHolder, spoon.reflect.code.CtStatement> {
		private CtBodyHolder_BODY_RoleHandler() {
			super(spoon.reflect.path.CtRole.BODY, spoon.reflect.code.CtBodyHolder.class, spoon.reflect.code.CtStatement.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getBody();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setBody(castValue(value));
		}
	}

	static class CtSynchronized_BODY_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtSynchronized, spoon.reflect.code.CtBlock<?>> {
		private CtSynchronized_BODY_RoleHandler() {
			super(spoon.reflect.path.CtRole.BODY, spoon.reflect.code.CtSynchronized.class, spoon.reflect.code.CtBlock.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getBlock();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setBlock(castValue(value));
		}
	}

	static class CtShadowable_IS_SHADOW_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtShadowable, java.lang.Boolean> {
		private CtShadowable_IS_SHADOW_RoleHandler() {
			super(spoon.reflect.path.CtRole.IS_SHADOW, spoon.reflect.declaration.CtShadowable.class, java.lang.Boolean.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).isShadow();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setShadow(castValue(value));
		}
	}

	static class CtIntersectionTypeReference_BOUND_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.reference.CtIntersectionTypeReference, spoon.reflect.reference.CtTypeReference<?>> {
		private CtIntersectionTypeReference_BOUND_RoleHandler() {
			super(spoon.reflect.path.CtRole.BOUND, spoon.reflect.reference.CtIntersectionTypeReference.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getBounds();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setBounds(castValue(value));
		}
	}

	static class CtTypeParameterReference_BOUNDING_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtTypeParameterReference, spoon.reflect.reference.CtTypeReference<?>> {
		private CtTypeParameterReference_BOUNDING_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.BOUNDING_TYPE, spoon.reflect.reference.CtTypeParameterReference.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getBoundingType();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setBoundingType(castValue(value));
		}
	}

	static class CtFieldReference_IS_FINAL_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtFieldReference, java.lang.Boolean> {
		private CtFieldReference_IS_FINAL_RoleHandler() {
			super(spoon.reflect.path.CtRole.IS_FINAL, spoon.reflect.reference.CtFieldReference.class, java.lang.Boolean.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).isFinal();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setFinal(castValue(value));
		}
	}

	static class CtExecutableReference_IS_STATIC_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtExecutableReference, java.lang.Boolean> {
		private CtExecutableReference_IS_STATIC_RoleHandler() {
			super(spoon.reflect.path.CtRole.IS_STATIC, spoon.reflect.reference.CtExecutableReference.class, java.lang.Boolean.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).isStatic();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setStatic(castValue(value));
		}
	}

	static class CtFieldReference_IS_STATIC_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtFieldReference, java.lang.Boolean> {
		private CtFieldReference_IS_STATIC_RoleHandler() {
			super(spoon.reflect.path.CtRole.IS_STATIC, spoon.reflect.reference.CtFieldReference.class, java.lang.Boolean.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).isStatic();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setStatic(castValue(value));
		}
	}

	static class CtTypeParameterReference_IS_UPPER_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtTypeParameterReference, java.lang.Boolean> {
		private CtTypeParameterReference_IS_UPPER_RoleHandler() {
			super(spoon.reflect.path.CtRole.IS_UPPER, spoon.reflect.reference.CtTypeParameterReference.class, java.lang.Boolean.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).isUpper();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setUpper(castValue(value));
		}
	}

	static class CtElement_IS_IMPLICIT_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtElement, java.lang.Boolean> {
		private CtElement_IS_IMPLICIT_RoleHandler() {
			super(spoon.reflect.path.CtRole.IS_IMPLICIT, spoon.reflect.declaration.CtElement.class, java.lang.Boolean.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).isImplicit();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setImplicit(castValue(value));
		}
	}

	static class CtMethod_IS_DEFAULT_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtMethod, java.lang.Boolean> {
		private CtMethod_IS_DEFAULT_RoleHandler() {
			super(spoon.reflect.path.CtRole.IS_DEFAULT, spoon.reflect.declaration.CtMethod.class, java.lang.Boolean.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).isDefaultMethod();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setDefaultMethod(castValue(value));
		}
	}

	static class CtParameter_IS_VARARGS_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtParameter, java.lang.Boolean> {
		private CtParameter_IS_VARARGS_RoleHandler() {
			super(spoon.reflect.path.CtRole.IS_VARARGS, spoon.reflect.declaration.CtParameter.class, java.lang.Boolean.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).isVarArgs();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setVarArgs(castValue(value));
		}
	}

	static class CtAnnotationMethod_DEFAULT_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtAnnotationMethod, spoon.reflect.code.CtExpression<?>> {
		private CtAnnotationMethod_DEFAULT_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, spoon.reflect.declaration.CtAnnotationMethod.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getDefaultExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setDefaultExpression(castValue(value));
		}
	}

	static class CtVariable_DEFAULT_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtVariable, spoon.reflect.code.CtExpression<?>> {
		private CtVariable_DEFAULT_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, spoon.reflect.declaration.CtVariable.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getDefaultExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setDefaultExpression(castValue(value));
		}
	}

	static class CtConditional_THEN_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtConditional, spoon.reflect.code.CtExpression<?>> {
		private CtConditional_THEN_RoleHandler() {
			super(spoon.reflect.path.CtRole.THEN, spoon.reflect.code.CtConditional.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getThenExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setThenExpression(castValue(value));
		}
	}

	static class CtIf_THEN_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtIf, spoon.reflect.code.CtStatement> {
		private CtIf_THEN_RoleHandler() {
			super(spoon.reflect.path.CtRole.THEN, spoon.reflect.code.CtIf.class, spoon.reflect.code.CtStatement.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getThenStatement();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setThenStatement(castValue(value));
		}
	}

	static class CtConditional_ELSE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtConditional, spoon.reflect.code.CtExpression<?>> {
		private CtConditional_ELSE_RoleHandler() {
			super(spoon.reflect.path.CtRole.ELSE, spoon.reflect.code.CtConditional.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getElseExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setElseExpression(castValue(value));
		}
	}

	static class CtIf_ELSE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtIf, spoon.reflect.code.CtStatement> {
		private CtIf_ELSE_RoleHandler() {
			super(spoon.reflect.path.CtRole.ELSE, spoon.reflect.code.CtIf.class, spoon.reflect.code.CtStatement.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getElseStatement();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setElseStatement(castValue(value));
		}
	}

	static class CtTypeReference_PACKAGE_REF_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtTypeReference, spoon.reflect.reference.CtPackageReference> {
		private CtTypeReference_PACKAGE_REF_RoleHandler() {
			super(spoon.reflect.path.CtRole.PACKAGE_REF, spoon.reflect.reference.CtTypeReference.class, spoon.reflect.reference.CtPackageReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getPackage();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setPackage(castValue(value));
		}
	}

	static class CtPackage_SUB_PACKAGE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SetHandler<spoon.reflect.declaration.CtPackage, spoon.reflect.declaration.CtPackage> {
		private CtPackage_SUB_PACKAGE_RoleHandler() {
			super(spoon.reflect.path.CtRole.SUB_PACKAGE, spoon.reflect.declaration.CtPackage.class, spoon.reflect.declaration.CtPackage.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getPackages();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setPackages(castValue(value));
		}
	}

	static class CtAssert_CONDITION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtAssert, spoon.reflect.code.CtExpression<java.lang.Boolean>> {
		private CtAssert_CONDITION_RoleHandler() {
			super(spoon.reflect.path.CtRole.CONDITION, spoon.reflect.code.CtAssert.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getAssertExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setAssertExpression(castValue(value));
		}
	}

	static class CtConditional_CONDITION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtConditional, spoon.reflect.code.CtExpression<java.lang.Boolean>> {
		private CtConditional_CONDITION_RoleHandler() {
			super(spoon.reflect.path.CtRole.CONDITION, spoon.reflect.code.CtConditional.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getCondition();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setCondition(castValue(value));
		}
	}

	static class CtIf_CONDITION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtIf, spoon.reflect.code.CtExpression<java.lang.Boolean>> {
		private CtIf_CONDITION_RoleHandler() {
			super(spoon.reflect.path.CtRole.CONDITION, spoon.reflect.code.CtIf.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getCondition();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setCondition(castValue(value));
		}
	}

	static class CtBinaryOperator_RIGHT_OPERAND_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtBinaryOperator, spoon.reflect.code.CtExpression<?>> {
		private CtBinaryOperator_RIGHT_OPERAND_RoleHandler() {
			super(spoon.reflect.path.CtRole.RIGHT_OPERAND, spoon.reflect.code.CtBinaryOperator.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getRightHandOperand();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setRightHandOperand(castValue(value));
		}
	}

	static class CtBinaryOperator_LEFT_OPERAND_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtBinaryOperator, spoon.reflect.code.CtExpression<?>> {
		private CtBinaryOperator_LEFT_OPERAND_RoleHandler() {
			super(spoon.reflect.path.CtRole.LEFT_OPERAND, spoon.reflect.code.CtBinaryOperator.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getLeftHandOperand();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setLeftHandOperand(castValue(value));
		}
	}

	static class CtStatement_LABEL_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtStatement, java.lang.String> {
		private CtStatement_LABEL_RoleHandler() {
			super(spoon.reflect.path.CtRole.LABEL, spoon.reflect.code.CtStatement.class, java.lang.String.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getLabel();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setLabel(castValue(value));
		}
	}

	static class CtSwitch_CASE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.code.CtSwitch, spoon.reflect.code.CtCase<?>> {
		private CtSwitch_CASE_RoleHandler() {
			super(spoon.reflect.path.CtRole.CASE, spoon.reflect.code.CtSwitch.class, spoon.reflect.code.CtCase.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getCases();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setCases(castValue(value));
		}
	}

	static class CtBinaryOperator_OPERATOR_KIND_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtBinaryOperator, spoon.reflect.code.BinaryOperatorKind> {
		private CtBinaryOperator_OPERATOR_KIND_RoleHandler() {
			super(spoon.reflect.path.CtRole.OPERATOR_KIND, spoon.reflect.code.CtBinaryOperator.class, spoon.reflect.code.BinaryOperatorKind.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getKind();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setKind(castValue(value));
		}
	}

	static class CtOperatorAssignment_OPERATOR_KIND_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtOperatorAssignment, spoon.reflect.code.BinaryOperatorKind> {
		private CtOperatorAssignment_OPERATOR_KIND_RoleHandler() {
			super(spoon.reflect.path.CtRole.OPERATOR_KIND, spoon.reflect.code.CtOperatorAssignment.class, spoon.reflect.code.BinaryOperatorKind.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getKind();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setKind(castValue(value));
		}
	}

	static class CtUnaryOperator_OPERATOR_KIND_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtUnaryOperator, spoon.reflect.code.UnaryOperatorKind> {
		private CtUnaryOperator_OPERATOR_KIND_RoleHandler() {
			super(spoon.reflect.path.CtRole.OPERATOR_KIND, spoon.reflect.code.CtUnaryOperator.class, spoon.reflect.code.UnaryOperatorKind.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getKind();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setKind(castValue(value));
		}
	}

	static class CtCatch_PARAMETER_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtCatch, spoon.reflect.code.CtCatchVariable<? extends java.lang.Throwable>> {
		private CtCatch_PARAMETER_RoleHandler() {
			super(spoon.reflect.path.CtRole.PARAMETER, spoon.reflect.code.CtCatch.class, spoon.reflect.code.CtCatchVariable.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getParameter();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setParameter(castValue(value));
		}
	}

	static class CtExecutable_PARAMETER_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.declaration.CtExecutable, spoon.reflect.declaration.CtParameter<?>> {
		private CtExecutable_PARAMETER_RoleHandler() {
			super(spoon.reflect.path.CtRole.PARAMETER, spoon.reflect.declaration.CtExecutable.class, spoon.reflect.declaration.CtParameter.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getParameters();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setParameters(castValue(value));
		}
	}

	static class CtExecutableReference_PARAMETER_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.reference.CtExecutableReference, spoon.reflect.reference.CtTypeReference<?>> {
		private CtExecutableReference_PARAMETER_RoleHandler() {
			super(spoon.reflect.path.CtRole.PARAMETER, spoon.reflect.reference.CtExecutableReference.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getParameters();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setParameters(castValue(value));
		}
	}

	static class CtArrayAccess_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtArrayAccess, spoon.reflect.code.CtExpression<java.lang.Integer>> {
		private CtArrayAccess_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtArrayAccess.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getIndexExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setIndexExpression(castValue(value));
		}
	}

	static class CtAssert_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtAssert, spoon.reflect.code.CtExpression<?>> {
		private CtAssert_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtAssert.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setExpression(castValue(value));
		}
	}

	static class CtCase_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtCase, spoon.reflect.code.CtExpression<?>> {
		private CtCase_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtCase.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getCaseExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setCaseExpression(castValue(value));
		}
	}

	static class CtCodeSnippet_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtCodeSnippet, java.lang.String> {
		private CtCodeSnippet_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.declaration.CtCodeSnippet.class, java.lang.String.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getValue();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setValue(castValue(value));
		}
	}

	static class CtDo_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtDo, spoon.reflect.code.CtExpression<java.lang.Boolean>> {
		private CtDo_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtDo.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getLoopingExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setLoopingExpression(castValue(value));
		}
	}

	static class CtFor_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtFor, spoon.reflect.code.CtExpression<java.lang.Boolean>> {
		private CtFor_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtFor.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setExpression(castValue(value));
		}
	}

	static class CtForEach_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtForEach, spoon.reflect.code.CtExpression<?>> {
		private CtForEach_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtForEach.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setExpression(castValue(value));
		}
	}

	static class CtLambda_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtLambda, spoon.reflect.code.CtExpression<?>> {
		private CtLambda_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtLambda.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setExpression(castValue(value));
		}
	}

	static class CtNewArray_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.code.CtNewArray, spoon.reflect.code.CtExpression<?>> {
		private CtNewArray_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtNewArray.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getElements();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setElements(castValue(value));
		}
	}

	static class CtReturn_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtReturn, spoon.reflect.code.CtExpression<?>> {
		private CtReturn_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtReturn.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getReturnedExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setReturnedExpression(castValue(value));
		}
	}

	static class CtSwitch_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtSwitch, spoon.reflect.code.CtExpression<?>> {
		private CtSwitch_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtSwitch.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getSelector();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setSelector(castValue(value));
		}
	}

	static class CtSynchronized_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtSynchronized, spoon.reflect.code.CtExpression<?>> {
		private CtSynchronized_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtSynchronized.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setExpression(castValue(value));
		}
	}

	static class CtUnaryOperator_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtUnaryOperator, spoon.reflect.code.CtExpression<?>> {
		private CtUnaryOperator_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtUnaryOperator.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getOperand();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setOperand(castValue(value));
		}
	}

	static class CtWhile_EXPRESSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtWhile, spoon.reflect.code.CtExpression<java.lang.Boolean>> {
		private CtWhile_EXPRESSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXPRESSION, spoon.reflect.code.CtWhile.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getLoopingExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setLoopingExpression(castValue(value));
		}
	}

	static class CtTargetedExpression_TARGET_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtTargetedExpression, spoon.reflect.code.CtExpression<?>> {
		private CtTargetedExpression_TARGET_RoleHandler() {
			super(spoon.reflect.path.CtRole.TARGET, spoon.reflect.code.CtTargetedExpression.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getTarget();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setTarget(castValue(value));
		}
	}

	static class CtAnnotationFieldAccess_VARIABLE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtAnnotationFieldAccess, spoon.reflect.reference.CtFieldReference<?>> {
		private CtAnnotationFieldAccess_VARIABLE_RoleHandler() {
			super(spoon.reflect.path.CtRole.VARIABLE, spoon.reflect.code.CtAnnotationFieldAccess.class, spoon.reflect.reference.CtFieldReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getVariable();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setVariable(castValue(value));
		}
	}

	static class CtFieldAccess_VARIABLE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtFieldAccess, spoon.reflect.reference.CtFieldReference<?>> {
		private CtFieldAccess_VARIABLE_RoleHandler() {
			super(spoon.reflect.path.CtRole.VARIABLE, spoon.reflect.code.CtFieldAccess.class, spoon.reflect.reference.CtFieldReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getVariable();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setVariable(castValue(value));
		}
	}

	static class CtForEach_VARIABLE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtForEach, spoon.reflect.code.CtLocalVariable<?>> {
		private CtForEach_VARIABLE_RoleHandler() {
			super(spoon.reflect.path.CtRole.VARIABLE, spoon.reflect.code.CtForEach.class, spoon.reflect.code.CtLocalVariable.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getVariable();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setVariable(castValue(value));
		}
	}

	static class CtVariableAccess_VARIABLE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtVariableAccess, spoon.reflect.reference.CtVariableReference<?>> {
		private CtVariableAccess_VARIABLE_RoleHandler() {
			super(spoon.reflect.path.CtRole.VARIABLE, spoon.reflect.code.CtVariableAccess.class, spoon.reflect.reference.CtVariableReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getVariable();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setVariable(castValue(value));
		}
	}

	static class CtTry_FINALIZER_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtTry, spoon.reflect.code.CtBlock<?>> {
		private CtTry_FINALIZER_RoleHandler() {
			super(spoon.reflect.path.CtRole.FINALIZER, spoon.reflect.code.CtTry.class, spoon.reflect.code.CtBlock.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getFinalizer();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setFinalizer(castValue(value));
		}
	}

	static class CtThrow_THROWN_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtThrow, spoon.reflect.code.CtExpression<? extends java.lang.Throwable>> {
		private CtThrow_THROWN_RoleHandler() {
			super(spoon.reflect.path.CtRole.THROWN, spoon.reflect.code.CtThrow.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getThrownExpression();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setThrownExpression(castValue(value));
		}
	}

	static class CtExecutable_THROWN_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SetHandler<spoon.reflect.declaration.CtExecutable, spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> {
		private CtExecutable_THROWN_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.THROWN_TYPE, spoon.reflect.declaration.CtExecutable.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getThrownTypes();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setThrownTypes(castValue(value));
		}
	}

	static class CtRHSReceiver_ASSIGNMENT_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtRHSReceiver, spoon.reflect.code.CtExpression<?>> {
		private CtRHSReceiver_ASSIGNMENT_RoleHandler() {
			super(spoon.reflect.path.CtRole.ASSIGNMENT, spoon.reflect.code.CtRHSReceiver.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getAssignment();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setAssignment(castValue(value));
		}
	}

	static class CtAssignment_ASSIGNED_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtAssignment, spoon.reflect.code.CtExpression<?>> {
		private CtAssignment_ASSIGNED_RoleHandler() {
			super(spoon.reflect.path.CtRole.ASSIGNED, spoon.reflect.code.CtAssignment.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getAssigned();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setAssigned(castValue(value));
		}
	}

	static class CtModifiable_MODIFIER_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SetHandler<spoon.reflect.declaration.CtModifiable, spoon.reflect.declaration.ModifierKind> {
		private CtModifiable_MODIFIER_RoleHandler() {
			super(spoon.reflect.path.CtRole.MODIFIER, spoon.reflect.declaration.CtModifiable.class, spoon.reflect.declaration.ModifierKind.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getModifiers();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setModifiers(castValue(value));
		}
	}

	static class CtTypeInformation_MODIFIER_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SetHandler<spoon.reflect.declaration.CtTypeInformation, spoon.reflect.declaration.ModifierKind> {
		private CtTypeInformation_MODIFIER_RoleHandler() {
			super(spoon.reflect.path.CtRole.MODIFIER, spoon.reflect.declaration.CtTypeInformation.class, spoon.reflect.declaration.ModifierKind.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getModifiers();
		}
	}

	static class CtElement_COMMENT_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.declaration.CtElement, spoon.reflect.code.CtComment> {
		private CtElement_COMMENT_RoleHandler() {
			super(spoon.reflect.path.CtRole.COMMENT, spoon.reflect.declaration.CtElement.class, spoon.reflect.code.CtComment.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getComments();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setComments(castValue(value));
		}
	}

	static class CtAnnotation_ANNOTATION_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtAnnotation, spoon.reflect.reference.CtTypeReference<?>> {
		private CtAnnotation_ANNOTATION_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.ANNOTATION_TYPE, spoon.reflect.declaration.CtAnnotation.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getAnnotationType();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setAnnotationType(castValue(value));
		}
	}

	static class CtTypeInformation_INTERFACE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SetHandler<spoon.reflect.declaration.CtTypeInformation, spoon.reflect.reference.CtTypeReference<?>> {
		private CtTypeInformation_INTERFACE_RoleHandler() {
			super(spoon.reflect.path.CtRole.INTERFACE, spoon.reflect.declaration.CtTypeInformation.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getSuperInterfaces();
		}
	}

	static class CtElement_ANNOTATION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.declaration.CtElement, spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> {
		private CtElement_ANNOTATION_RoleHandler() {
			super(spoon.reflect.path.CtRole.ANNOTATION, spoon.reflect.declaration.CtElement.class, spoon.reflect.declaration.CtAnnotation.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getAnnotations();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setAnnotations(castValue(value));
		}
	}

	static class CtStatementList_STATEMENT_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.code.CtStatementList, spoon.reflect.code.CtStatement> {
		private CtStatementList_STATEMENT_RoleHandler() {
			super(spoon.reflect.path.CtRole.STATEMENT, spoon.reflect.code.CtStatementList.class, spoon.reflect.code.CtStatement.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getStatements();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setStatements(castValue(value));
		}
	}

	static class CtAbstractInvocation_ARGUMENT_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.code.CtAbstractInvocation, spoon.reflect.code.CtExpression<?>> {
		private CtAbstractInvocation_ARGUMENT_RoleHandler() {
			super(spoon.reflect.path.CtRole.ARGUMENT, spoon.reflect.code.CtAbstractInvocation.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getArguments();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setArguments(castValue(value));
		}
	}

	static class CtTypeInformation_SUPER_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtTypeInformation, spoon.reflect.reference.CtTypeReference<?>> {
		private CtTypeInformation_SUPER_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.SUPER_TYPE, spoon.reflect.declaration.CtTypeInformation.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getSuperclass();
		}
	}

	static class CtNewClass_NESTED_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtNewClass, spoon.reflect.declaration.CtClass<?>> {
		private CtNewClass_NESTED_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.NESTED_TYPE, spoon.reflect.code.CtNewClass.class, spoon.reflect.declaration.CtClass.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getAnonymousClass();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setAnonymousClass(castValue(value));
		}
	}

	static class CtType_NESTED_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SetHandler<spoon.reflect.declaration.CtType, spoon.reflect.declaration.CtType<?>> {
		private CtType_NESTED_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.NESTED_TYPE, spoon.reflect.declaration.CtType.class, spoon.reflect.declaration.CtType.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getNestedTypes();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setNestedTypes(castValue(value));
		}
	}

	static class CtClass_CONSTRUCTOR_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SetHandler<spoon.reflect.declaration.CtClass, spoon.reflect.declaration.CtConstructor<?>> {
		private CtClass_CONSTRUCTOR_RoleHandler() {
			super(spoon.reflect.path.CtRole.CONSTRUCTOR, spoon.reflect.declaration.CtClass.class, spoon.reflect.declaration.CtConstructor.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getConstructors();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setConstructors(castValue(value));
		}
	}

	static class CtAbstractInvocation_EXECUTABLE_REF_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtAbstractInvocation, spoon.reflect.reference.CtExecutableReference<?>> {
		private CtAbstractInvocation_EXECUTABLE_REF_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXECUTABLE_REF, spoon.reflect.code.CtAbstractInvocation.class, spoon.reflect.reference.CtExecutableReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getExecutable();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setExecutable(castValue(value));
		}
	}

	static class CtExecutableReferenceExpression_EXECUTABLE_REF_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtExecutableReferenceExpression, spoon.reflect.reference.CtExecutableReference<?>> {
		private CtExecutableReferenceExpression_EXECUTABLE_REF_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXECUTABLE_REF, spoon.reflect.code.CtExecutableReferenceExpression.class, spoon.reflect.reference.CtExecutableReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getExecutable();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setExecutable(castValue(value));
		}
	}

	static class CtParameterReference_EXECUTABLE_REF_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.reference.CtParameterReference, spoon.reflect.reference.CtExecutableReference<?>> {
		private CtParameterReference_EXECUTABLE_REF_RoleHandler() {
			super(spoon.reflect.path.CtRole.EXECUTABLE_REF, spoon.reflect.reference.CtParameterReference.class, spoon.reflect.reference.CtExecutableReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getDeclaringExecutable();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setDeclaringExecutable(castValue(value));
		}
	}

	static class CtType_METHOD_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SetHandler<spoon.reflect.declaration.CtType, spoon.reflect.declaration.CtMethod<?>> {
		private CtType_METHOD_RoleHandler() {
			super(spoon.reflect.path.CtRole.METHOD, spoon.reflect.declaration.CtType.class, spoon.reflect.declaration.CtMethod.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getMethods();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setMethods(castValue(value));
		}
	}

	static class CtClass_ANNONYMOUS_EXECUTABLE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.declaration.CtClass, spoon.reflect.declaration.CtAnonymousExecutable> {
		private CtClass_ANNONYMOUS_EXECUTABLE_RoleHandler() {
			super(spoon.reflect.path.CtRole.ANNONYMOUS_EXECUTABLE, spoon.reflect.declaration.CtClass.class, spoon.reflect.declaration.CtAnonymousExecutable.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getAnonymousExecutables();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setAnonymousExecutables(castValue(value));
		}
	}

	static class CtType_FIELD_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.declaration.CtType, spoon.reflect.declaration.CtField<?>> {
		private CtType_FIELD_RoleHandler() {
			super(spoon.reflect.path.CtRole.FIELD, spoon.reflect.declaration.CtType.class, spoon.reflect.declaration.CtField.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getFields();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setFields(castValue(value));
		}
	}

	static class CtExpression_CAST_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.code.CtExpression, spoon.reflect.reference.CtTypeReference<?>> {
		private CtExpression_CAST_RoleHandler() {
			super(spoon.reflect.path.CtRole.CAST, spoon.reflect.code.CtExpression.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getTypeCasts();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setTypeCasts(castValue(value));
		}
	}

	static class CtAnnotation_VALUE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.MapHandler<spoon.reflect.declaration.CtAnnotation, spoon.reflect.code.CtExpression> {
		private CtAnnotation_VALUE_RoleHandler() {
			super(spoon.reflect.path.CtRole.VALUE, spoon.reflect.declaration.CtAnnotation.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getValues();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setValues(castValue(value));
		}
	}

	static class CtEnum_VALUE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.declaration.CtEnum, spoon.reflect.declaration.CtEnumValue<?>> {
		private CtEnum_VALUE_RoleHandler() {
			super(spoon.reflect.path.CtRole.VALUE, spoon.reflect.declaration.CtEnum.class, spoon.reflect.declaration.CtEnumValue.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getEnumValues();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setEnumValues(castValue(value));
		}
	}

	static class CtLiteral_VALUE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtLiteral, java.lang.Object> {
		private CtLiteral_VALUE_RoleHandler() {
			super(spoon.reflect.path.CtRole.VALUE, spoon.reflect.code.CtLiteral.class, java.lang.Object.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getValue();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setValue(castValue(value));
		}
	}

	static class CtFor_FOR_UPDATE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.code.CtFor, spoon.reflect.code.CtStatement> {
		private CtFor_FOR_UPDATE_RoleHandler() {
			super(spoon.reflect.path.CtRole.FOR_UPDATE, spoon.reflect.code.CtFor.class, spoon.reflect.code.CtStatement.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getForUpdate();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setForUpdate(castValue(value));
		}
	}

	static class CtFor_FOR_INIT_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.code.CtFor, spoon.reflect.code.CtStatement> {
		private CtFor_FOR_INIT_RoleHandler() {
			super(spoon.reflect.path.CtRole.FOR_INIT, spoon.reflect.code.CtFor.class, spoon.reflect.code.CtStatement.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getForInit();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setForInit(castValue(value));
		}
	}

	static class CtTryWithResource_TRY_RESOURCE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.code.CtTryWithResource, spoon.reflect.code.CtLocalVariable<?>> {
		private CtTryWithResource_TRY_RESOURCE_RoleHandler() {
			super(spoon.reflect.path.CtRole.TRY_RESOURCE, spoon.reflect.code.CtTryWithResource.class, spoon.reflect.code.CtLocalVariable.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getResources();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setResources(castValue(value));
		}
	}

	static class CtNewArray_DIMENSION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.code.CtNewArray, spoon.reflect.code.CtExpression<java.lang.Integer>> {
		private CtNewArray_DIMENSION_RoleHandler() {
			super(spoon.reflect.path.CtRole.DIMENSION, spoon.reflect.code.CtNewArray.class, spoon.reflect.code.CtExpression.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getDimensionExpressions();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setDimensionExpressions(castValue(value));
		}
	}

	static class CtTry_CATCH_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.code.CtTry, spoon.reflect.code.CtCatch> {
		private CtTry_CATCH_RoleHandler() {
			super(spoon.reflect.path.CtRole.CATCH, spoon.reflect.code.CtTry.class, spoon.reflect.code.CtCatch.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getCatchers();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setCatchers(castValue(value));
		}
	}

	static class CtLabelledFlowBreak_TARGET_LABEL_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtLabelledFlowBreak, java.lang.String> {
		private CtLabelledFlowBreak_TARGET_LABEL_RoleHandler() {
			super(spoon.reflect.path.CtRole.TARGET_LABEL, spoon.reflect.code.CtLabelledFlowBreak.class, java.lang.String.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getTargetLabel();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setTargetLabel(castValue(value));
		}
	}

	static class CtActualTypeContainer_TYPE_PARAMETER_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.reference.CtActualTypeContainer, spoon.reflect.reference.CtTypeReference<?>> {
		private CtActualTypeContainer_TYPE_PARAMETER_RoleHandler() {
			super(spoon.reflect.path.CtRole.TYPE_PARAMETER, spoon.reflect.reference.CtActualTypeContainer.class, spoon.reflect.reference.CtTypeReference.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getActualTypeArguments();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setActualTypeArguments(castValue(value));
		}
	}

	static class CtFormalTypeDeclarer_TYPE_PARAMETER_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.declaration.CtFormalTypeDeclarer, spoon.reflect.declaration.CtTypeParameter> {
		private CtFormalTypeDeclarer_TYPE_PARAMETER_RoleHandler() {
			super(spoon.reflect.path.CtRole.TYPE_PARAMETER, spoon.reflect.declaration.CtFormalTypeDeclarer.class, spoon.reflect.declaration.CtTypeParameter.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getFormalCtTypeParameters();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setFormalCtTypeParameters(castValue(value));
		}
	}

	static class CtJavaDoc_COMMENT_TAG_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.ListHandler<spoon.reflect.code.CtJavaDoc, spoon.reflect.code.CtJavaDocTag> {
		private CtJavaDoc_COMMENT_TAG_RoleHandler() {
			super(spoon.reflect.path.CtRole.COMMENT_TAG, spoon.reflect.code.CtJavaDoc.class, spoon.reflect.code.CtJavaDocTag.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getTags();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setTags(castValue(value));
		}
	}

	static class CtComment_COMMENT_CONTENT_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtComment, java.lang.String> {
		private CtComment_COMMENT_CONTENT_RoleHandler() {
			super(spoon.reflect.path.CtRole.COMMENT_CONTENT, spoon.reflect.code.CtComment.class, java.lang.String.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getContent();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setContent(castValue(value));
		}
	}

	static class CtJavaDocTag_COMMENT_CONTENT_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtJavaDocTag, java.lang.String> {
		private CtJavaDocTag_COMMENT_CONTENT_RoleHandler() {
			super(spoon.reflect.path.CtRole.COMMENT_CONTENT, spoon.reflect.code.CtJavaDocTag.class, java.lang.String.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getContent();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setContent(castValue(value));
		}
	}

	static class CtComment_COMMENT_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtComment, spoon.reflect.code.CtComment.CommentType> {
		private CtComment_COMMENT_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.COMMENT_TYPE, spoon.reflect.code.CtComment.class, spoon.reflect.code.CtComment.CommentType.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getCommentType();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setCommentType(castValue(value));
		}
	}

	static class CtJavaDocTag_DOCUMENTATION_TYPE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtJavaDocTag, spoon.reflect.code.CtJavaDocTag.TagType> {
		private CtJavaDocTag_DOCUMENTATION_TYPE_RoleHandler() {
			super(spoon.reflect.path.CtRole.DOCUMENTATION_TYPE, spoon.reflect.code.CtJavaDocTag.class, spoon.reflect.code.CtJavaDocTag.TagType.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getType();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setType(castValue(value));
		}
	}

	static class CtJavaDocTag_JAVADOC_TAG_VALUE_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.code.CtJavaDocTag, java.lang.String> {
		private CtJavaDocTag_JAVADOC_TAG_VALUE_RoleHandler() {
			super(spoon.reflect.path.CtRole.JAVADOC_TAG_VALUE, spoon.reflect.code.CtJavaDocTag.class, java.lang.String.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getParam();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setParam(castValue(value));
		}
	}

	static class CtElement_POSITION_RoleHandler extends spoon.reflect.meta.impl.AbstractRoleHandler.SingleHandler<spoon.reflect.declaration.CtElement, spoon.reflect.cu.SourcePosition> {
		private CtElement_POSITION_RoleHandler() {
			super(spoon.reflect.path.CtRole.POSITION, spoon.reflect.declaration.CtElement.class, spoon.reflect.cu.SourcePosition.class);
		}

		@java.lang.Override
		public java.lang.Object getValue(spoon.reflect.declaration.CtElement element) {
			return castTarget(element).getPosition();
		}

		@java.lang.Override
		public void setValue(spoon.reflect.declaration.CtElement element, java.lang.Object value) {
			castTarget(element).setPosition(castValue(value));
		}
	}
}

