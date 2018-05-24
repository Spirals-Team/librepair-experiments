package spoon.reflect.ast;


public class AstCheckerTest {
    @org.junit.Test
    public void testAvoidSetCollectionSavedOnAST() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("src/main/java");
        launcher.buildModel();
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        final java.util.List<spoon.reflect.reference.CtTypeReference<?>> collectionsRef = java.util.Arrays.asList(factory.Type().createReference(java.util.Collection.class), factory.Type().createReference(java.util.List.class), factory.Type().createReference(java.util.Set.class), factory.Type().createReference(java.util.Map.class));
        final java.util.List<spoon.reflect.code.CtInvocation<?>> invocations = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtInvocation<?> element) {
                if (!((element.getParent()) instanceof spoon.reflect.code.CtInvocation)) {
                    return false;
                }
                final spoon.reflect.code.CtInvocation<?> parent = ((spoon.reflect.code.CtInvocation<?>) (element.getParent()));
                if (((parent.getTarget()) == null) || (!(parent.getTarget().equals(element)))) {
                    return false;
                }
                if (!(element.getExecutable().getDeclaringType().getSimpleName().startsWith("Ct"))) {
                    return false;
                }
                boolean isDataStructure = false;
                for (int i = 0; i < (collectionsRef.size()); i++) {
                    spoon.reflect.reference.CtTypeReference<?> ctTypeReference = collectionsRef.get(i);
                    if (element.getType().isSubtypeOf(ctTypeReference)) {
                        isDataStructure = true;
                        break;
                    }
                }
                if (!isDataStructure) {
                    return false;
                }
                final java.lang.String simpleName = parent.getExecutable().getSimpleName();
                return ((simpleName.startsWith("add")) || (simpleName.startsWith("remove"))) || (simpleName.startsWith("put"));
            }
        });
        if ((invocations.size()) > 0) {
            final java.lang.String error = invocations.stream().sorted(new spoon.support.comparator.CtLineElementComparator()).map(( i) -> (("see " + (i.getPosition().getFile().getAbsoluteFile())) + " at ") + (i.getPosition().getLine())).collect(java.util.stream.Collectors.joining(",\n"));
            throw new java.lang.AssertionError(error);
        }
    }

    @org.junit.Test
    public void testPushToStackChanges() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/main/java/spoon/support/reflect/CtModifierHandler.java");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/reference");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/internal");
        launcher.addInputResource("./src/test/java/spoon/reflect/ast/AstCheckerTest.java");
        launcher.buildModel();
        final spoon.reflect.ast.AstCheckerTest.PushStackInIntercessionChecker checker = new spoon.reflect.ast.AstCheckerTest.PushStackInIntercessionChecker();
        checker.scan(launcher.getModel().getRootPackage());
        if (!(checker.result.isEmpty())) {
            java.lang.System.err.println(checker.count);
            throw new java.lang.AssertionError(checker.result);
        }
    }

    private class PushStackInIntercessionChecker extends spoon.reflect.visitor.CtScanner {
        private final java.util.List<java.lang.String> notCandidates;

        private java.lang.String result = "";

        private int count;

        PushStackInIntercessionChecker() {
            notCandidates = java.util.Arrays.asList("CtTypeImpl#setTypeMembers", "CtStatementListImpl#setPosition", "CtElementImpl#setFactory", "CtElementImpl#setPositions", "CtElementImpl#setDocComment", "CtElementImpl#setParent", "CtElementImpl#setValueByRole", "CtTypeParameterReferenceImpl#addBound", "CtTypeParameterReferenceImpl#removeBound", "CtTypeParameterReferenceImpl#setBounds", "CtModuleImpl#addUsedService", "CtModuleImpl#addExportedPackage", "CtModuleImpl#addOpenedPackage", "CtModuleImpl#addRequiredModule", "CtModuleImpl#addProvidedService");
        }

        private boolean isToBeProcessed(spoon.reflect.declaration.CtMethod<?> candidate) {
            if ((candidate.getAnnotation(spoon.support.UnsettableProperty.class)) != null) {
                return false;
            }
            if ((candidate.getAnnotation(spoon.support.DerivedProperty.class)) != null) {
                return false;
            }
            return ((((((((((candidate.getBody()) != null) && ((candidate.getParameters().size()) != 0)) && (candidate.hasModifier(spoon.reflect.declaration.ModifierKind.PUBLIC))) && (((candidate.getSimpleName().startsWith("add")) || (candidate.getSimpleName().startsWith("set"))) || (candidate.getSimpleName().startsWith("remove")))) && (candidate.getDeclaringType().getSimpleName().startsWith("Ct"))) && (!(isNotCandidate(candidate)))) && (!(isSurcharged(candidate)))) && (!(isDelegateMethod(candidate)))) && (!(isUnsupported(candidate.getBody())))) && (!(hasPushToStackInvocation(candidate.getBody())));
        }

        private boolean isNotCandidate(spoon.reflect.declaration.CtMethod<?> candidate) {
            return ("setVisibility".equals(candidate.getSimpleName())) || (notCandidates.contains((((candidate.getDeclaringType().getSimpleName()) + "#") + (candidate.getSimpleName()))));
        }

        private boolean isSurcharged(spoon.reflect.declaration.CtMethod<?> candidate) {
            spoon.reflect.code.CtBlock<?> block = candidate.getBody();
            if ((block.getStatements().size()) == 0) {
                return false;
            }
            spoon.reflect.code.CtInvocation potentialDelegate;
            if ((block.getLastStatement()) instanceof spoon.reflect.code.CtReturn) {
                if (!((((spoon.reflect.code.CtReturn) (block.getLastStatement())).getReturnedExpression()) instanceof spoon.reflect.code.CtInvocation)) {
                    if ((block.getStatement(0)) instanceof spoon.reflect.code.CtInvocation) {
                        potentialDelegate = block.getStatement(0);
                    }else {
                        return false;
                    }
                }else {
                    potentialDelegate = ((spoon.reflect.code.CtInvocation) (((spoon.reflect.code.CtReturn) (block.getLastStatement())).getReturnedExpression()));
                }
            }else
                if (((block.getStatement(0)) instanceof spoon.reflect.code.CtInvocation) && ((block.getStatements().size()) == 1)) {
                    potentialDelegate = block.getStatement(0);
                }else {
                    return false;
                }

            spoon.reflect.declaration.CtExecutable declaration = potentialDelegate.getExecutable().getDeclaration();
            if ((declaration == null) || (!(declaration instanceof spoon.reflect.declaration.CtMethod))) {
                return false;
            }
            return !(isToBeProcessed(((spoon.reflect.declaration.CtMethod<?>) (declaration))));
        }

        private boolean isDelegateMethod(spoon.reflect.declaration.CtMethod<?> candidate) {
            if ((candidate.getBody().getStatements().size()) == 0) {
                return false;
            }
            if (!((candidate.getBody().getStatement(0)) instanceof spoon.reflect.code.CtIf)) {
                return false;
            }
            if (!((((spoon.reflect.code.CtIf) (candidate.getBody().getStatement(0))).getThenStatement()) instanceof spoon.reflect.code.CtBlock)) {
                return false;
            }
            final spoon.reflect.code.CtBlock block = ((spoon.reflect.code.CtIf) (candidate.getBody().getStatement(0))).getThenStatement();
            if (!(((block.getStatement(0)) instanceof spoon.reflect.code.CtInvocation) || ((block.getStatement(0)) instanceof spoon.reflect.code.CtReturn))) {
                return false;
            }
            spoon.reflect.code.CtInvocation potentialDelegate;
            if ((block.getStatement(0)) instanceof spoon.reflect.code.CtReturn) {
                if (!((((spoon.reflect.code.CtReturn) (block.getStatement(0))).getReturnedExpression()) instanceof spoon.reflect.code.CtInvocation)) {
                    return false;
                }
                potentialDelegate = ((spoon.reflect.code.CtInvocation) (((spoon.reflect.code.CtReturn) (block.getStatement(0))).getReturnedExpression()));
            }else {
                potentialDelegate = ((spoon.reflect.code.CtInvocation) (block.getStatement(0)));
            }
            return potentialDelegate.getExecutable().getSimpleName().equals(candidate.getSimpleName());
        }

        private boolean isUnsupported(spoon.reflect.code.CtBlock<?> body) {
            return (((body.getStatements().size()) != 0) && ((body.getStatements().get(0)) instanceof spoon.reflect.code.CtThrow)) && ("UnsupportedOperationException".equals(((spoon.reflect.code.CtThrow) (body.getStatements().get(0))).getThrownExpression().getType().getSimpleName()));
        }

        private boolean hasPushToStackInvocation(spoon.reflect.code.CtBlock<?> body) {
            return (body.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class) {
                @java.lang.Override
                public boolean matches(spoon.reflect.code.CtInvocation<?> element) {
                    return (spoon.experimental.modelobs.FineModelChangeListener.class.getSimpleName().equals(element.getExecutable().getDeclaringType().getSimpleName())) && (super.matches(element));
                }
            }).size()) > 0;
        }

        private void process(spoon.reflect.declaration.CtMethod<?> element) {
            (count)++;
            result += (((element.getSignature()) + " on ") + (element.getDeclaringType().getQualifiedName())) + "\n";
        }

        @java.lang.Override
        public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> m) {
            if (isToBeProcessed(m)) {
                process(m);
            }
            super.visitCtMethod(m);
        }
    }
}

