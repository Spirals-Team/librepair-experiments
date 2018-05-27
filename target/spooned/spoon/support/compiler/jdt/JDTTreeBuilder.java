package spoon.support.compiler.jdt;


public class JDTTreeBuilder extends org.eclipse.jdt.internal.compiler.ASTVisitor {
    private final spoon.support.compiler.jdt.PositionBuilder position;

    private final spoon.support.compiler.jdt.ContextBuilder context;

    private final spoon.support.compiler.jdt.ParentExiter exiter;

    final spoon.support.compiler.jdt.ReferenceBuilder references;

    private final spoon.support.compiler.jdt.JDTTreeBuilderHelper helper;

    private final spoon.reflect.factory.Factory factory;

    boolean skipTypeInAnnotation = false;

    public static org.apache.log4j.Logger getLogger() {
        return spoon.support.compiler.jdt.JDTTreeBuilder.LOGGER;
    }

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(spoon.support.compiler.jdt.JDTTreeBuilder.class);

    public spoon.support.compiler.jdt.PositionBuilder getPositionBuilder() {
        return position;
    }

    public spoon.support.compiler.jdt.ContextBuilder getContextBuilder() {
        return context;
    }

    public spoon.support.compiler.jdt.ReferenceBuilder getReferencesBuilder() {
        return references;
    }

    public spoon.support.compiler.jdt.JDTTreeBuilderHelper getHelper() {
        return helper;
    }

    public spoon.support.compiler.jdt.ParentExiter getExiter() {
        return exiter;
    }

    public spoon.reflect.factory.Factory getFactory() {
        return factory;
    }

    public JDTTreeBuilder(spoon.reflect.factory.Factory factory) {
        super();
        this.factory = factory;
        this.position = new spoon.support.compiler.jdt.PositionBuilder(this);
        this.context = new spoon.support.compiler.jdt.ContextBuilder(this);
        this.exiter = new spoon.support.compiler.jdt.ParentExiter(this);
        this.references = new spoon.support.compiler.jdt.ReferenceBuilder(this);
        this.helper = new spoon.support.compiler.jdt.JDTTreeBuilderHelper(this);
        spoon.support.compiler.jdt.JDTTreeBuilder.LOGGER.setLevel(factory.getEnvironment().getLevel());
    }

    static abstract class OnAccessListener {
        abstract boolean onAccess(char[][] tokens, int index);
    }

    class SpoonReferenceBinding extends org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding {
        private org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding enclosingType;

        SpoonReferenceBinding(char[] sourceName, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding enclosingType) {
            this.sourceName = sourceName;
            this.enclosingType = enclosingType;
        }

        @java.lang.Override
        public org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding enclosingType() {
            return enclosingType;
        }
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.AllocationExpression allocationExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(allocationExpression);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.AND_AND_Expression and_and_Expression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(and_and_Expression);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration annotationTypeDeclaration, org.eclipse.jdt.internal.compiler.lookup.ClassScope classScope) {
        context.exit(annotationTypeDeclaration);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.Argument argument, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(argument);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression arrayAllocationExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(arrayAllocationExpression);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ArrayInitializer arrayInitializer, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(arrayInitializer);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ArrayReference arrayReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(arrayReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference arrayTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(arrayTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference arrayTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        context.exit(arrayTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference arrayQualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(arrayQualifiedTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference arrayQualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        context.exit(arrayQualifiedTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.AssertStatement assertStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(assertStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.Assignment assignment, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(assignment);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.BinaryExpression binaryExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(binaryExpression);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.Block block, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(block);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.BreakStatement breakStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(breakStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.CaseStatement caseStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.CharLiteral charLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(charLiteral);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ClassLiteralAccess classLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(classLiteral);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.CompoundAssignment compoundAssignment, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(compoundAssignment);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ConditionalExpression conditionalExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(conditionalExpression);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration constructorDeclaration, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        context.exit(constructorDeclaration);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ContinueStatement continueStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(continueStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.DoStatement doStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(doStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.DoubleLiteral doubleLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(doubleLiteral);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.EqualExpression equalExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(equalExpression);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ExplicitConstructorCall explicitConstructor, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(explicitConstructor);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ExtendedStringLiteral extendedStringLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(extendedStringLiteral);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.FalseLiteral falseLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(falseLiteral);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.FieldDeclaration fieldDeclaration, org.eclipse.jdt.internal.compiler.lookup.MethodScope scope) {
        context.exit(fieldDeclaration);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.FieldReference fieldReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(fieldReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.FloatLiteral floatLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(floatLiteral);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ForeachStatement forStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(forStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ForStatement forStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(forStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.IfStatement ifStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(ifStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.Initializer initializer, org.eclipse.jdt.internal.compiler.lookup.MethodScope scope) {
        context.exit(initializer);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.InstanceOfExpression instanceOfExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(instanceOfExpression);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.IntLiteral intLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(intLiteral);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.LocalDeclaration localDeclaration, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(localDeclaration);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.LongLiteral longLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(longLiteral);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.NormalAnnotation annotation, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        context.exit(annotation);
        skipTypeInAnnotation = false;
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.MarkerAnnotation annotation, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        context.exit(annotation);
        skipTypeInAnnotation = false;
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.MarkerAnnotation annotation, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(annotation);
        skipTypeInAnnotation = false;
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.MemberValuePair pair, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        if (!(context.annotationValueName.pop().equals(new java.lang.String(pair.name)))) {
            throw new java.lang.RuntimeException("Unconsistant Stack");
        }
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.MemberValuePair pair, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if (!(context.annotationValueName.pop().equals(new java.lang.String(pair.name)))) {
            throw new java.lang.RuntimeException("Unconsistant Stack");
        }
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.MessageSend messageSend, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(messageSend);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.MethodDeclaration methodDeclaration, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        context.exit(methodDeclaration);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.NormalAnnotation annotation, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(annotation);
        skipTypeInAnnotation = false;
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.NullLiteral nullLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(nullLiteral);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.OR_OR_Expression or_or_Expression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(or_or_Expression);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        if (skipTypeInAnnotation) {
            skipTypeInAnnotation = false;
            return;
        }
        context.exit(parameterizedQualifiedTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if (skipTypeInAnnotation) {
            skipTypeInAnnotation = false;
            return;
        }
        context.exit(parameterizedQualifiedTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ParameterizedSingleTypeReference parameterizedSingleTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if (skipTypeInAnnotation) {
            skipTypeInAnnotation = false;
            return;
        }
        context.exit(parameterizedSingleTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ParameterizedSingleTypeReference parameterizedSingleTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        if (skipTypeInAnnotation) {
            skipTypeInAnnotation = false;
            return;
        }
        context.exit(parameterizedSingleTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.PostfixExpression postfixExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(postfixExpression);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.PrefixExpression prefixExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(prefixExpression);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression qualifiedAllocationExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        endVisit(((org.eclipse.jdt.internal.compiler.ast.AllocationExpression) (qualifiedAllocationExpression)), scope);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference qualifiedNameReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if ((context.stack.peek().node) == qualifiedNameReference) {
            context.exit(qualifiedNameReference);
        }
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.QualifiedThisReference qualifiedThisReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        endVisit(((org.eclipse.jdt.internal.compiler.ast.ThisReference) (qualifiedThisReference)), scope);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference qualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if (skipTypeInAnnotation) {
            skipTypeInAnnotation = false;
            return;
        }
        context.exit(qualifiedTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference qualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        endVisit(qualifiedTypeReference, ((org.eclipse.jdt.internal.compiler.lookup.BlockScope) (null)));
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ReturnStatement returnStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(returnStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.SingleMemberAnnotation annotation, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if (!(context.annotationValueName.pop().equals("value"))) {
            throw new java.lang.RuntimeException("unconsistant Stack");
        }
        context.exit(annotation);
        skipTypeInAnnotation = false;
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.SingleNameReference singleNameReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if ((context.stack.peek().node) == singleNameReference) {
            context.exit(singleNameReference);
        }
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.SingleTypeReference singleTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if (skipTypeInAnnotation) {
            skipTypeInAnnotation = false;
            return;
        }
        context.exit(singleTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.SingleTypeReference singleTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        if (skipTypeInAnnotation) {
            skipTypeInAnnotation = false;
            return;
        }
        context.exit(singleTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.StringLiteral stringLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(stringLiteral);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.StringLiteralConcatenation literal, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(literal);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.QualifiedSuperReference qualifiedsuperReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(qualifiedsuperReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.SuperReference superReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(superReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.QualifiedThisReference qualifiedThisReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        super.endVisit(qualifiedThisReference, scope);
        context.exit(qualifiedThisReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ThisReference thisReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(thisReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.SwitchStatement switchStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if ((context.stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.CaseStatement) {
            context.exit(context.stack.peek().node);
        }
        context.exit(switchStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.SynchronizedStatement synchronizedStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(synchronizedStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ThrowStatement throwStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(throwStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.TrueLiteral trueLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(trueLiteral);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.TryStatement tryStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(tryStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.TypeParameter typeParameter, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(typeParameter);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.TypeParameter typeParameter, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        context.exit(typeParameter);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.TypeDeclaration localTypeDeclaration, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(localTypeDeclaration);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.TypeDeclaration memberTypeDeclaration, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        while ((!(context.stack.isEmpty())) && ((context.stack.peek().node) == memberTypeDeclaration)) {
            context.exit(memberTypeDeclaration);
        } 
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.TypeDeclaration typeDeclaration, org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope scope) {
        while ((!(context.stack.isEmpty())) && ((context.stack.peek().node) == typeDeclaration)) {
            context.exit(typeDeclaration);
        } 
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.UnaryExpression unaryExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(unaryExpression);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.WhileStatement whileStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(whileStatement);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration compilationUnitDeclaration, org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope scope) {
        context.compilationunitdeclaration = null;
        context.compilationUnitSpoon = null;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.Javadoc javadoc, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        return false;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.Javadoc javadoc, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        return false;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration compilationUnitDeclaration, org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope scope) {
        context.compilationunitdeclaration = scope.referenceContext;
        context.compilationUnitSpoon = getFactory().CompilationUnit().getOrCreate(new java.lang.String(context.compilationunitdeclaration.getFileName()));
        context.compilationUnitSpoon.setDeclaredPackage(getFactory().Package().getOrCreate(org.eclipse.jdt.core.compiler.CharOperation.toString(scope.currentPackageName)));
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ReferenceExpression referenceExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope blockScope) {
        context.enter(helper.createExecutableReferenceExpression(referenceExpression), referenceExpression);
        return true;
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.ReferenceExpression referenceExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope blockScope) {
        context.exit(referenceExpression);
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.LambdaExpression lambdaExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope blockScope) {
        spoon.reflect.code.CtLambda<?> lambda = factory.Core().createLambda();
        final org.eclipse.jdt.internal.compiler.lookup.MethodBinding methodBinding = lambdaExpression.getMethodBinding();
        if (methodBinding != null) {
            lambda.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(methodBinding.constantPoolName()));
        }
        context.isBuildLambda = true;
        context.enter(lambda, lambdaExpression);
        return true;
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.LambdaExpression lambdaExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope blockScope) {
        context.isBuildLambda = false;
        context.exit(lambdaExpression);
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.AllocationExpression allocationExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtConstructorCall constructorCall = factory.Core().createConstructorCall();
        constructorCall.setExecutable(references.getExecutableReference(allocationExpression));
        context.enter(constructorCall, allocationExpression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression qualifiedAllocationExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtConstructorCall constructorCall;
        if ((qualifiedAllocationExpression.anonymousType) != null) {
            constructorCall = factory.Core().createNewClass();
        }else {
            constructorCall = factory.Core().createConstructorCall();
        }
        constructorCall.setExecutable(references.getExecutableReference(qualifiedAllocationExpression));
        context.enter(constructorCall, qualifiedAllocationExpression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.AND_AND_Expression and_and_Expression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtBinaryOperator<?> op = factory.Core().createBinaryOperator();
        op.setKind(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getBinaryOperatorKind((((and_and_Expression.bits) & (org.eclipse.jdt.internal.compiler.ast.ASTNode.OperatorMASK)) >> (org.eclipse.jdt.internal.compiler.ast.ASTNode.OperatorSHIFT))));
        context.enter(op, and_and_Expression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration annotationTypeDeclaration, org.eclipse.jdt.internal.compiler.lookup.ClassScope classScope) {
        spoon.reflect.declaration.CtAnnotationMethod<java.lang.Object> ctAnnotationMethod = factory.Core().createAnnotationMethod();
        ctAnnotationMethod.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(annotationTypeDeclaration.selector));
        context.enter(ctAnnotationMethod, annotationTypeDeclaration);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.Argument argument, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if ((this.getContextBuilder().stack.peekFirst().element) instanceof spoon.reflect.code.CtTry) {
            context.enter(factory.Core().createCatch(), argument);
            return true;
        }
        context.enter(helper.createParameter(argument), argument);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression arrayAllocationExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createNewArray(), arrayAllocationExpression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ArrayInitializer arrayInitializer, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createNewArray(), arrayInitializer);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ArrayReference arrayReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtArrayAccess<?, ?> a;
        if (spoon.support.compiler.jdt.JDTTreeBuilderQuery.isLhsAssignment(context, arrayReference)) {
            a = factory.Core().createArrayWrite();
        }else {
            a = factory.Core().createArrayRead();
        }
        context.enter(a, arrayReference);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference arrayTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        final spoon.reflect.code.CtTypeAccess<java.lang.Object> typeAccess = factory.Code().createTypeAccess(references.buildTypeReference(arrayTypeReference, scope));
        if ((typeAccess.getAccessedType()) instanceof spoon.reflect.reference.CtArrayTypeReference) {
            ((spoon.reflect.reference.CtArrayTypeReference) (typeAccess.getAccessedType())).getArrayType().setAnnotations(this.references.buildTypeReference(arrayTypeReference, scope).getAnnotations());
        }
        context.enter(typeAccess, arrayTypeReference);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ArrayTypeReference arrayTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        return visit(arrayTypeReference, ((org.eclipse.jdt.internal.compiler.lookup.BlockScope) (null)));
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference arrayQualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        final spoon.reflect.code.CtTypeAccess<java.lang.Object> typeAccess = factory.Core().createTypeAccess();
        context.enter(typeAccess, arrayQualifiedTypeReference);
        final spoon.reflect.reference.CtArrayTypeReference<java.lang.Object> arrayType = ((spoon.reflect.reference.CtArrayTypeReference<java.lang.Object>) (references.getTypeReference(arrayQualifiedTypeReference.resolvedType)));
        arrayType.getArrayType().setAnnotations(this.references.buildTypeReference(arrayQualifiedTypeReference, scope).getAnnotations());
        typeAccess.setAccessedType(arrayType);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ArrayQualifiedTypeReference arrayQualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        return visit(arrayQualifiedTypeReference, ((org.eclipse.jdt.internal.compiler.lookup.BlockScope) (null)));
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.AssertStatement assertStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createAssert(), assertStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.Assignment assignment, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createAssignment(), assignment);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.CompoundAssignment compoundAssignment, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtOperatorAssignment<java.lang.Object, java.lang.Object> a = factory.Core().createOperatorAssignment();
        a.setKind(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getBinaryOperatorKind(compoundAssignment.operator));
        context.enter(a, compoundAssignment);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.BinaryExpression binaryExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtBinaryOperator<?> op = factory.Core().createBinaryOperator();
        op.setKind(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getBinaryOperatorKind((((binaryExpression.bits) & (org.eclipse.jdt.internal.compiler.ast.ASTNode.OperatorMASK)) >> (org.eclipse.jdt.internal.compiler.ast.ASTNode.OperatorSHIFT))));
        context.enter(op, binaryExpression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.Block block, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createBlock(), block);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.BreakStatement breakStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtBreak b = factory.Core().createBreak();
        if ((breakStatement.label) != null) {
            b.setTargetLabel(new java.lang.String(breakStatement.label));
        }
        context.enter(b, breakStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.CastExpression castExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.casts.add(this.references.buildTypeReference(castExpression.type, scope));
        castExpression.expression.traverse(this, scope);
        return false;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.CharLiteral charLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        charLiteral.computeConstant();
        context.enter(factory.Code().createLiteral(charLiteral.constant.charValue()), charLiteral);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ClassLiteralAccess classLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Code().createClassAccess(references.getTypeReference(classLiteral.targetType)), classLiteral);
        return false;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ConditionalExpression conditionalExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createConditional(), conditionalExpression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.MethodDeclaration methodDeclaration, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        spoon.reflect.declaration.CtMethod<java.lang.Object> m = factory.Core().createMethod();
        m.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(methodDeclaration.selector));
        if ((methodDeclaration.binding) != null) {
            m.setExtendedModifiers(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(methodDeclaration.binding.modifiers, true, true));
        }
        for (spoon.support.reflect.CtExtendedModifier extendedModifier : spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(methodDeclaration.modifiers, false, true)) {
            m.addModifier(extendedModifier.getKind());
        }
        m.setDefaultMethod(methodDeclaration.isDefaultMethod());
        context.enter(m, methodDeclaration);
        if ((!(methodDeclaration.isAbstract())) && (((methodDeclaration.modifiers) & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccNative)) == 0)) {
            context.enter(getFactory().Core().createBlock(), methodDeclaration);
            context.exit(methodDeclaration);
        }
        org.eclipse.jdt.internal.compiler.ast.Receiver receiver = methodDeclaration.receiver;
        if (receiver != null) {
            receiver.traverse(this, methodDeclaration.scope);
        }
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration constructorDeclaration, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        spoon.reflect.declaration.CtConstructor<java.lang.Object> c = factory.Core().createConstructor();
        c.setImplicit(((scope.referenceContext.sourceStart()) == (constructorDeclaration.sourceStart())));
        if ((constructorDeclaration.binding) != null) {
            c.setExtendedModifiers(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(constructorDeclaration.binding.modifiers, true, true));
        }
        if (!(c.isImplicit())) {
            for (spoon.support.reflect.CtExtendedModifier extendedModifier : spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(constructorDeclaration.modifiers, false, true)) {
                c.addModifier(extendedModifier.getKind());
            }
        }
        context.enter(c, constructorDeclaration);
        context.enter(factory.Core().createBlock(), constructorDeclaration);
        context.exit(constructorDeclaration);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.TypeParameter typeParameter, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        return visitTypeParameter(typeParameter, scope);
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.TypeParameter typeParameter, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        return visitTypeParameter(typeParameter, scope);
    }

    private boolean visitTypeParameter(org.eclipse.jdt.internal.compiler.ast.TypeParameter typeParameter, org.eclipse.jdt.internal.compiler.lookup.Scope scope) {
        final spoon.reflect.declaration.CtTypeParameter typeParameterRef = factory.Core().createTypeParameter();
        typeParameterRef.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(typeParameter.name));
        context.enter(typeParameterRef, typeParameter);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ContinueStatement continueStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtContinue c = factory.Core().createContinue();
        context.enter(c, continueStatement);
        if ((continueStatement.label) != null) {
            c.setTargetLabel(new java.lang.String(continueStatement.label));
        }
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.DoStatement doStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createDo(), doStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.DoubleLiteral doubleLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        doubleLiteral.computeConstant();
        context.enter(factory.Code().createLiteral(doubleLiteral.constant.doubleValue()), doubleLiteral);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.EqualExpression equalExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtBinaryOperator<?> op = factory.Core().createBinaryOperator();
        op.setKind(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getBinaryOperatorKind((((equalExpression.bits) & (org.eclipse.jdt.internal.compiler.ast.ASTNode.OperatorMASK)) >> (org.eclipse.jdt.internal.compiler.ast.ASTNode.OperatorSHIFT))));
        context.enter(op, equalExpression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ExplicitConstructorCall explicitConstructor, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtInvocation<java.lang.Object> inv = factory.Core().createInvocation();
        inv.setImplicit(explicitConstructor.isImplicitSuper());
        inv.setExecutable(references.getExecutableReference(explicitConstructor.binding));
        spoon.reflect.reference.CtTypeReference<?> declaringType = inv.getExecutable().getDeclaringType();
        inv.getExecutable().setType((declaringType == null ? null : ((spoon.reflect.reference.CtTypeReference<java.lang.Object>) (declaringType.clone()))));
        context.enter(inv, explicitConstructor);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ExtendedStringLiteral extendedStringLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Code().createLiteral(org.eclipse.jdt.core.compiler.CharOperation.charToString(extendedStringLiteral.source())), extendedStringLiteral);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.FalseLiteral falseLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Code().createLiteral(false), falseLiteral);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.FieldDeclaration fieldDeclaration, org.eclipse.jdt.internal.compiler.lookup.MethodScope scope) {
        spoon.reflect.declaration.CtField<java.lang.Object> field;
        if ((fieldDeclaration.type) != null) {
            field = factory.Core().createField();
        }else {
            field = factory.Core().createEnumValue();
            if ((fieldDeclaration.binding) != null) {
                field.setType(references.getTypeReference(fieldDeclaration.binding.type));
            }
        }
        field.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(fieldDeclaration.name));
        java.util.Set<spoon.support.reflect.CtExtendedModifier> modifierSet = new java.util.HashSet<>();
        if ((fieldDeclaration.binding) != null) {
            if (((fieldDeclaration.binding.declaringClass) != null) && (fieldDeclaration.binding.declaringClass.isEnum())) {
                field.setExtendedModifiers(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(fieldDeclaration.binding.declaringClass.modifiers, true, false));
            }else {
                field.setExtendedModifiers(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(fieldDeclaration.binding.modifiers, true, false));
            }
        }
        for (spoon.support.reflect.CtExtendedModifier extendedModifier : spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(fieldDeclaration.modifiers, false, false)) {
            field.addModifier(extendedModifier.getKind());
        }
        context.enter(field, fieldDeclaration);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.FieldReference fieldReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(helper.createFieldAccess(fieldReference), fieldReference);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.FloatLiteral floatLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        floatLiteral.computeConstant();
        context.enter(factory.Code().createLiteral(floatLiteral.constant.floatValue()), floatLiteral);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ForeachStatement forStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createForEach(), forStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ForStatement forStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createFor(), forStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.IfStatement ifStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createIf(), ifStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.Initializer initializer, org.eclipse.jdt.internal.compiler.lookup.MethodScope scope) {
        spoon.reflect.declaration.CtAnonymousExecutable b = factory.Core().createAnonymousExecutable();
        if (initializer.isStatic()) {
            b.addModifier(spoon.reflect.declaration.ModifierKind.STATIC);
        }
        context.enter(b, initializer);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.InstanceOfExpression instanceOfExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtBinaryOperator<?> op = factory.Core().createBinaryOperator();
        op.setKind(spoon.reflect.code.BinaryOperatorKind.INSTANCEOF);
        context.enter(op, instanceOfExpression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.IntLiteral intLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        intLiteral.computeConstant();
        spoon.reflect.code.CtLiteral<java.lang.Integer> l = factory.Code().createLiteral(intLiteral.constant.intValue());
        context.enter(l, intLiteral);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.LabeledStatement labeledStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.label.push(new java.lang.String(labeledStatement.label));
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.LocalDeclaration localDeclaration, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtLocalVariable<java.lang.Object> v = factory.Core().createLocalVariable();
        v.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(localDeclaration.name));
        if ((localDeclaration.binding) != null) {
            v.setExtendedModifiers(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(localDeclaration.binding.modifiers, true, false));
        }
        for (spoon.support.reflect.CtExtendedModifier extendedModifier : spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(localDeclaration.modifiers, false, false)) {
            v.addModifier(extendedModifier.getKind());
        }
        context.enter(v, localDeclaration);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.LongLiteral longLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        longLiteral.computeConstant();
        context.enter(factory.Code().createLiteral(longLiteral.constant.longValue()), longLiteral);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.NormalAnnotation annotation, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        return visitNormalAnnotation(annotation, scope);
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.NormalAnnotation annotation, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        return visitNormalAnnotation(annotation, scope);
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.MarkerAnnotation annotation, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        return visitMarkerAnnotation(annotation, scope);
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.MarkerAnnotation annotation, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        return visitMarkerAnnotation(annotation, scope);
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.SingleMemberAnnotation annotation, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        visitMarkerAnnotation(annotation, scope);
        context.annotationValueName.push("value");
        return true;
    }

    private <A extends java.lang.annotation.Annotation> boolean visitNormalAnnotation(org.eclipse.jdt.internal.compiler.ast.NormalAnnotation annotation, org.eclipse.jdt.internal.compiler.lookup.Scope scope) {
        context.enter(factory.Code().createAnnotation(references.<A>getTypeReference(annotation.resolvedType)), annotation);
        skipTypeInAnnotation = true;
        return true;
    }

    private <A extends java.lang.annotation.Annotation> boolean visitMarkerAnnotation(org.eclipse.jdt.internal.compiler.ast.Annotation annotation, org.eclipse.jdt.internal.compiler.lookup.Scope scope) {
        context.enter(factory.Code().createAnnotation(references.<A>getTypeReference(annotation.resolvedType, annotation.type)), annotation);
        skipTypeInAnnotation = true;
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.MemberValuePair pair, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        context.annotationValueName.push(new java.lang.String(pair.name));
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.MemberValuePair pair, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.annotationValueName.push(new java.lang.String(pair.name));
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.MessageSend messageSend, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtInvocation<java.lang.Object> inv = factory.Core().createInvocation();
        inv.setExecutable(references.getExecutableReference(messageSend));
        if ((messageSend.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding) {
            if (((inv.getExecutable()) != null) && ((inv.getExecutable().getDeclaringType()) != null)) {
                inv.setTarget(factory.Code().createTypeAccess(inv.getExecutable().getDeclaringType(), inv.getExecutable().getDeclaringType().isAnonymous()));
            }
            if ((messageSend.expectedType()) != null) {
                inv.getExecutable().setType(references.getTypeReference(messageSend.expectedType()));
            }
        }
        context.enter(inv, messageSend);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.NullLiteral nullLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Code().createLiteral(null), nullLiteral);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.OR_OR_Expression or_or_Expression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtBinaryOperator<?> op = factory.Core().createBinaryOperator();
        op.setKind(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getBinaryOperatorKind((((or_or_Expression.bits) & (org.eclipse.jdt.internal.compiler.ast.ASTNode.OperatorMASK)) >> (org.eclipse.jdt.internal.compiler.ast.ASTNode.OperatorSHIFT))));
        context.enter(op, or_or_Expression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        return createParameterizedType(parameterizedQualifiedTypeReference);
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        return createParameterizedType(parameterizedQualifiedTypeReference);
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ParameterizedSingleTypeReference parameterizedSingleTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        return createParameterizedType(parameterizedSingleTypeReference);
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ParameterizedSingleTypeReference parameterizedSingleTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        return createParameterizedType(parameterizedSingleTypeReference);
    }

    private boolean createParameterizedType(org.eclipse.jdt.internal.compiler.ast.TypeReference parameterizedTypeReference) {
        if (skipTypeInAnnotation) {
            return true;
        }
        spoon.reflect.reference.CtTypeReference typeReference = references.buildTypeReference(parameterizedTypeReference, null);
        spoon.reflect.code.CtTypeAccess typeAccess = factory.Code().createTypeAccessWithoutCloningReference(typeReference);
        context.enter(typeAccess, parameterizedTypeReference);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.PostfixExpression postfixExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtUnaryOperator<?> op = factory.Core().createUnaryOperator();
        if ((postfixExpression.operator) == (org.eclipse.jdt.internal.compiler.ast.OperatorIds.PLUS)) {
            op.setKind(spoon.reflect.code.UnaryOperatorKind.POSTINC);
        }
        if ((postfixExpression.operator) == (org.eclipse.jdt.internal.compiler.ast.OperatorIds.MINUS)) {
            op.setKind(spoon.reflect.code.UnaryOperatorKind.POSTDEC);
        }
        context.enter(op, postfixExpression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.PrefixExpression prefixExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtUnaryOperator<?> op = factory.Core().createUnaryOperator();
        if ((prefixExpression.operator) == (org.eclipse.jdt.internal.compiler.ast.OperatorIds.PLUS)) {
            op.setKind(spoon.reflect.code.UnaryOperatorKind.PREINC);
        }
        if ((prefixExpression.operator) == (org.eclipse.jdt.internal.compiler.ast.OperatorIds.MINUS)) {
            op.setKind(spoon.reflect.code.UnaryOperatorKind.PREDEC);
        }
        context.enter(op, prefixExpression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference qualifiedNameRef, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if (((qualifiedNameRef.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.FieldBinding) || ((qualifiedNameRef.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.VariableBinding)) {
            context.enter(helper.createVariableAccess(qualifiedNameRef), qualifiedNameRef);
            return true;
        }else
            if ((qualifiedNameRef.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.TypeBinding) {
                context.enter(factory.Code().createTypeAccessWithoutCloningReference(references.getTypeReference(((org.eclipse.jdt.internal.compiler.lookup.TypeBinding) (qualifiedNameRef.binding)))), qualifiedNameRef);
                return true;
            }else
                if ((qualifiedNameRef.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.ProblemBinding) {
                    if ((context.stack.peek().element) instanceof spoon.reflect.code.CtInvocation) {
                        context.enter(helper.createTypeAccessNoClasspath(qualifiedNameRef), qualifiedNameRef);
                        return true;
                    }
                    context.enter(helper.createFieldAccessNoClasspath(qualifiedNameRef), qualifiedNameRef);
                    return true;
                }else {
                    context.enter(helper.createVariableAccess(factory.Core().createUnboundVariableReference().<spoon.reflect.reference.CtUnboundVariableReference>setSimpleName(qualifiedNameRef.toString()), spoon.support.compiler.jdt.JDTTreeBuilderQuery.isLhsAssignment(context, qualifiedNameRef)), qualifiedNameRef);
                    return true;
                }


    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference qualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if (skipTypeInAnnotation) {
            return true;
        }
        if ((context.stack.peekFirst().node) instanceof org.eclipse.jdt.internal.compiler.ast.UnionTypeReference) {
            context.enter(references.<java.lang.Throwable>getTypeReference(qualifiedTypeReference.resolvedType), qualifiedTypeReference);
            return true;
        }else
            if ((context.stack.peekFirst().element) instanceof spoon.reflect.code.CtCatch) {
                context.enter(helper.createCatchVariable(qualifiedTypeReference), qualifiedTypeReference);
                return true;
            }

        context.enter(factory.Code().createTypeAccessWithoutCloningReference(references.buildTypeReference(qualifiedTypeReference, scope)), qualifiedTypeReference);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference qualifiedTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        return visit(qualifiedTypeReference, ((org.eclipse.jdt.internal.compiler.lookup.BlockScope) (null)));
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ReturnStatement returnStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createReturn(), returnStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.SingleNameReference singleNameReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if ((singleNameReference.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.FieldBinding) {
            context.enter(helper.createFieldAccess(singleNameReference), singleNameReference);
        }else
            if ((singleNameReference.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.VariableBinding) {
                context.enter(helper.createVariableAccess(singleNameReference), singleNameReference);
            }else
                if ((singleNameReference.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.TypeBinding) {
                    context.enter(factory.Code().createTypeAccessWithoutCloningReference(references.getTypeReference(((org.eclipse.jdt.internal.compiler.lookup.TypeBinding) (singleNameReference.binding)))), singleNameReference);
                }else
                    if ((singleNameReference.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.ProblemBinding) {
                        if (((context.stack.peek().element) instanceof spoon.reflect.code.CtInvocation) && (java.lang.Character.isUpperCase(org.eclipse.jdt.core.compiler.CharOperation.charToString(singleNameReference.token).charAt(0)))) {
                            context.enter(helper.createTypeAccessNoClasspath(singleNameReference), singleNameReference);
                        }else {
                            context.enter(helper.createFieldAccessNoClasspath(singleNameReference), singleNameReference);
                        }
                    }else
                        if ((singleNameReference.binding) == null) {
                            spoon.reflect.code.CtExpression access = helper.createVariableAccessNoClasspath(singleNameReference);
                            if (access == null) {
                                access = helper.createTypeAccessNoClasspath(singleNameReference);
                            }
                            context.enter(access, singleNameReference);
                        }




        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.QualifiedSuperReference qualifiedSuperReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if (skipTypeInAnnotation) {
            return true;
        }
        context.enter(factory.Core().createSuperAccess(), qualifiedSuperReference);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.SuperReference superReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createSuperAccess(), superReference);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.QualifiedThisReference qualifiedThisRef, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Code().createThisAccess(references.getTypeReference(qualifiedThisRef.qualification.resolvedType), qualifiedThisRef.isImplicitThis()), qualifiedThisRef);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ThisReference thisReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Code().createThisAccess(references.getTypeReference(thisReference.resolvedType), thisReference.isImplicitThis()), thisReference);
        return true;
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.UnionTypeReference unionTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.exit(unionTypeReference);
    }

    @java.lang.Override
    public void endVisit(org.eclipse.jdt.internal.compiler.ast.UnionTypeReference unionTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        endVisit(unionTypeReference, ((org.eclipse.jdt.internal.compiler.lookup.BlockScope) (null)));
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.UnionTypeReference unionTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if (!((context.stack.peekFirst().node) instanceof org.eclipse.jdt.internal.compiler.ast.Argument)) {
            throw new spoon.SpoonException("UnionType is only supported for CtCatch.");
        }
        context.enter(helper.createCatchVariable(unionTypeReference), unionTypeReference);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.UnionTypeReference unionTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        return visit(unionTypeReference, ((org.eclipse.jdt.internal.compiler.lookup.BlockScope) (null)));
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.SingleTypeReference singleTypeReference, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if (skipTypeInAnnotation) {
            return true;
        }
        if ((context.stack.peekFirst().node) instanceof org.eclipse.jdt.internal.compiler.ast.UnionTypeReference) {
            if ((singleTypeReference.resolvedType) == null) {
                spoon.reflect.reference.CtTypeReference typeReference = factory.Type().createReference(singleTypeReference.toString());
                spoon.reflect.reference.CtReference ref = references.getDeclaringReferenceFromImports(singleTypeReference.getLastToken());
                references.setPackageOrDeclaringType(typeReference, ref);
                context.enter(typeReference, singleTypeReference);
            }else {
                context.enter(references.<java.lang.Throwable>getTypeReference(singleTypeReference.resolvedType), singleTypeReference);
            }
            return true;
        }else
            if ((context.stack.peekFirst().element) instanceof spoon.reflect.code.CtCatch) {
                context.enter(helper.createCatchVariable(singleTypeReference), singleTypeReference);
                return true;
            }

        context.enter(factory.Code().createTypeAccessWithoutCloningReference(references.buildTypeReference(singleTypeReference, scope)), singleTypeReference);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.SingleTypeReference singleTypeReference, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        return visit(singleTypeReference, ((org.eclipse.jdt.internal.compiler.lookup.BlockScope) (null)));
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.StringLiteral stringLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Code().createLiteral(org.eclipse.jdt.core.compiler.CharOperation.charToString(stringLiteral.source())), stringLiteral);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.StringLiteralConcatenation literal, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createBinaryOperator().<spoon.reflect.code.CtBinaryOperator>setKind(spoon.reflect.code.BinaryOperatorKind.PLUS), literal);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.CaseStatement caseStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        if ((context.stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.CaseStatement) {
            context.exit(context.stack.peek().node);
        }
        context.enter(factory.Core().createCase(), caseStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.SwitchStatement switchStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createSwitch(), switchStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.SynchronizedStatement synchronizedStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createSynchronized(), synchronizedStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ThrowStatement throwStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createThrow(), throwStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.TrueLiteral trueLiteral, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Code().createLiteral(true), trueLiteral);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.TryStatement tryStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtTry t;
        if ((tryStatement.resources.length) > 0) {
            t = factory.Core().createTryWithResource();
        }else {
            t = factory.Core().createTry();
        }
        context.enter(t, tryStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.TypeDeclaration localTypeDeclaration, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.declaration.CtType<?> t;
        if ((localTypeDeclaration.binding) == null) {
            t = factory.Core().createClass();
            t.setSimpleName(spoon.reflect.declaration.CtType.NAME_UNKNOWN);
            ((spoon.reflect.declaration.CtClass) (t)).setSuperclass(references.getTypeReference(null, localTypeDeclaration.allocation.type));
            context.enter(t, localTypeDeclaration);
        }else {
            helper.createType(localTypeDeclaration);
        }
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.TypeDeclaration memberTypeDeclaration, org.eclipse.jdt.internal.compiler.lookup.ClassScope scope) {
        helper.createType(memberTypeDeclaration);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.TypeDeclaration typeDeclaration, org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope scope) {
        if (new java.lang.String(typeDeclaration.name).equals("package-info")) {
            context.enter(factory.Package().getOrCreate(new java.lang.String(typeDeclaration.binding.fPackage.readableName())), typeDeclaration);
            return true;
        }else {
            spoon.reflect.declaration.CtPackage pack;
            if (((typeDeclaration.binding.fPackage.shortReadableName()) != null) && ((typeDeclaration.binding.fPackage.shortReadableName().length) > 0)) {
                pack = factory.Package().getOrCreate(new java.lang.String(typeDeclaration.binding.fPackage.shortReadableName()));
            }else {
                pack = factory.Package().getRootPackage();
            }
            context.enter(pack, typeDeclaration);
            pack.addType(helper.createType(typeDeclaration));
            return true;
        }
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.UnaryExpression unaryExpression, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        spoon.reflect.code.CtUnaryOperator<?> op = factory.Core().createUnaryOperator();
        op.setKind(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getUnaryOperator((((unaryExpression.bits) & (org.eclipse.jdt.internal.compiler.ast.ASTNode.OperatorMASK)) >> (org.eclipse.jdt.internal.compiler.ast.ASTNode.OperatorSHIFT))));
        context.enter(op, unaryExpression);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.WhileStatement whileStatement, org.eclipse.jdt.internal.compiler.lookup.BlockScope scope) {
        context.enter(factory.Core().createWhile(), whileStatement);
        return true;
    }

    @java.lang.Override
    public boolean visit(org.eclipse.jdt.internal.compiler.ast.ModuleDeclaration moduleDeclaration, org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope scope) {
        spoon.reflect.declaration.CtModule module = getHelper().createModule(moduleDeclaration);
        context.compilationUnitSpoon.setDeclaredModule(module);
        return true;
    }
}

