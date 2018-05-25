package spoon.support;


public class DefaultCoreFactory extends spoon.reflect.factory.SubFactory implements java.io.Serializable , spoon.reflect.factory.CoreFactory {
    private static final long serialVersionUID = 1L;

    public DefaultCoreFactory() {
        super(null);
    }

    public <T extends spoon.reflect.declaration.CtElement> T clone(T object) {
        return spoon.support.visitor.equals.CloneHelper.INSTANCE.clone(object);
    }

    public <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> createAnnotation() {
        spoon.reflect.declaration.CtAnnotation<A> e = new spoon.support.reflect.declaration.CtAnnotationImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotationType<T> createAnnotationType() {
        spoon.reflect.declaration.CtAnnotationType<T> e = new spoon.support.reflect.declaration.CtAnnotationTypeImpl<>();
        e.setFactory(getMainFactory());
        e.setParent(getMainFactory().Package().getRootPackage());
        return e;
    }

    public spoon.reflect.declaration.CtAnonymousExecutable createAnonymousExecutable() {
        spoon.reflect.declaration.CtAnonymousExecutable e = new spoon.support.reflect.declaration.CtAnonymousExecutableImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.code.CtArrayRead<T> createArrayRead() {
        spoon.reflect.code.CtArrayRead<T> e = new spoon.support.reflect.code.CtArrayReadImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.code.CtArrayWrite<T> createArrayWrite() {
        spoon.reflect.code.CtArrayWrite<T> e = new spoon.support.reflect.code.CtArrayWriteImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.reference.CtArrayTypeReference<T> createArrayTypeReference() {
        spoon.reflect.reference.CtArrayTypeReference<T> e = new spoon.support.reflect.reference.CtArrayTypeReferenceImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtAssert<T> createAssert() {
        spoon.reflect.code.CtAssert<T> e = new spoon.support.reflect.code.CtAssertImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T, A extends T> spoon.reflect.code.CtAssignment<T, A> createAssignment() {
        spoon.reflect.code.CtAssignment<T, A> e = new spoon.support.reflect.code.CtAssignmentImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtBinaryOperator<T> createBinaryOperator() {
        spoon.reflect.code.CtBinaryOperator<T> e = new spoon.support.reflect.code.CtBinaryOperatorImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <R> spoon.reflect.code.CtBlock<R> createBlock() {
        spoon.reflect.code.CtBlock<R> e = new spoon.support.reflect.code.CtBlockImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtBreak createBreak() {
        spoon.reflect.code.CtBreak e = new spoon.support.reflect.code.CtBreakImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public <S> spoon.reflect.code.CtCase<S> createCase() {
        spoon.reflect.code.CtCase<S> e = new spoon.support.reflect.code.CtCaseImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtCatch createCatch() {
        spoon.reflect.code.CtCatch e = new spoon.support.reflect.code.CtCatchImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.declaration.CtClass<T> createClass() {
        spoon.reflect.declaration.CtClass<T> e = new spoon.support.reflect.declaration.CtClassImpl<>();
        e.setFactory(getMainFactory());
        e.setParent(getMainFactory().Package().getRootPackage());
        return e;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtTypeParameter createTypeParameter() {
        spoon.reflect.declaration.CtTypeParameter e = new spoon.support.reflect.declaration.CtTypeParameterImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtConditional<T> createConditional() {
        spoon.reflect.code.CtConditional<T> e = new spoon.support.reflect.code.CtConditionalImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.declaration.CtConstructor<T> createConstructor() {
        spoon.reflect.declaration.CtConstructor<T> e = new spoon.support.reflect.declaration.CtConstructorImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtContinue createContinue() {
        spoon.reflect.code.CtContinue e = new spoon.support.reflect.code.CtContinueImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtDo createDo() {
        spoon.reflect.code.CtDo e = new spoon.support.reflect.code.CtDoImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T extends java.lang.Enum<?>> spoon.reflect.declaration.CtEnum<T> createEnum() {
        spoon.reflect.declaration.CtEnum<T> e = new spoon.support.reflect.declaration.CtEnumImpl<>();
        e.setFactory(getMainFactory());
        e.setParent(getMainFactory().Package().getRootPackage());
        return e;
    }

    public <T> spoon.reflect.reference.CtExecutableReference<T> createExecutableReference() {
        spoon.reflect.reference.CtExecutableReference<T> e = new spoon.support.reflect.reference.CtExecutableReferenceImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.declaration.CtField<T> createField() {
        spoon.reflect.declaration.CtField<T> e = new spoon.support.reflect.declaration.CtFieldImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.declaration.CtEnumValue<T> createEnumValue() {
        spoon.reflect.declaration.CtEnumValue<T> e = new spoon.support.reflect.declaration.CtEnumValueImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.code.CtFieldRead<T> createFieldRead() {
        spoon.reflect.code.CtFieldRead<T> e = new spoon.support.reflect.code.CtFieldReadImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.code.CtFieldWrite<T> createFieldWrite() {
        spoon.reflect.code.CtFieldWrite<T> e = new spoon.support.reflect.code.CtFieldWriteImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtAnnotationFieldAccess<T> createAnnotationFieldAccess() {
        spoon.reflect.code.CtAnnotationFieldAccess<T> e = new spoon.support.reflect.code.CtAnnotationFieldAccessImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.reference.CtUnboundVariableReference<T> createUnboundVariableReference() {
        spoon.reflect.reference.CtUnboundVariableReference e = new spoon.support.reflect.reference.CtUnboundVariableReferenceImpl<T>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.reference.CtFieldReference<T> createFieldReference() {
        spoon.reflect.reference.CtFieldReference<T> e = new spoon.support.reflect.reference.CtFieldReferenceImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtFor createFor() {
        spoon.reflect.code.CtFor e = new spoon.support.reflect.code.CtForImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtForEach createForEach() {
        spoon.reflect.code.CtForEach e = new spoon.support.reflect.code.CtForEachImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtIf createIf() {
        spoon.reflect.code.CtIf e = new spoon.support.reflect.code.CtIfImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.declaration.CtInterface<T> createInterface() {
        spoon.reflect.declaration.CtInterface<T> e = new spoon.support.reflect.declaration.CtInterfaceImpl<>();
        e.setFactory(getMainFactory());
        e.setParent(getMainFactory().Package().getRootPackage());
        return e;
    }

    public <T> spoon.reflect.code.CtInvocation<T> createInvocation() {
        spoon.reflect.code.CtInvocation<T> e = new spoon.support.reflect.code.CtInvocationImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtLiteral<T> createLiteral() {
        spoon.reflect.code.CtLiteral<T> e = new spoon.support.reflect.code.CtLiteralImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtLocalVariable<T> createLocalVariable() {
        spoon.reflect.code.CtLocalVariable<T> e = new spoon.support.reflect.code.CtLocalVariableImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.reference.CtLocalVariableReference<T> createLocalVariableReference() {
        spoon.reflect.reference.CtLocalVariableReference<T> e = new spoon.support.reflect.reference.CtLocalVariableReferenceImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.code.CtCatchVariable<T> createCatchVariable() {
        spoon.reflect.code.CtCatchVariable<T> e = new spoon.support.reflect.code.CtCatchVariableImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.reference.CtCatchVariableReference<T> createCatchVariableReference() {
        spoon.reflect.reference.CtCatchVariableReference<T> e = new spoon.support.reflect.reference.CtCatchVariableReferenceImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.declaration.CtMethod<T> createMethod() {
        spoon.reflect.declaration.CtMethod<T> e = new spoon.support.reflect.declaration.CtMethodImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.declaration.CtAnnotationMethod<T> createAnnotationMethod() {
        spoon.reflect.declaration.CtAnnotationMethod<T> e = new spoon.support.reflect.declaration.CtAnnotationMethodImpl<T>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtNewArray<T> createNewArray() {
        spoon.reflect.code.CtNewArray<T> e = new spoon.support.reflect.code.CtNewArrayImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.code.CtConstructorCall<T> createConstructorCall() {
        spoon.reflect.code.CtConstructorCall<T> e = new spoon.support.reflect.code.CtConstructorCallImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtNewClass<T> createNewClass() {
        spoon.reflect.code.CtNewClass<T> e = new spoon.support.reflect.code.CtNewClassImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.code.CtLambda<T> createLambda() {
        spoon.reflect.code.CtLambda<T> e = new spoon.support.reflect.code.CtLambdaImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T, E extends spoon.reflect.code.CtExpression<?>> spoon.reflect.code.CtExecutableReferenceExpression<T, E> createExecutableReferenceExpression() {
        spoon.reflect.code.CtExecutableReferenceExpression<T, E> e = new spoon.support.reflect.code.CtExecutableReferenceExpressionImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T, A extends T> spoon.reflect.code.CtOperatorAssignment<T, A> createOperatorAssignment() {
        spoon.reflect.code.CtOperatorAssignment<T, A> e = new spoon.support.reflect.code.CtOperatorAssignmentImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.declaration.CtPackage createPackage() {
        spoon.reflect.declaration.CtPackage e = new spoon.support.reflect.declaration.CtPackageImpl();
        e.setFactory(getMainFactory());
        e.setParent(getMainFactory().Package().getRootPackage());
        return e;
    }

    public spoon.reflect.reference.CtPackageReference createPackageReference() {
        spoon.reflect.reference.CtPackageReference e = new spoon.support.reflect.reference.CtPackageReferenceImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.declaration.CtParameter<T> createParameter() {
        spoon.reflect.declaration.CtParameter<T> e = new spoon.support.reflect.declaration.CtParameterImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.reference.CtParameterReference<T> createParameterReference() {
        spoon.reflect.reference.CtParameterReference<T> e = new spoon.support.reflect.reference.CtParameterReferenceImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <R> spoon.reflect.code.CtReturn<R> createReturn() {
        spoon.reflect.code.CtReturn<R> e = new spoon.support.reflect.code.CtReturnImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <R> spoon.reflect.code.CtStatementList createStatementList() {
        spoon.reflect.code.CtStatementList e = new spoon.support.reflect.code.CtStatementListImpl<R>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <S> spoon.reflect.code.CtSwitch<S> createSwitch() {
        spoon.reflect.code.CtSwitch<S> e = new spoon.support.reflect.code.CtSwitchImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtSynchronized createSynchronized() {
        spoon.reflect.code.CtSynchronized e = new spoon.support.reflect.code.CtSynchronizedImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtThrow createThrow() {
        spoon.reflect.code.CtThrow e = new spoon.support.reflect.code.CtThrowImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtTry createTry() {
        spoon.reflect.code.CtTry e = new spoon.support.reflect.code.CtTryImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public spoon.reflect.code.CtTryWithResource createTryWithResource() {
        spoon.reflect.code.CtTryWithResource e = new spoon.support.reflect.code.CtTryWithResourceImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.reference.CtTypeParameterReference createTypeParameterReference() {
        spoon.reflect.reference.CtTypeParameterReference e = new spoon.support.reflect.reference.CtTypeParameterReferenceImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtWildcardReference createWildcardReference() {
        spoon.reflect.reference.CtWildcardReference e = new spoon.support.reflect.reference.CtWildcardReferenceImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.reference.CtIntersectionTypeReference<T> createIntersectionTypeReference() {
        spoon.reflect.reference.CtIntersectionTypeReference<T> e = new spoon.support.reflect.reference.CtIntersectionTypeReferenceImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.reference.CtTypeReference<T> createTypeReference() {
        spoon.reflect.reference.CtTypeReference<T> e = new spoon.support.reflect.reference.CtTypeReferenceImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public <T> spoon.reflect.code.CtTypeAccess<T> createTypeAccess() {
        spoon.reflect.code.CtTypeAccess<T> e = new spoon.support.reflect.code.CtTypeAccessImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtUnaryOperator<T> createUnaryOperator() {
        spoon.reflect.code.CtUnaryOperator<T> e = new spoon.support.reflect.code.CtUnaryOperatorImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtVariableRead<T> createVariableRead() {
        spoon.reflect.code.CtVariableRead<T> e = new spoon.support.reflect.code.CtVariableReadImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtVariableWrite<T> createVariableWrite() {
        spoon.reflect.code.CtVariableWrite<T> e = new spoon.support.reflect.code.CtVariableWriteImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtCodeSnippetExpression<T> createCodeSnippetExpression() {
        spoon.reflect.code.CtCodeSnippetExpression<T> e = new spoon.support.reflect.code.CtCodeSnippetExpressionImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtCodeSnippetStatement createCodeSnippetStatement() {
        spoon.reflect.code.CtCodeSnippetStatement e = new spoon.support.reflect.code.CtCodeSnippetStatementImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtComment createComment() {
        spoon.reflect.code.CtComment e = new spoon.support.reflect.code.CtCommentImpl();
        e.setFactory(getMainFactory());
        e.setCommentType(spoon.reflect.code.CtComment.CommentType.BLOCK);
        e.setContent("");
        return e;
    }

    @java.lang.Override
    public spoon.reflect.code.CtJavaDoc createJavaDoc() {
        spoon.reflect.code.CtJavaDoc e = new spoon.support.reflect.code.CtJavaDocImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    @java.lang.Override
    public spoon.reflect.code.CtJavaDocTag createJavaDocTag() {
        spoon.reflect.code.CtJavaDocTag e = new spoon.support.reflect.code.CtJavaDocTagImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.code.CtWhile createWhile() {
        spoon.reflect.code.CtWhile e = new spoon.support.reflect.code.CtWhileImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.declaration.CtImport createImport() {
        spoon.reflect.declaration.CtImport e = new spoon.support.reflect.declaration.CtImportImpl();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.factory.Factory getMainFactory() {
        return factory;
    }

    public void setMainFactory(spoon.reflect.factory.Factory mainFactory) {
        this.factory = mainFactory;
    }

    @java.lang.Override
    public spoon.reflect.cu.SourcePosition createSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit, int startSource, int end, int[] lineSeparatorPositions) {
        return new spoon.support.reflect.cu.position.SourcePositionImpl(compilationUnit, startSource, end, lineSeparatorPositions);
    }

    @java.lang.Override
    public spoon.reflect.cu.SourcePosition createPartialSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit) {
        return ((spoon.support.reflect.cu.CompilationUnitImpl) (compilationUnit)).getOrCreatePartialSourcePosition();
    }

    @java.lang.Override
    public spoon.reflect.cu.position.DeclarationSourcePosition createDeclarationSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit, int startSource, int end, int modifierStart, int modifierEnd, int declarationStart, int declarationEnd, int[] lineSeparatorPositions) {
        return new spoon.support.reflect.cu.position.DeclarationSourcePositionImpl(compilationUnit, startSource, end, modifierStart, modifierEnd, declarationStart, declarationEnd, lineSeparatorPositions);
    }

    @java.lang.Override
    public spoon.reflect.cu.position.BodyHolderSourcePosition createBodyHolderSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit, int startSource, int end, int modifierStart, int modifierEnd, int declarationStart, int declarationEnd, int bodyStart, int bodyEnd, int[] lineSeparatorPositions) {
        return new spoon.support.reflect.cu.position.BodyHolderSourcePositionImpl(compilationUnit, startSource, end, modifierStart, modifierEnd, declarationStart, declarationEnd, bodyStart, bodyEnd, lineSeparatorPositions);
    }

    public spoon.reflect.cu.CompilationUnit createCompilationUnit() {
        spoon.reflect.cu.CompilationUnit cu = new spoon.support.reflect.cu.CompilationUnitImpl();
        cu.setFactory(getMainFactory());
        return cu;
    }

    public <T> spoon.reflect.code.CtThisAccess<T> createThisAccess() {
        spoon.reflect.code.CtThisAccess<T> e = new spoon.support.reflect.code.CtThisAccessImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public <T> spoon.reflect.code.CtSuperAccess<T> createSuperAccess() {
        spoon.reflect.code.CtSuperAccess<T> e = new spoon.support.reflect.code.CtSuperAccessImpl<>();
        e.setFactory(getMainFactory());
        return e;
    }

    public spoon.reflect.declaration.CtElement create(java.lang.Class<? extends spoon.reflect.declaration.CtElement> klass) {
        if (klass.equals(spoon.reflect.code.CtAnnotationFieldAccess.class)) {
            return createAnnotationFieldAccess();
        }
        if (klass.equals(spoon.reflect.code.CtArrayRead.class)) {
            return createArrayRead();
        }
        if (klass.equals(spoon.reflect.code.CtArrayWrite.class)) {
            return createArrayWrite();
        }
        if (klass.equals(spoon.reflect.code.CtAssert.class)) {
            return createAssert();
        }
        if (klass.equals(spoon.reflect.code.CtAssignment.class)) {
            return createAssignment();
        }
        if (klass.equals(spoon.reflect.code.CtBinaryOperator.class)) {
            return createBinaryOperator();
        }
        if (klass.equals(spoon.reflect.code.CtBlock.class)) {
            return createBlock();
        }
        if (klass.equals(spoon.reflect.code.CtBreak.class)) {
            return createBreak();
        }
        if (klass.equals(spoon.reflect.code.CtCase.class)) {
            return createCase();
        }
        if (klass.equals(spoon.reflect.code.CtCatch.class)) {
            return createCatch();
        }
        if (klass.equals(spoon.reflect.code.CtCatchVariable.class)) {
            return createCatchVariable();
        }
        if (klass.equals(spoon.reflect.code.CtCodeSnippetExpression.class)) {
            return createCodeSnippetExpression();
        }
        if (klass.equals(spoon.reflect.code.CtCodeSnippetStatement.class)) {
            return createCodeSnippetStatement();
        }
        if (klass.equals(spoon.reflect.code.CtComment.class)) {
            return createComment();
        }
        if (klass.equals(spoon.reflect.code.CtJavaDoc.class)) {
            return createJavaDoc();
        }
        if (klass.equals(spoon.reflect.code.CtJavaDocTag.class)) {
            return createJavaDocTag();
        }
        if (klass.equals(spoon.reflect.code.CtConditional.class)) {
            return createConditional();
        }
        if (klass.equals(spoon.reflect.code.CtConstructorCall.class)) {
            return createConstructorCall();
        }
        if (klass.equals(spoon.reflect.code.CtContinue.class)) {
            return createContinue();
        }
        if (klass.equals(spoon.reflect.code.CtDo.class)) {
            return createDo();
        }
        if (klass.equals(spoon.reflect.code.CtExecutableReferenceExpression.class)) {
            return createExecutableReferenceExpression();
        }
        if (klass.equals(spoon.reflect.code.CtFieldRead.class)) {
            return createFieldRead();
        }
        if (klass.equals(spoon.reflect.code.CtFieldWrite.class)) {
            return createFieldWrite();
        }
        if (klass.equals(spoon.reflect.code.CtForEach.class)) {
            return createForEach();
        }
        if (klass.equals(spoon.reflect.code.CtFor.class)) {
            return createFor();
        }
        if (klass.equals(spoon.reflect.code.CtIf.class)) {
            return createIf();
        }
        if (klass.equals(spoon.reflect.code.CtInvocation.class)) {
            return createInvocation();
        }
        if (klass.equals(spoon.reflect.code.CtLambda.class)) {
            return createLambda();
        }
        if (klass.equals(spoon.reflect.code.CtLiteral.class)) {
            return createLiteral();
        }
        if (klass.equals(spoon.reflect.code.CtLocalVariable.class)) {
            return createLocalVariable();
        }
        if (klass.equals(spoon.reflect.code.CtNewArray.class)) {
            return createNewArray();
        }
        if (klass.equals(spoon.reflect.code.CtNewClass.class)) {
            return createNewClass();
        }
        if (klass.equals(spoon.reflect.code.CtOperatorAssignment.class)) {
            return createOperatorAssignment();
        }
        if (klass.equals(spoon.reflect.code.CtReturn.class)) {
            return createReturn();
        }
        if (klass.equals(spoon.reflect.code.CtStatementList.class)) {
            return createStatementList();
        }
        if (klass.equals(spoon.reflect.code.CtSuperAccess.class)) {
            return createSuperAccess();
        }
        if (klass.equals(spoon.reflect.code.CtSwitch.class)) {
            return createSwitch();
        }
        if (klass.equals(spoon.reflect.code.CtSynchronized.class)) {
            return createSynchronized();
        }
        if (klass.equals(spoon.reflect.code.CtThisAccess.class)) {
            return createThisAccess();
        }
        if (klass.equals(spoon.reflect.code.CtThrow.class)) {
            return createThrow();
        }
        if (klass.equals(spoon.reflect.code.CtTry.class)) {
            return createTry();
        }
        if (klass.equals(spoon.reflect.code.CtTryWithResource.class)) {
            return createTryWithResource();
        }
        if (klass.equals(spoon.reflect.code.CtTypeAccess.class)) {
            return createTypeAccess();
        }
        if (klass.equals(spoon.reflect.code.CtUnaryOperator.class)) {
            return createUnaryOperator();
        }
        if (klass.equals(spoon.reflect.code.CtVariableRead.class)) {
            return createVariableRead();
        }
        if (klass.equals(spoon.reflect.code.CtVariableWrite.class)) {
            return createVariableWrite();
        }
        if (klass.equals(spoon.reflect.code.CtWhile.class)) {
            return createWhile();
        }
        if (klass.equals(spoon.reflect.declaration.CtAnnotation.class)) {
            return createAnnotation();
        }
        if (klass.equals(spoon.reflect.declaration.CtAnnotationMethod.class)) {
            return createAnnotationMethod();
        }
        if (klass.equals(spoon.reflect.declaration.CtAnnotationType.class)) {
            return createAnnotationType();
        }
        if (klass.equals(spoon.reflect.declaration.CtAnonymousExecutable.class)) {
            return createAnonymousExecutable();
        }
        if (klass.equals(spoon.reflect.declaration.CtClass.class)) {
            return createClass();
        }
        if (klass.equals(spoon.reflect.declaration.CtConstructor.class)) {
            return createConstructor();
        }
        if (klass.equals(spoon.reflect.declaration.CtEnum.class)) {
            return createEnum();
        }
        if (klass.equals(spoon.reflect.declaration.CtEnumValue.class)) {
            return createEnumValue();
        }
        if (klass.equals(spoon.reflect.declaration.CtField.class)) {
            return createField();
        }
        if (klass.equals(spoon.reflect.declaration.CtInterface.class)) {
            return createInterface();
        }
        if (klass.equals(spoon.reflect.declaration.CtMethod.class)) {
            return createMethod();
        }
        if (klass.equals(spoon.reflect.declaration.CtPackage.class)) {
            return createPackage();
        }
        if (klass.equals(spoon.reflect.declaration.CtParameter.class)) {
            return createParameter();
        }
        if (klass.equals(spoon.reflect.declaration.CtTypeParameter.class)) {
            return createTypeParameter();
        }
        if (klass.equals(spoon.reflect.reference.CtArrayTypeReference.class)) {
            return createArrayTypeReference();
        }
        if (klass.equals(spoon.reflect.reference.CtCatchVariableReference.class)) {
            return createCatchVariableReference();
        }
        if (klass.equals(spoon.reflect.reference.CtExecutableReference.class)) {
            return createExecutableReference();
        }
        if (klass.equals(spoon.reflect.reference.CtFieldReference.class)) {
            return createFieldReference();
        }
        if (klass.equals(spoon.reflect.reference.CtIntersectionTypeReference.class)) {
            return createIntersectionTypeReference();
        }
        if (klass.equals(spoon.reflect.reference.CtLocalVariableReference.class)) {
            return createLocalVariableReference();
        }
        if (klass.equals(spoon.reflect.reference.CtPackageReference.class)) {
            return createPackageReference();
        }
        if (klass.equals(spoon.reflect.reference.CtParameterReference.class)) {
            return createParameterReference();
        }
        if (klass.equals(spoon.reflect.reference.CtTypeParameterReference.class)) {
            return createTypeParameterReference();
        }
        if (klass.equals(spoon.reflect.reference.CtTypeReference.class)) {
            return createTypeReference();
        }
        if (klass.equals(spoon.reflect.reference.CtUnboundVariableReference.class)) {
            return createUnboundVariableReference();
        }
        if (klass.equals(spoon.reflect.reference.CtWildcardReference.class)) {
            return createWildcardReference();
        }
        if (klass.equals(spoon.reflect.declaration.CtImport.class)) {
            return createImport();
        }
        if (klass.equals(spoon.reflect.reference.CtModuleReference.class)) {
            return createModuleReference();
        }
        if (klass.equals(spoon.reflect.declaration.CtModule.class)) {
            return createModule();
        }
        if (klass.equals(spoon.reflect.declaration.CtModuleRequirement.class)) {
            return createModuleRequirement();
        }
        if (klass.equals(spoon.reflect.declaration.CtPackageExport.class)) {
            return createPackageExport();
        }
        if (klass.equals(spoon.reflect.declaration.CtProvidedService.class)) {
            return createProvidedService();
        }
        if (klass.equals(spoon.reflect.declaration.CtUsedService.class)) {
            return createUsedService();
        }
        throw new java.lang.IllegalArgumentException(("not instantiable by CoreFactory(): " + klass));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference createWildcardStaticTypeMemberReference() {
        spoon.reflect.reference.CtTypeReference result = new spoon.support.reflect.reference.CtWildcardStaticTypeMemberReferenceImpl();
        result.setFactory(getMainFactory());
        return result;
    }

    public spoon.reflect.declaration.CtModule createModule() {
        spoon.reflect.declaration.CtModule module = new spoon.support.reflect.declaration.CtModuleImpl();
        module.setFactory(getMainFactory());
        this.getMainFactory().Module().getUnnamedModule().addModule(module);
        return module;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtModuleReference createModuleReference() {
        spoon.reflect.reference.CtModuleReference moduleReference = new spoon.support.reflect.reference.CtModuleReferenceImpl();
        moduleReference.setFactory(getMainFactory());
        return moduleReference;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtModuleRequirement createModuleRequirement() {
        spoon.reflect.declaration.CtModuleRequirement moduleRequirement = new spoon.support.reflect.declaration.CtModuleRequirementImpl();
        moduleRequirement.setFactory(getMainFactory());
        return moduleRequirement;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtPackageExport createPackageExport() {
        spoon.reflect.declaration.CtPackageExport moduleExport = new spoon.support.reflect.declaration.CtPackageExportImpl();
        moduleExport.setFactory(getMainFactory());
        return moduleExport;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtProvidedService createProvidedService() {
        spoon.reflect.declaration.CtProvidedService moduleProvidedService = new spoon.support.reflect.declaration.CtProvidedServiceImpl();
        moduleProvidedService.setFactory(getMainFactory());
        return moduleProvidedService;
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtUsedService createUsedService() {
        spoon.reflect.declaration.CtUsedService ctUsedService = new spoon.support.reflect.declaration.CtUsedServiceImpl();
        ctUsedService.setFactory(getMainFactory());
        return ctUsedService;
    }
}

