package spoon.support.reflect.eval;


public class VisitorPartialEvaluator extends spoon.reflect.visitor.CtScanner implements spoon.reflect.eval.PartialEvaluator {
    boolean flowEnded = false;

    spoon.reflect.declaration.CtElement result;

    java.lang.Number convert(spoon.reflect.reference.CtTypeReference<?> type, java.lang.Number n) {
        if (((type.getActualClass()) == (int.class)) || ((type.getActualClass()) == (java.lang.Integer.class))) {
            return n.intValue();
        }
        if (((type.getActualClass()) == (byte.class)) || ((type.getActualClass()) == (java.lang.Byte.class))) {
            return n.byteValue();
        }
        if (((type.getActualClass()) == (long.class)) || ((type.getActualClass()) == (java.lang.Long.class))) {
            return n.longValue();
        }
        if (((type.getActualClass()) == (float.class)) || ((type.getActualClass()) == (java.lang.Float.class))) {
            return n.floatValue();
        }
        if (((type.getActualClass()) == (short.class)) || ((type.getActualClass()) == (java.lang.Short.class))) {
            return n.shortValue();
        }
        return n;
    }

    @java.lang.Override
    protected void exit(spoon.reflect.declaration.CtElement e) {
        result = null;
    }

    @java.lang.SuppressWarnings("unchecked")
    public <R extends spoon.reflect.declaration.CtElement> R evaluate(R element) {
        if (element == null) {
            return null;
        }
        element.accept(this);
        if ((result) != null) {
            spoon.reflect.declaration.CtElement r = result;
            result = null;
            if (element.isParentInitialized()) {
                r.setParent(element.getParent());
            }
            return ((R) (r));
        }
        return ((R) (element.clone()));
    }

    void setResult(spoon.reflect.declaration.CtElement element) {
        result = element;
    }

    @java.lang.SuppressWarnings("unchecked")
    public <T> void visitCtBinaryOperator(spoon.reflect.code.CtBinaryOperator<T> operator) {
        spoon.reflect.code.CtExpression<?> left = evaluate(operator.getLeftHandOperand());
        spoon.reflect.code.CtExpression<?> right = evaluate(operator.getRightHandOperand());
        if ((left instanceof spoon.reflect.code.CtLiteral) && (right instanceof spoon.reflect.code.CtLiteral)) {
            java.lang.Object leftObject = ((spoon.reflect.code.CtLiteral<?>) (left)).getValue();
            java.lang.Object rightObject = ((spoon.reflect.code.CtLiteral<?>) (right)).getValue();
            spoon.reflect.code.CtLiteral<java.lang.Object> res = operator.getFactory().Core().createLiteral();
            switch (operator.getKind()) {
                case AND :
                    res.setValue((((java.lang.Boolean) (leftObject)) && ((java.lang.Boolean) (rightObject))));
                    break;
                case OR :
                    res.setValue((((java.lang.Boolean) (leftObject)) || ((java.lang.Boolean) (rightObject))));
                    break;
                case EQ :
                    if (leftObject == null) {
                        res.setValue((leftObject == rightObject));
                    }else {
                        res.setValue(leftObject.equals(rightObject));
                    }
                    break;
                case NE :
                    if (leftObject == null) {
                        res.setValue((leftObject != rightObject));
                    }else {
                        res.setValue((!(leftObject.equals(rightObject))));
                    }
                    break;
                case GE :
                    res.setValue(((((java.lang.Number) (leftObject)).doubleValue()) >= (((java.lang.Number) (rightObject)).doubleValue())));
                    break;
                case LE :
                    res.setValue(((((java.lang.Number) (leftObject)).doubleValue()) <= (((java.lang.Number) (rightObject)).doubleValue())));
                    break;
                case GT :
                    res.setValue(((((java.lang.Number) (leftObject)).doubleValue()) > (((java.lang.Number) (rightObject)).doubleValue())));
                    break;
                case LT :
                    res.setValue(((((java.lang.Number) (leftObject)).doubleValue()) < (((java.lang.Number) (rightObject)).doubleValue())));
                    break;
                case MINUS :
                    res.setValue(convert(operator.getType(), ((((java.lang.Number) (leftObject)).doubleValue()) - (((java.lang.Number) (rightObject)).doubleValue()))));
                    break;
                case MUL :
                    res.setValue(convert(operator.getType(), ((((java.lang.Number) (leftObject)).doubleValue()) * (((java.lang.Number) (rightObject)).doubleValue()))));
                    break;
                case DIV :
                    res.setValue(convert(operator.getType(), ((((java.lang.Number) (leftObject)).doubleValue()) / (((java.lang.Number) (rightObject)).doubleValue()))));
                    break;
                case PLUS :
                    if ((leftObject instanceof java.lang.String) || (rightObject instanceof java.lang.String)) {
                        res.setValue((("" + leftObject) + rightObject));
                    }else {
                        res.setValue(convert(operator.getType(), ((((java.lang.Number) (leftObject)).doubleValue()) + (((java.lang.Number) (rightObject)).doubleValue()))));
                    }
                    break;
                case BITAND :
                    if (leftObject instanceof java.lang.Boolean) {
                        res.setValue((((java.lang.Boolean) (leftObject)) & ((java.lang.Boolean) (rightObject))));
                    }else {
                        res.setValue(((((java.lang.Number) (leftObject)).intValue()) & (((java.lang.Number) (rightObject)).intValue())));
                    }
                    break;
                case BITOR :
                    if (leftObject instanceof java.lang.Boolean) {
                        res.setValue((((java.lang.Boolean) (leftObject)) | ((java.lang.Boolean) (rightObject))));
                    }else {
                        res.setValue(((((java.lang.Number) (leftObject)).intValue()) | (((java.lang.Number) (rightObject)).intValue())));
                    }
                    break;
                case BITXOR :
                    if (leftObject instanceof java.lang.Boolean) {
                        res.setValue((((java.lang.Boolean) (leftObject)) ^ ((java.lang.Boolean) (rightObject))));
                    }else {
                        res.setValue(((((java.lang.Number) (leftObject)).intValue()) ^ (((java.lang.Number) (rightObject)).intValue())));
                    }
                    break;
                default :
                    throw new java.lang.RuntimeException(("unsupported operator " + (operator.getKind())));
            }
            setResult(res);
            return;
        }else
            if ((left instanceof spoon.reflect.code.CtLiteral) || (right instanceof spoon.reflect.code.CtLiteral)) {
                spoon.reflect.code.CtLiteral<?> literal;
                spoon.reflect.code.CtExpression<?> expr;
                if (left instanceof spoon.reflect.code.CtLiteral) {
                    literal = ((spoon.reflect.code.CtLiteral<?>) (left));
                    expr = right;
                }else {
                    literal = ((spoon.reflect.code.CtLiteral<?>) (right));
                    expr = left;
                }
                java.lang.Object o = literal.getValue();
                spoon.reflect.code.CtLiteral<java.lang.Object> res = operator.getFactory().Core().createLiteral();
                switch (operator.getKind()) {
                    case AND :
                        if (((java.lang.Boolean) (o))) {
                            setResult(expr);
                        }else {
                            res.setValue(false);
                            setResult(res);
                        }
                        return;
                    case OR :
                        if (((java.lang.Boolean) (o))) {
                            res.setValue(true);
                            setResult(res);
                        }else {
                            setResult(expr);
                        }
                        return;
                    case BITOR :
                        if ((o instanceof java.lang.Boolean) && ((java.lang.Boolean) (o))) {
                            res.setValue(true);
                            setResult(res);
                        }
                        return;
                    default :
                }
            }

    }

    public <R> void visitCtBlock(spoon.reflect.code.CtBlock<R> block) {
        spoon.reflect.code.CtBlock<?> b = block.getFactory().Core().createBlock();
        for (spoon.reflect.code.CtStatement s : block.getStatements()) {
            spoon.reflect.declaration.CtElement res = evaluate(s);
            if (res != null) {
                if (res instanceof spoon.reflect.code.CtStatement) {
                    b.addStatement(((spoon.reflect.code.CtStatement) (res)));
                }else {
                    b.addStatement(s.clone());
                }
            }
            if (flowEnded) {
                break;
            }
        }
        setResult(b);
    }

    public void visitCtDo(spoon.reflect.code.CtDo doLoop) {
        spoon.reflect.code.CtDo w = doLoop.clone();
        w.setLoopingExpression(evaluate(doLoop.getLoopingExpression()));
        w.setBody(evaluate(doLoop.getBody()));
        setResult(w);
    }

    @java.lang.Override
    public <T> void visitCtFieldRead(spoon.reflect.code.CtFieldRead<T> fieldRead) {
        visitFieldAccess(fieldRead);
    }

    @java.lang.Override
    public <T> void visitCtFieldWrite(spoon.reflect.code.CtFieldWrite<T> fieldWrite) {
        visitFieldAccess(fieldWrite);
    }

    private <T> void visitFieldAccess(spoon.reflect.code.CtFieldAccess<T> fieldAccess) {
        if ("class".equals(fieldAccess.getVariable().getSimpleName())) {
            java.lang.Class<?> actualClass = fieldAccess.getVariable().getDeclaringType().getActualClass();
            if (actualClass != null) {
                spoon.reflect.code.CtLiteral<java.lang.Class<?>> literal = fieldAccess.getFactory().Core().createLiteral();
                literal.setValue(actualClass);
                setResult(literal);
                return;
            }
        }
        if ("length".equals(fieldAccess.getVariable().getSimpleName())) {
            spoon.reflect.code.CtExpression<?> target = fieldAccess.getTarget();
            if (target instanceof spoon.reflect.code.CtNewArray<?>) {
                spoon.reflect.code.CtNewArray<?> newArr = ((spoon.reflect.code.CtNewArray<?>) (target));
                spoon.reflect.code.CtLiteral<java.lang.Number> literal = fieldAccess.getFactory().createLiteral(newArr.getElements().size());
                setResult(literal);
                return;
            }
        }
        if (fieldAccess.getFactory().Type().ENUM.isSubtypeOf(fieldAccess.getVariable().getDeclaringType())) {
            spoon.reflect.code.CtLiteral<spoon.reflect.reference.CtFieldReference<?>> l = fieldAccess.getFactory().Core().createLiteral();
            l.setValue(fieldAccess.getVariable());
            setResult(l);
            return;
        }
        spoon.reflect.declaration.CtField<?> f = fieldAccess.getVariable().getDeclaration();
        if ((f != null) && (f.getModifiers().contains(spoon.reflect.declaration.ModifierKind.FINAL))) {
            setResult(evaluate(f.getDefaultExpression()));
            return;
        }
        setResult(fieldAccess.clone());
    }

    public <T> void visitCtAnnotationFieldAccess(spoon.reflect.code.CtAnnotationFieldAccess<T> annotationFieldAccess) {
        spoon.reflect.declaration.CtField<?> f = annotationFieldAccess.getVariable().getDeclaration();
        setResult(evaluate(f.getDefaultExpression()));
    }

    public void visitCtFor(spoon.reflect.code.CtFor forLoop) {
        java.util.List<spoon.reflect.code.CtStatement> lst = forLoop.getForInit();
        for (spoon.reflect.code.CtStatement s : lst) {
            spoon.reflect.code.CtStatement evaluateStatement = evaluate(s);
            if (evaluateStatement != null) {
                forLoop.addForInit(evaluateStatement);
            }
        }
        forLoop.setExpression(evaluate(forLoop.getExpression()));
        lst = forLoop.getForUpdate();
        for (spoon.reflect.code.CtStatement s : lst) {
            spoon.reflect.code.CtStatement evaluateStatement = evaluate(s);
            if (evaluateStatement != null) {
                forLoop.addForUpdate(evaluateStatement);
            }
        }
        setResult(forLoop.clone());
    }

    public void visitCtIf(spoon.reflect.code.CtIf ifElement) {
        spoon.reflect.code.CtExpression<java.lang.Boolean> r = evaluate(ifElement.getCondition());
        if (r instanceof spoon.reflect.code.CtLiteral) {
            spoon.reflect.code.CtLiteral<java.lang.Boolean> l = ((spoon.reflect.code.CtLiteral<java.lang.Boolean>) (r));
            if (l.getValue()) {
                setResult(evaluate(ifElement.getThenStatement()));
            }else {
                if ((ifElement.getElseStatement()) != null) {
                    setResult(evaluate(ifElement.getElseStatement()));
                }else {
                    setResult(ifElement.getFactory().Code().createComment("if removed", spoon.reflect.code.CtComment.CommentType.INLINE));
                }
            }
        }else {
            spoon.reflect.code.CtIf ifRes = ifElement.getFactory().Core().createIf();
            ifRes.setCondition(r);
            boolean thenEnded = false;
            boolean elseEnded = false;
            ifRes.setThenStatement(((spoon.reflect.code.CtStatement) (evaluate(ifElement.getThenStatement()))));
            if (flowEnded) {
                thenEnded = true;
                flowEnded = false;
            }
            if ((ifElement.getElseStatement()) != null) {
                ifRes.setElseStatement(((spoon.reflect.code.CtStatement) (evaluate(ifElement.getElseStatement()))));
            }
            if (flowEnded) {
                elseEnded = true;
                flowEnded = false;
            }
            setResult(ifRes);
            if (thenEnded && elseEnded) {
                flowEnded = true;
            }
        }
    }

    public <T> void visitCtInvocation(spoon.reflect.code.CtInvocation<T> invocation) {
        spoon.reflect.code.CtInvocation<T> i = invocation.getFactory().Core().createInvocation();
        i.setExecutable(invocation.getExecutable());
        i.setTypeCasts(invocation.getTypeCasts());
        boolean constant = true;
        i.setTarget(evaluate(invocation.getTarget()));
        if (((i.getTarget()) != null) && (!((i.getTarget()) instanceof spoon.reflect.code.CtLiteral))) {
            constant = false;
        }
        for (spoon.reflect.code.CtExpression<?> e : invocation.getArguments()) {
            spoon.reflect.code.CtExpression<?> re = evaluate(e);
            if (!(re instanceof spoon.reflect.code.CtLiteral)) {
                constant = false;
            }
            i.addArgument(re);
        }
        if (i.getExecutable().getSimpleName().equals(spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME)) {
            setResult(i);
            return;
        }
        if (constant) {
            spoon.reflect.declaration.CtExecutable<?> executable = invocation.getExecutable().getDeclaration();
            spoon.reflect.declaration.CtType<?> aType = invocation.getParent(spoon.reflect.declaration.CtType.class);
            spoon.reflect.reference.CtTypeReference<?> execDeclaringType = invocation.getExecutable().getDeclaringType();
            if (((((executable != null) && (aType != null)) && ((invocation.getType()) != null)) && (execDeclaringType != null)) && (execDeclaringType.isSubtypeOf(aType.getReference()))) {
                spoon.reflect.code.CtBlock<?> b = evaluate(executable.getBody());
                flowEnded = false;
                spoon.reflect.code.CtStatement last = b.getStatements().get(((b.getStatements().size()) - 1));
                if ((last != null) && (last instanceof spoon.reflect.code.CtReturn)) {
                    if ((((spoon.reflect.code.CtReturn<?>) (last)).getReturnedExpression()) instanceof spoon.reflect.code.CtLiteral) {
                        setResult(((spoon.reflect.code.CtReturn<?>) (last)).getReturnedExpression());
                        return;
                    }
                }
            }else {
                T r = null;
                try {
                    r = spoon.support.util.RtHelper.invoke(i);
                    if (isLiteralType(r)) {
                        spoon.reflect.code.CtLiteral<T> l = invocation.getFactory().Core().createLiteral();
                        l.setValue(r);
                        setResult(l);
                        return;
                    }
                } catch (java.lang.Exception e) {
                }
            }
        }
        setResult(i);
    }

    private boolean isLiteralType(java.lang.Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof java.lang.String) {
            return true;
        }
        if (object instanceof java.lang.Number) {
            return true;
        }
        if (object instanceof java.lang.Character) {
            return true;
        }
        if (object instanceof java.lang.Class) {
            return true;
        }
        return false;
    }

    @java.lang.Override
    public <T> void visitCtField(spoon.reflect.declaration.CtField<T> f) {
        spoon.reflect.declaration.CtField<T> r = f.clone();
        r.setDefaultExpression(evaluate(f.getDefaultExpression()));
        setResult(r);
    }

    public <T> void visitCtLocalVariable(final spoon.reflect.code.CtLocalVariable<T> localVariable) {
        spoon.reflect.code.CtLocalVariable<T> r = localVariable.clone();
        r.setDefaultExpression(evaluate(localVariable.getDefaultExpression()));
        setResult(r);
    }

    @java.lang.Override
    public <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> catchVariable) {
        spoon.reflect.code.CtCatchVariable<T> r = catchVariable.clone();
        r.setDefaultExpression(evaluate(catchVariable.getDefaultExpression()));
        setResult(r);
    }

    public <R> void visitCtReturn(spoon.reflect.code.CtReturn<R> returnStatement) {
        spoon.reflect.code.CtReturn<R> r = returnStatement.getFactory().Core().createReturn();
        r.setReturnedExpression(evaluate(returnStatement.getReturnedExpression()));
        setResult(r);
        flowEnded = true;
    }

    public void visitCtSynchronized(spoon.reflect.code.CtSynchronized synchro) {
        spoon.reflect.code.CtSynchronized s = synchro.clone();
        s.setBlock(evaluate(synchro.getBlock()));
        setResult(s);
    }

    public void visitCtThrow(spoon.reflect.code.CtThrow throwStatement) {
        spoon.reflect.code.CtThrow r = throwStatement.getFactory().Core().createThrow();
        r.setThrownExpression(evaluate(throwStatement.getThrownExpression()));
        setResult(r);
        flowEnded = true;
    }

    @java.lang.Override
    public void visitCtCatch(spoon.reflect.code.CtCatch catchBlock) {
        super.visitCtCatch(catchBlock);
        flowEnded = false;
    }

    public <T> void visitCtUnaryOperator(spoon.reflect.code.CtUnaryOperator<T> operator) {
        spoon.reflect.code.CtExpression<?> operand = evaluate(operator.getOperand());
        if (operand instanceof spoon.reflect.code.CtLiteral) {
            java.lang.Object object = ((spoon.reflect.code.CtLiteral<?>) (operand)).getValue();
            spoon.reflect.code.CtLiteral<java.lang.Object> res = operator.getFactory().Core().createLiteral();
            switch (operator.getKind()) {
                case NOT :
                    res.setValue((!((java.lang.Boolean) (object))));
                    break;
                default :
                    throw new java.lang.RuntimeException(("unsupported operator " + (operator.getKind())));
            }
            setResult(res);
            return;
        }
        setResult(operator.clone());
    }

    @java.lang.Override
    public <T> void visitCtVariableRead(spoon.reflect.code.CtVariableRead<T> variableRead) {
        visitVariableAccess(variableRead);
    }

    @java.lang.Override
    public <T> void visitCtVariableWrite(spoon.reflect.code.CtVariableWrite<T> variableWrite) {
        visitVariableAccess(variableWrite);
    }

    private <T> void visitVariableAccess(spoon.reflect.code.CtVariableAccess<T> variableAccess) {
        spoon.reflect.declaration.CtVariable<?> v = variableAccess.getVariable().getDeclaration();
        if (((v != null) && (v.hasModifier(spoon.reflect.declaration.ModifierKind.FINAL))) && ((v.getDefaultExpression()) != null)) {
            setResult(evaluate(v.getDefaultExpression()));
        }else {
            setResult(variableAccess.clone());
        }
    }

    public <T, A extends T> void visitCtAssignment(spoon.reflect.code.CtAssignment<T, A> variableAssignment) {
        spoon.reflect.code.CtAssignment<T, A> a = variableAssignment.clone();
        a.setAssignment(evaluate(a.getAssignment()));
        setResult(a);
    }

    public void visitCtWhile(spoon.reflect.code.CtWhile whileLoop) {
        spoon.reflect.code.CtWhile w = whileLoop.clone();
        w.setLoopingExpression(evaluate(whileLoop.getLoopingExpression()));
        if (((whileLoop.getLoopingExpression()) instanceof spoon.reflect.code.CtLiteral) && (!(((spoon.reflect.code.CtLiteral<java.lang.Boolean>) (whileLoop.getLoopingExpression())).getValue()))) {
            setResult(null);
            return;
        }
        w.setBody(evaluate(whileLoop.getBody()));
        setResult(w);
    }

    public <T> void visitCtConditional(spoon.reflect.code.CtConditional<T> conditional) {
        spoon.reflect.code.CtExpression<java.lang.Boolean> r = evaluate(conditional.getCondition());
        if (r instanceof spoon.reflect.code.CtLiteral) {
            spoon.reflect.code.CtLiteral<java.lang.Boolean> l = ((spoon.reflect.code.CtLiteral<java.lang.Boolean>) (r));
            if (l.getValue()) {
                setResult(evaluate(conditional.getThenExpression()));
            }else {
                setResult(evaluate(conditional.getElseExpression()));
            }
        }else {
            spoon.reflect.code.CtConditional<T> ifRes = conditional.getFactory().Core().createConditional();
            ifRes.setCondition(r);
            ifRes.setThenExpression(evaluate(conditional.getThenExpression()));
            ifRes.setElseExpression(evaluate(conditional.getElseExpression()));
            setResult(ifRes);
        }
    }
}

